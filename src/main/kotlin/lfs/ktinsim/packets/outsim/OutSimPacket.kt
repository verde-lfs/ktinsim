package lfs.ktinsim.packets.outsim

import lfs.ktinsim.data.FloatVector
import lfs.ktinsim.data.IntVector
import lfs.ktinsim.getFloatAt
import lfs.ktinsim.getIntAt
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.packets.Packet

data class OutSimPacket(
    val time: UInt,
    val angularVelocity: FloatVector,
    val heading: Float,
    val pitch: Float,
    val roll: Float,
    val acceleration: FloatVector,
    val velocity: FloatVector,
    val position: IntVector,

    val id: Int,
): Packet {
    companion object {
        const val SIZE = 68
    }

    constructor(data: ByteArray) : this(
        time = data.getUIntAt(0),
        angularVelocity = FloatVector(data, 4),
        heading = data.getFloatAt(16),
        pitch = data.getFloatAt(20),
        roll = data.getFloatAt(24),
        acceleration = FloatVector(data, 28),
        velocity = FloatVector(data, 40),
        position = IntVector(data, 52),
        id = data.getIntAt(64)
    )
}
