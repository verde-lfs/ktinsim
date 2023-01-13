package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.put
import java.nio.ByteBuffer

data class ObjectInfo(
    val x: UShort,
    val y: UShort,
    val z: UByte,
    val flags: UByte,
    val index: UByte,
    val heading: UByte) {

    constructor(data: ByteArray) : this(
        x = data.getUShortAt(0),
        y = data.getUShortAt(2),
        z = data[4].toUByte(),
        flags = data[5].toUByte(),
        index = data[6].toUByte(),
        heading = data[7].toUByte(),
    )

    fun getByteArray() : ByteArray {
        val buffer = ByteBuffer.allocate(8)
        buffer.putShort(x.toShort())
            .putShort(y.toShort())
            .put(z)
            .put(flags)
            .put(index)
            .put(heading)
        return buffer.array()
    }
}
