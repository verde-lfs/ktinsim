package lfs.ktinsim.packets

interface ByteEnum {
    abstract val ordinal: Int

    fun byte(): Byte = this.ordinal.toByte()
}

inline fun <reified T: Enum<T>> get(ordinal: Byte) : T = enumValues<T>()[ordinal.toInt()]