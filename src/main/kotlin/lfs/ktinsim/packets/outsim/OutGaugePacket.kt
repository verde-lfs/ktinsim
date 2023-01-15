package lfs.ktinsim.packets.outsim

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.getFloatAt
import lfs.ktinsim.getIntAt
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.packets.Car
import lfs.ktinsim.packets.Packet

data class OutGaugePacket(
    val time: UInt,

    val car: String,
    val flags: List<Flags>,
    val gear: Byte,
    val playerId: UByte,
    val speed: Float,
    val rpm: Float,
    val turbo: Float,
    val engineTemp: Float,
    val fuel: Float,
    val oilPressure: Float,
    val oilTemp: Float,
    val dashLights: List<DashLight>,
    val showLights: List<DashLight>,
    val throttle: Float,
    val brake: Float,
    val clutch: Float,
    val display1: String,
    val display2: String,

    val id: Int,
): Packet {
    companion object {
        const val SIZE = 96
    }

    constructor(data: ByteArray) : this(
        time = data.getUIntAt(0),
        car = data.getASCIIString(4, 4),
        flags = Flags.getList(data.getUShortAt(8).toInt()),
        gear = data[10],
        playerId = data[11].toUByte(),
        speed = data.getFloatAt(12),
        rpm = data.getFloatAt(16),
        turbo = data.getFloatAt(20),
        engineTemp = data.getFloatAt(24),
        fuel = data.getFloatAt(28),
        oilPressure = data.getFloatAt(32),
        oilTemp = data.getFloatAt(36),
        dashLights = DashLight.getList(data.getIntAt(40)),
        showLights = DashLight.getList(data.getIntAt(44)),
        throttle = data.getFloatAt(48),
        brake = data.getFloatAt(52),
        clutch = data.getFloatAt(56),
        display1 = data.getASCIIString(60, 16),
        display2 = data.getASCIIString(76, 16),
        id = data.getIntAt(92)
    )

    enum class Flags(val value: Int) {
        SHIFT(1),
        CTRL(2),

        TURBO(8192),
        KM(16384),
        BAR(32768);

        companion object {
            fun getList(data: Int): List<Flags> {
                return values().filter {
                    (data and it.value) > 0
                }
            }
        }
    }

    enum class DashLight(val value: Int) {
        SHIFT(1),
        FULLBEAM(2),
        HANDBRAKE(4),
        PITSPEED(8),
        TC(16),
        SIGNAL_L(32),
        SIGNAL_R(64),
        SIGNAL_ANY(128),
        OILWARN(256),
        BATTERY(512),
        ABS(1024),
        SPARE(2048),
        NUM(4096);

        companion object {
            fun getList(data: Int): List<DashLight> {
                return values().filter {
                    (data and it.value) > 0
                }
            }
        }
    }
}