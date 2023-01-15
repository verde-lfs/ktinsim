package lfs.ktinsim.data

data class LapTiming(
    val checkPointsCount: UByte,
    val timingType: Type,
) {

    constructor(data: UByte) : this(
        checkPointsCount = data and 0x03u,
        timingType = when (data and 0xc0u) {
            0x40u.toUByte() -> Type.STANDARD
            0x80u.toUByte() -> Type.CUSTOM
            else -> Type.NONE
        }
    )

    enum class Type { STANDARD, CUSTOM, NONE }
}