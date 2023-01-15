package lfs.ktinsim.data

import lfs.ktinsim.packets.ByteEnum

enum class InGameCamera: ByteEnum {
    FOLLOW,	// 0 - arcade
    HELI,	// 1 - helicopter
    CAM,	// 2 - tv camera
    DRIVER,	// 3 - cockpit
    CUSTOM,	// 4 - custom
    MAX,
    ANOTHER;

    override fun byte(): Byte {
        return if (this == ANOTHER)
                255.toByte()
            else
                super.byte()
    }

    companion object {
        fun get(ord: UByte): InGameCamera {
            return when (ord) {
                255u.toUByte() -> ANOTHER
                else -> values()[ord.toInt()]
            }
        }
    }
}