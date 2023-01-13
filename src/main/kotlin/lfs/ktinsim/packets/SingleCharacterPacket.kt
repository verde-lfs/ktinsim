package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

data class SingleCharacterPacket(
    val key: UByte,
    val isShiftPressed: Boolean = false,
    val isCtrlPressed: Boolean = false
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 8
        val TYPE : Byte = InSim.PacketTypes.ISP_SCH.byte()
    }
    private var keyModifierFlags : Int = 0

    init {
        if (isShiftPressed)
            keyModifierFlags += 1

        if (isCtrlPressed)
            keyModifierFlags += 2
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        result.put(key)
        	.put(keyModifierFlags.toByte())
        return result
    }

}