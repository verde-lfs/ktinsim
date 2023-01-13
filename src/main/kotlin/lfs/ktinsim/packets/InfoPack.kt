package lfs.ktinsim.packets

interface InfoPack<T> {
    fun pack(data: ByteArray) : T
}