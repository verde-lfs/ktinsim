package lfs.ktinsim.packets

enum class CarInfoByte(override val value: UInt): FlagEnum {
    BLUE(1u),
    YELLOW(2u),
    LAG(32u),
    FIRST(64u),
    LAST(128u);

    companion object: FlagEnumCompanion<CarInfoByte> {
        override var values = values()
    }
}