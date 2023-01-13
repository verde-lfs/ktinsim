package lfs.ktinsim.packets

enum class PlayerFlags {
    LEFTSIDE,
    RESERVED_2,
    RESERVED_4,
    AUTOGEARS,
    SHIFTER,
    RESERVED_32,
    HELP_B,
    AXIS_CLUTCH,
    INPITS,
    AUTOCLUTCH,
    MOUSE,
    KB_NO_HELP,
    KB_STABILISED,
    CUSTOM_VIEW;

    companion object {
        fun getList(data: Int) : List<PlayerFlags> {
            return values().filterIndexed {
                    index, flag ->
                (data and (1 shl index)) > 0
            }
        }
    }

}