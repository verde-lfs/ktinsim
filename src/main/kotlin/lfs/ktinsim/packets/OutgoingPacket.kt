package lfs.ktinsim.packets

import java.nio.ByteBuffer

fun ByteBuffer.putMessageSize(size: Int) {
    this.put((size / 4).toByte())
}

interface OutgoingPacket: Packet {
    fun getByteBuffer() : ByteBuffer
    fun initByteBuffer(size: Int) : ByteBuffer = ByteBuffer.allocate(size).also{it.putMessageSize(size)}
}