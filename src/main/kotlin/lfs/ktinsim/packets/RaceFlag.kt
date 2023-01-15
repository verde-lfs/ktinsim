package lfs.ktinsim.packets

enum class RaceFlag(override val value: UInt): FlagEnum {
    CAN_VOTE(1u),
    CAN_SELECT(2u),
    MID_RACE(32u),
    MUST_PIT(64u),
    CAN_RESET(128u),
    FCV(256u),
    CRUISE(512u);

    companion object: FlagEnumCompanion<RaceFlag> {
        override var values = values()
    }
}