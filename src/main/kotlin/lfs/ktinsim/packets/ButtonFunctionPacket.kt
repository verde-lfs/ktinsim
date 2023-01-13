package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// BUTTONS
// =======

// You can make up to 240 buttons appear on the host or guests (ID = 0 to 239).
// You should set the ISF_LOCAL flag (in IS_ISI) if your program is not a host control
// system, to make sure your buttons do not conflict with any buttons sent by the host.

// LFS can display normal buttons in these four screens:

// - main entry screen
// - race setup screen
// - in game
// - SHIFT+U mode

// The recommended area for most buttons is defined by:

#define IS_X_MIN 0
#define IS_X_MAX 110

#define IS_Y_MIN 30
#define IS_Y_MAX 170

// If you draw buttons in this area, the area will be kept clear to
// avoid overlapping LFS buttons with your InSim program's buttons.
// Buttons outside that area will not have a space kept clear.
// You can also make buttons visible in all screens - see below.

// To delete one button or a range of buttons or clear all buttons, send this packet:

struct IS_BFN // Button FunctioN - delete buttons / receive button requests
{
	byte	Size;		// 8
	byte	Type;		// ISP_BFN
	byte	ReqI;		// 0
	byte	SubT;		// subtype, from BFN_ enumeration (see below)

	byte	UCID;		// connection to send to or received from (0 = local / 255 = all)
	byte	ClickID;	// if SubT is BFN_DEL_BTN: ID of single button to delete or first button in range
	byte	ClickMax;	// if SubT is BFN_DEL_BTN: ID of last button in range (if greater than ClickID)
	byte	Inst;		// used internally by InSim
};

enum // the fourth byte of IS_BFN packets is one of these
{
	BFN_DEL_BTN,		//  0 - instruction		: delete one button or range of buttons (must set ClickID)
	BFN_CLEAR,			//  1 - instruction		: clear all buttons made by this insim instance
	BFN_USER_CLEAR,		//  2 - info			: user cleared this insim instance's buttons
	BFN_REQUEST,		//  3 - user request	: SHIFT+B or SHIFT+I - request for buttons
};

// NOTE: BFN_REQUEST allows the user to bring up buttons with SHIFT+B or SHIFT+I

// SHIFT+I clears all host buttons if any - or sends a BFN_REQUEST to host instances
// SHIFT+B is the same but for local buttons and local instances
 */

data class ButtonFunctionPacket(
    val subType: SubType,
    val connectionId: UByte = ConnectionId.LOCAL,
    val clickId: UByte,
    val clickMaxId: UByte,
    val internalValue: UByte
): OutgoingPacket
{
    companion object {
        const val SIZE: Int = 8
        val TYPE = InSim.PacketTypes.ISP_BFN.byte()
    }

    constructor(data: ByteArray) : this(
        subType = get<SubType>(data[3]),
        connectionId = data[4].toUByte(),
        clickId = data[5].toUByte(),
        clickMaxId = data[6].toUByte(),
        internalValue = data[7].toUByte()
    )

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(subType.byte())

        result.put(byteArrayOf(
            connectionId.toByte(), clickId.toByte(), clickMaxId.toByte(), internalValue.toByte()
        ))

        return result
    }

    enum class SubType: ByteEnum // the fourth byte of IS_BFN packets is one of these
    {
        DEL_BTN,		//  0 - instruction		: delete one button or range of buttons (must set ClickID)
        CLEAR,			//  1 - instruction		: clear all buttons made by this insim instance
        USER_CLEAR,		//  2 - info			: user cleared this insim instance's buttons
        REQUEST,		//  3 - user request	: SHIFT+B or SHIFT+I - request for buttons
    }
}