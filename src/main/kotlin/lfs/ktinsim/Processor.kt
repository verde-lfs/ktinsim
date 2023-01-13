package lfs.ktinsim

import lfs.ktinsim.packets.*

open class Processor(
    val ktInSim: KtInSim,
) {
    open fun process(packet: VersionPacket) {
        if (packet.inSimVersion == InSim.VERSION) {
            println("RequestId: ${packet.requestId}, LFS version: ${packet.version}, InSim version ${packet.inSimVersion}")
        } else {
            ktInSim.stop()
            println("InSim version ${packet.inSimVersion} is not supported by ktinsim")
        }
    }

    open fun process(packet: TinyPacket) {
        if (packet.subType == TinyPacket.SubType.NONE) {
            ktInSim.addPacketToQueue(packet)
        }
    }

    open fun process(packet: MessageOutPacket) {
        println(packet.message)
    }

    open fun process(packet: ScreenshotPacket) {
        println(
            when (packet.error) {
                ScreenshotPacket.Error.OK -> "Screenshot saved successfully"
                ScreenshotPacket.Error.DEDICATED -> "Could not save screenshot: dedicated host"
                ScreenshotPacket.Error.CORRUPTED -> "Packet is corrupted: name does not end with zero"
                else -> "Could not save the screenshot"
            }
        )
    }

    open fun process(packet: Packet) {

    }
}