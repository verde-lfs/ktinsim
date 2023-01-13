package lfs.ktinsim.packets

enum class TyreCompound {
    R1,			// 0
    R2,			// 1
    R3,			// 2
    R4,			// 3
    ROAD_SUPER,	// 4
    ROAD_NORMAL,	// 5
    HYBRID,		// 6
    KNOBBLY,		// 7
    NUM,
    NOT_CHANGED;

    fun ubyte() : UByte {
        return when (this) {
            NOT_CHANGED -> 255.toUByte()
            else -> ordinal.toUByte()
        }
    }

    companion object {
        fun get(i: Int) : TyreCompound {
            return when (i) {
                255 -> NOT_CHANGED
                else -> values()[i]
            }
        }

        infix fun getList(data: ByteArray): List<TyreCompound> {
            return data.map { byte -> get(byte.toUByte().toInt()) }
        }
    }
}

