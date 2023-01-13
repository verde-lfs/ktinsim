package lfs.ktinsim.packets

/*
// BStyle byte: style flags for the button

#define ISB_C1			1		// you can choose a standard
#define ISB_C2			2		// interface colour using
#define ISB_C4			4		// these 3 lowest bits - see below
#define ISB_CLICK		8		// click this button to send IS_BTC
#define ISB_LIGHT		16		// light button
#define ISB_DARK		32		// dark button
#define ISB_LEFT		64		// align text to left
#define ISB_RIGHT		128		// align text to right

// colour 0: light grey			(not user editable)
// colour 1: title colour		(default:yellow)
// colour 2: unselected text	(default:black)
// colour 3: selected text		(default:white)
// colour 4: ok					(default:green)
// colour 5: cancel				(default:red)
// colour 6: text string		(default:pale blue)
// colour 7: unavailable		(default:grey)
 */

class ButtonStyle(
    val colour: Colour = Colour.LIGHT_GREY,
    val isClickable: Boolean = false,
    val isLight: Boolean = false,
    val isDark: Boolean = false,
    val isLeftAligned: Boolean = false,
    val isRightAligned: Boolean = false
) {

    fun toUByte(): UByte {
        var result : UInt = 0u
        result += colour.byte().toUInt()
        if (isClickable) result += 8u
        if (isLight) result += 16u
        if (isDark) result += 32u
        if (isLeftAligned) result += 64u
        if (isRightAligned) result += 128u
        return result.toUByte()
    }

    enum class Colour: ByteEnum {
        LIGHT_GREY,
        TITLE,
        UNSELECTED_TEXT,
        SELECTED_TEXT,
        OK,
        CANCEL,
        TEXT_STRING,
        UNAVAILABLE;
    }
}