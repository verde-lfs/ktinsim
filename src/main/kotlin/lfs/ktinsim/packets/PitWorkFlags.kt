package lfs.ktinsim.packets

enum class PitWorkFlags {
    NOTHING,		// bit 0 (1)
    STOP,			// bit 1 (2)
    FR_DAM,			// bit 2 (4)
    FR_WHL,			// etc...
    LE_FR_DAM,
    LE_FR_WHL,
    RI_FR_DAM,
    RI_FR_WHL,
    RE_DAM,
    RE_WHL,
    LE_RE_DAM,
    LE_RE_WHL,
    RI_RE_DAM,
    RI_RE_WHL,
    BODY_MINOR,
    BODY_MAJOR,
    SETUP,
    REFUEL,
    NUM;
}