package lfs.ktinsim.packets

enum class Passenger(override val value: UInt): FlagEnum {
    FRONT_M(1u),
    FRONT_F(2u),
    REAR_LEFT_M(4u),
    REAR_LEFT_F(8u),
    REAR_MIDDLE_M(16u),
    REAR_MIDDLE_F(32u),
    REAR_RIGHT_M(64u),
    REAR_RIGHT_F(128u);

    companion object: FlagEnumCompanion<Passenger> {
        override var values = values()
    }
}