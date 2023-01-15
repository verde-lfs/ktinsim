package lfs.ktinsim.packets

/*

// To operate the local car's lights, horn or siren you can send this IS_SMALL:

// ReqI: 0
// SubT: SMALL_LCS		(Local Car Switches)
// UVal: Switches		(see below)

// Switches bits

// Bits 0 to 7 are a set of flags specifying which values to set.  You can set as many
// as you like at a time.  This is to allow you to set only the values you want to set
// while leaving the others to be controlled by the user.

#define LCS_SET_SIGNALS		1		// bit 0
#define LCS_SET_FLASH		2		// bit 1
#define LCS_SET_HEADLIGHTS	4		// bit 2
#define LCS_SET_HORN		8		// bit 3
#define LCS_SET_SIREN		0x10	// bit 4

// Depending on the above values, InSim will read some of the following values and try
// to set them as required, if a real player is found on the local computer.

// bits 8-9   (Switches & 0x0300) - Signal    (0 off / 1 left / 2 right / 3 hazard)
// bit  10    (Switches & 0x0400) - Flash
// bit	11    (Switches & 0x0800) - Headlights

// bits 16-18 (Switches & 0x070000) - Horn    (0 off / 1 to 5 horn type)
// bits 20-21 (Switches & 0x300000) - Siren   (0 off / 1 fast / 2 slow)

 */

enum class LocalCarSwitch(override val value: UInt): FlagEnum {
    SET_SIGNALS(1u),
    SET_FLASH(2u),
    SET_HEADLIGHTS(4u),
    SET_HORN(8u),
    SET_SIREN(16u),

    SIGNAL_LEFT(0x0100u),
    SIGNAL_RIGHT(0x0200u),
    SIGNAL_HAZARD(0x0300u),

    FLASH_ON(0x0400u),

    HEADLIGHTS_ON(0x0800u),

    HORN_TYPE_1(0x10000u),
    HORN_TYPE_2(0x20000u),
    HORN_TYPE_3(0x30000u),
    HORN_TYPE_4(0x40000u),
    HORN_TYPE_5(0x50000u),

    SIREN_FAST(0x100000u),
    SIREN_SLOW(0x200000u);

}
fun List<LocalCarSwitch>.toUInt(): UInt {
    return this.fold(0u) {acc, switch -> acc or switch.value }
}