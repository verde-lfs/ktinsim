package lfs.ktinsim

import lfs.ktinsim.packets.MessageOutPacket
import lfs.ktinsim.packets.Packet
import lfs.ktinsim.packets.TinyPacket

open class DebugProcessor(ktInSim: KtInSim): Processor(ktInSim) {
    override fun process(packet: TinyPacket) {
        super.process(packet)
        println(packet)
    }

    override fun process(packet: MessageOutPacket) {
        super.process(packet)
        packet.message.codePoints().forEach {
            print(it)
            print(", ")
        }
        println("")
        println("${packet}")
    }

    override fun process(packet: Packet) {
        println(packet)
    }
}