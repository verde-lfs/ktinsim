package lfs.ktinsim.packets.outsim

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.getFloatAt
import lfs.ktinsim.getIntAt
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.packets.Packet

data class OutGaugePacket(
    val time: UInt,

    val car: String,
    val flags: UShort, // TODO change to outgauge flags
    val gear: Byte,
    val playerId: UByte,
    val speed: Float,
    val rpm: Float,
    val turbo: Float,
    val engineTemp: Float,
    val fuel: Float,
    val oilPressure: Float,
    val oilTemp: Float,
    val dashLights: UInt,
    val showLights: UInt,
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
        flags = data.getUShortAt(8),
        gear = data[10],
        playerId = data[11].toUByte(),
        speed = data.getFloatAt(12),
        rpm = data.getFloatAt(16),
        turbo = data.getFloatAt(20),
        engineTemp = data.getFloatAt(24),
        fuel = data.getFloatAt(28),
        oilPressure = data.getFloatAt(32),
        oilTemp = data.getFloatAt(36),
        dashLights = data.getUIntAt(40),
        showLights = data.getUIntAt(44),
        throttle = data.getFloatAt(48),
        brake = data.getFloatAt(52),
        clutch = data.getFloatAt(56),
        display1 = data.getASCIIString(60, 16),
        display2 = data.getASCIIString(76, 16),
        id = data.getIntAt(92)
    )

}