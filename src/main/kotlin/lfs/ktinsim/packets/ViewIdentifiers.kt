package lfs.ktinsim.packets

enum class ViewIdentifiers {
    FOLLOW,	// 0 - arcade
    HELI,		// 1 - helicopter
    CAM,		// 2 - tv camera
    DRIVER,	// 3 - cockpit
    CUSTOM,	// 4 - custom
    MAX,
    ANOTHER;

    fun ubyte() : UByte {
        return when (this) {
            ANOTHER -> 255.toUByte()
            else -> ordinal.toUByte()
        }
    }
}