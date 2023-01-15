package lfs.ktinsim.packets

enum class PitWorkFlag(override val value: UInt): FlagEnum {
    NOTHING(1u),
    STOP(2u),
    FR_DAM(4u),
    FR_WHL(8u),
    LE_FR_DAM(16u),
    LE_FR_WHL(32u),
    RI_FR_DAM(64u),
    RI_FR_WHL(128u),
    RE_DAM(256u),
    RE_WHL(512u),
    LE_RE_DAM(1024u),
    LE_RE_WHL(2048u),
    RI_RE_DAM(4096u),
    RI_RE_WHL(8192u),
    BODY_MINOR(16384u),
    BODY_MAJOR(32768u),
    SETUP(65536u),
    REFUEL(131072u),
    NUM(262144u);
    
    companion object: FlagEnumCompanion<PitWorkFlag> {
        override var values = values()
    }
}