package lfs.ktinsim

import kotlinx.coroutines.*
import lfs.ktinsim.packets.*
import lfs.ktinsim.packets.outsim.*
import lfs.ktinsim.InSim.PacketTypes
import java.io.BufferedInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.Socket
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

typealias TinyType = TinyPacket.SubType

open class KtInSim(
    val host: String = "127.0.0.1",
    val port: Int = 29999,
    val udpPort: Int = 0,
    processorClass: KClass<out Processor> = Processor::class
) {
    private val requests = Stack<ByteArray>()
    private var running : Boolean = false
    private var processor : Processor = processorClass.constructors.first().call(this)
    private var udpSocket : DatagramSocket? = null

    fun run(initPacket: InitPacket = InitPacket(udpPort = udpPort)) {
        runBlocking {
            val deferredResult = async {
                withContext(Dispatchers.IO) {
                    val socket = Socket(host, port)
                    if (initPacket.udpPort > 0) {
                        udpSocket = DatagramSocket(udpPort, socket.inetAddress)
                    }
                    val outputStream = DataOutputStream(socket.getOutputStream())
                    val inputStream = BufferedInputStream(socket.getInputStream())

                    val initMsgBuffer = initPacket.getByteBuffer()

                    try {
                        outputStream.write(initMsgBuffer.array())
                        running = true
                        udpSocket?.let {
                            async {
                                readIncomingUDPPackets()
                            }
                        }

                        while (running) {
                            while (!requests.isEmpty()) {
                                outputStream.write(requests.pop())
                            }

                            val buffer = readPacketToArray(inputStream)
                            async {processPacket(buffer)}
                        }
                        outputStream.write(TinyPacket(TinyType.CLOSE).getByteBuffer().array())

                    } catch (e: Throwable) {
                        this.coroutineContext.cancelChildren()
                        e.printStackTrace()
                    }
                    socket.close()
                    udpSocket?.close()
                }
            }

            deferredResult.await()
        }
    }

    private suspend fun readIncomingUDPPackets() = withContext(Dispatchers.IO) {
        while (running) {
            val buffer = ByteArray(256)
            val packet = DatagramPacket(buffer, buffer.size)
            udpSocket!!.receive(packet)

            val lastNonZeroByteIndex = packet.data.indexOfLast {it.toInt() != 0}
            processor.process(
                if (packet.data.isNLP()) {
                    NodeLapPacket(buffer)
                } else if (packet.data.isMCI()) {
                    MultiCarInfoPacket(buffer)
                } else if (lastNonZeroByteIndex >= OutSimPacket.SIZE) {
                    OutGaugePacket(buffer)
                } else {
                    OutSimPacket(buffer)
                }
            )
        }
    }

    private suspend fun readPacketToArray(inputStream: BufferedInputStream) : ByteArray = withContext(Dispatchers.IO) {
        var messageSize = -1
        while (messageSize == -1) {
            messageSize = inputStream.read()
        }
        messageSize *= 4
        val buffer = ByteArray(messageSize)
        buffer[0] = (messageSize / 4).toByte()
        var offset = 1
        while (offset < messageSize) {
            val bytes = inputStream.read(buffer, offset, messageSize - offset)
            if (bytes != -1) {
                offset += bytes
            } else {
                throw IOException("Message could not be read")
            }
        }
        return@withContext buffer
    }

    private fun processPacket(buffer : ByteArray) {
        lateinit var packet: Packet
        val messageType = buffer[1]
        try {
            packet = InSim.PacketTypes.get(messageType)
                .getPacketClass()
                .constructors.first {
                    it.parameters.size == 1 && it.parameters[0].type == typeOf<ByteArray>()
                }.call(buffer)
        } catch (e : Throwable) {
            e.printStackTrace()
            println(messageType)
            return
        }

        when (messageType) {
            VersionPacket.TYPE -> processor.process(packet as VersionPacket)
            TinyPacket.TYPE -> processor.process(packet as TinyPacket)
            SmallPacket.TYPE -> processor.process(packet as SmallPacket)
            StatePacket.TYPE -> processor.process(packet as StatePacket)
            CameraPositionPacket.TYPE -> processor.process(packet as CameraPositionPacket)
            MultiplayerPacket.TYPE -> processor.process(packet as MultiplayerPacket)
            MessageOutPacket.TYPE -> processor.process(packet as MessageOutPacket)
            InfoPacket.TYPE -> processor.process(packet as InfoPacket)
            VoteNotifyPacket.TYPE -> processor.process(packet as VoteNotifyPacket)
            RaceStartPacket.TYPE -> processor.process(packet as RaceStartPacket)
            NewConnectionPacket.TYPE -> processor.process(packet as NewConnectionPacket)
            ConnectionLeavePacket.TYPE -> processor.process(packet as ConnectionLeavePacket)
            ConnectionPlayerRenamePacket.TYPE -> processor.process(packet as ConnectionPlayerRenamePacket)
            PlayerJoinPacket.TYPE -> processor.process(packet as PlayerJoinPacket)
            PlayerPitsPacket.TYPE -> processor.process(packet as PlayerPitsPacket)
            PlayerLeavePacket.TYPE -> processor.process(packet as PlayerLeavePacket)
            LapTimePacket.TYPE -> processor.process(packet as LapTimePacket)
            SplitTimePacket.TYPE -> processor.process(packet as SplitTimePacket)
            PitStopPacket.TYPE -> processor.process(packet as PitStopPacket)
            PitStopFinishedPacket.TYPE -> processor.process(packet as PitStopFinishedPacket)
            PitLanePacket.TYPE -> processor.process(packet as PitLanePacket)
            CameraChangePacket.TYPE -> processor.process(packet as CameraChangePacket)
            PenaltyPacket.TYPE -> processor.process(packet as PenaltyPacket)
            TakeOverCarPacket.TYPE -> processor.process(packet as TakeOverCarPacket)
            FlagChangePacket.TYPE -> processor.process(packet as FlagChangePacket)
            PlayerFlagsChangePacket.TYPE -> processor.process(packet as PlayerFlagsChangePacket)
            FinishedRacePacket.TYPE -> processor.process(packet as FinishedRacePacket)
            ResultPacket.TYPE -> processor.process(packet as ResultPacket)
            ReorderPacket.TYPE -> processor.process(packet as ReorderPacket)
            NodeLapPacket.TYPE -> processor.process(packet as NodeLapPacket)
            MultiCarInfoPacket.TYPE -> processor.process(packet as MultiCarInfoPacket)
            CarResetPacket.TYPE -> processor.process(packet as CarResetPacket)
            ButtonFunctionPacket.TYPE -> processor.process(packet as ButtonFunctionPacket)
            AutoXInfoPacket.TYPE -> processor.process(packet as AutoXInfoPacket)
            AutoXObjectPacket.TYPE -> processor.process(packet as AutoXObjectPacket)
            ButtonClickPacket.TYPE -> processor.process(packet as ButtonClickPacket)
            ButtonTypePacket.TYPE -> processor.process(packet as ButtonTypePacket)
            ReplayInfoPacket.TYPE -> processor.process(packet as ReplayInfoPacket)
            ScreenshotPacket.TYPE -> processor.process(packet as ScreenshotPacket)
            CarContactPacket.TYPE -> processor.process(packet as CarContactPacket)
            ObjectHitPacket.TYPE -> processor.process(packet as ObjectHitPacket)
            HotlapValidityPacket.TYPE -> processor.process(packet as HotlapValidityPacket)
            AutoXMultipleObjsPacket.TYPE -> processor.process(packet as AutoXMultipleObjsPacket)
            AdminCommandReportPacket.TYPE -> processor.process(packet as AdminCommandReportPacket)
            NewConnectionInfoPacket.TYPE -> processor.process(packet as NewConnectionInfoPacket)
            UserControlObjectPacket.TYPE -> processor.process(packet as UserControlObjectPacket)
            SelectedCarPacket.TYPE -> processor.process(packet as SelectedCarPacket)
            CarStateChangedPacket.TYPE -> processor.process(packet as CarStateChangedPacket)
            ConnectionInterfaceModePacket.TYPE -> processor.process(packet as ConnectionInterfaceModePacket)
            ModsAllowedPacket.TYPE -> processor.process(packet as ModsAllowedPacket)
            
            else -> processor.process(packet)
        }
    }

    fun addPacketToQueue(packet: OutgoingPacket) {
        requests.add(packet.getByteBuffer().array())
    }

    fun stop() {
        running = false
    }

    fun requestPacket(kclass: KClass<out Packet>, requestId: UByte = 1u) {
        if (requestId.toUInt() == 0u) {
            error("Request id must not be zero")
        }
        val messageType : TinyType = when (kclass) {
            VersionPacket::class -> TinyType.VER
            CameraPositionPacket::class -> TinyType.SCP
            StatePacket::class -> TinyType.SST
            MultiplayerPacket::class -> TinyType.ISM
            NewConnectionPacket::class -> TinyType.NCN
            PlayerJoinPacket::class -> TinyType.NPL
            ResultPacket::class -> TinyType.RES
            NodeLapPacket::class -> TinyType.NLP
            MultiCarInfoPacket::class -> TinyType.MCI
            ReorderPacket::class -> TinyType.REO
            RaceStartPacket::class -> TinyType.RST
            AutoXInfoPacket::class -> TinyType.AXI
            ReplayInfoPacket::class -> TinyType.RIP
            NewConnectionInfoPacket::class -> TinyType.NCI
            AutoXMultipleObjsPacket::class -> TinyType.AXM
            SelectedCarPacket::class -> TinyType.SLC
            ModsAllowedPacket::class -> TinyType.MAL
            else -> null
        } ?: error("Requesting $kclass is not available")
        addPacketToQueue(TinyPacket(messageType, requestId = requestId))
    }

    fun checkConnection(requestId: UByte = 1u) {
        addPacketToQueue(TinyPacket(TinyType.PING, requestId))
    }

    fun cancelVote() {
        addPacketToQueue(TinyPacket(TinyType.VTC))
    }

    fun requestTimeInHundredths() {
        addPacketToQueue(TinyPacket(TinyType.GTH))
    }

    fun requestAllowedCars() {
        addPacketToQueue(TinyPacket(TinyType.ALC))
    }

    open fun startSendingCarPositions(time: UInt) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.SSP,
                time)
        )
    }

    open fun stopSendingCarPositions() {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.SSP,
                0u)
        )
    }

    open fun startSendingCarGauges(time: UInt) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.SSG,
                time
            )
        )
    }

    open fun stopSendingCarGauges() {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.SSG,
                0u
            )
        )
    }

    open fun controlTime(value: TimeControl) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.TMS,
                value.ordinal.toUInt()
            )
        )
    }

    enum class TimeControl { START, STOP }

    open fun makeStep(timeInHundredths: UInt) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.STP,
                timeInHundredths
            )
        )
    }

    open fun setNodeLapInterval(time: UInt) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.NLI,
                time
            )
        )
    }
    
    open fun setAllowedCars(cars: List<Car>) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.ALC,
                cars.toUInt()
            )
        )
    }

    open fun setLocalCarSwitches(switches: List<LocalCarSwitch>) {
        addPacketToQueue(
            SmallPacket(
                SmallPacket.SubType.LCS,
                switches.toUInt()
            )
        )
    }
}

private fun ByteArray.hasPacketType(type: PacketTypes) : Boolean {
    val messageType = this[1]
    val messageSize = this[0].toUByte().toInt() * 4
    val structCount = this[3].toInt()
    val expectedMessageSize =
        when (type) {
            PacketTypes.ISP_NLP -> 4 + structCount * 6 + ((if (structCount.mod(2) == 0) 0 else 2))
            PacketTypes.ISP_MCI -> 4 + structCount * 28
            else -> throw IllegalArgumentException("Only ISP_NLP and ISP_MCI are allowed")
        }

    return (messageType == type.byte()) && (messageSize == expectedMessageSize)
}

fun ByteArray.isNLP() = this.hasPacketType(PacketTypes.ISP_NLP)
fun ByteArray.isMCI() = this.hasPacketType(PacketTypes.ISP_MCI)