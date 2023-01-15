package lfs.ktinsim.packets

interface FlagEnumCompanion<T: FlagEnum> {
    var values: Array<T>

    fun getList(data: UInt): List<T> {
        return values.filter {
            (data and it.value) > 0u
        }
    }
}