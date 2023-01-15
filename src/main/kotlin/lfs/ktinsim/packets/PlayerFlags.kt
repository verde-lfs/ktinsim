package lfs.ktinsim.packets

enum class PlayerFlags(override val value: UInt): FlagEnum {
    LEFTSIDE(1u),
    AUTOGEARS(8u),
    SHIFTER(16u),
    HELP_B(64u),
    AXIS_CLUTCH(128u),
    INPITS(256u),
    AUTOCLUTCH(512u),
    MOUSE(1024u),
    KB_NO_HELP(2048u),
    KB_STABILISED(4096u),
    CUSTOM_VIEW(8192u);

    companion object : FlagEnumCompanion<PlayerFlags> {
        override var values = PlayerFlags.values()
    }

}