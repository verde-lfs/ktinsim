package lfs.ktinsim.packets

import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.put
import lfs.ktinsim.roundBy
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// To send a button to LFS, send this variable sized packet

struct IS_BTN // BuTtoN - button header - followed by 0 to 240 characters
{
	byte	Size;		// 12 + TEXT_SIZE (a multiple of 4)
	byte	Type;		// ISP_BTN
	byte	ReqI;		// non-zero (returned in IS_BTC and IS_BTT packets)
	byte	UCID;		// connection to display the button (0 = local / 255 = all)

	byte	ClickID;	// button ID (0 to 239)
	byte	Inst;		// some extra flags - see below
	byte	BStyle;		// button style flags - see below
	byte	TypeIn;		// max chars to type in - see below

	byte	L;			// left   : 0 - 200
	byte	T;			// top    : 0 - 200
	byte	W;			// width  : 0 - 200
	byte	H;			// height : 0 - 200

//	char	Text[TEXT_SIZE]; // 0 to 240 characters of text
};

// ClickID byte: this value is returned in IS_BTC and IS_BTT packets.

// Host buttons and local buttons are stored separately, so there is no chance of a conflict between
// a host control system and a local system (although the buttons could overlap on screen).

// Programmers of local InSim programs may wish to consider using a configurable button range and
// possibly screen position, in case their users will use more than one local InSim program at once.

// TypeIn byte: if set, the user can click this button to type in text.

// Lowest 7 bits are the maximum number of characters to type in (0 to 95)
// Highest bit (128) can be set to initialise dialog with the button's text

// On clicking the button, a text entry dialog will be opened, allowing the specified number of
// characters to be typed in.  The caption on the text entry dialog is optionally customisable using
// Text in the IS_BTN packet.  If the first character of IS_BTN's Text field is zero, LFS will read
// the caption up to the second zero.  The visible button text then follows that second zero.

// Text: 65-66-67-0 would display button text "ABC" and no caption

// Text: 0-65-66-67-0-68-69-70-71-0-0-0 would display button text "DEFG" and caption "ABC"

// Inst byte: mainly used internally by InSim but also provides some extra user flags

#define INST_ALWAYS_ON	128		// if this bit is set the button is visible in all screens

// NOTE: You should not use INST_ALWAYS_ON for most buttons.  This is a special flag for buttons
// that really must be on in all screens (including the garage and options screens).  You will
// probably need to confine these buttons to the top or bottom edge of the screen, to avoid
// overwriting LFS buttons.  Most buttons should be defined without this flag, and positioned
// in the recommended area so LFS can keep a space clear in the main screens.



// NOTE: If width or height are zero, this would normally be an invalid button.  But in that case if
// there is an existing button with the same ClickID, all the packet contents are ignored except the
// Text field.  This can be useful for updating the text in a button without knowing its position.
// For example, you might reply to an IS_BTT using an IS_BTN with zero W and H to update the text.

 */

data class ButtonPacket(
    val requestId: UByte,
    val connectionId: UByte,
    val clickId: UByte,
    //val internalValue: UByte,
    val buttonStyle: ButtonStyle,
    var maxChars: UByte,
    val left: UByte,
    val top: UByte,
    val width: UByte,
    val height: UByte,
    val text: String,
    val visibleOnAllScreens: Boolean = false,
    val initEntryDialog: Boolean = false,
): OutgoingPacket
{
    companion object {
        val TYPE = InSim.PacketTypes.ISP_BTN.byte()
    }

    init {
        maxChars = maxChars and 127u
    }

    override fun getByteBuffer(): ByteBuffer {
        val textBytes = text.toLFSBytes()
        val textSize = minOf(text.length.roundBy(4), 240)
        val result = initByteBuffer(12 + textSize)
        result.put(TYPE)
            .put(requestId)
            .put(connectionId)

        result.put(clickId)
            .put(if (visibleOnAllScreens) 128u else 0u)
            .put(buttonStyle.toUByte())
            .put((maxChars + (if (initEntryDialog) 128u else 0u)).toUByte())

        result.put(left).put(top).put(width).put(height)

        result.put(textBytes.getFixedSizeByteArray(textSize))

        return result
    }


}