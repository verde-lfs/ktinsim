package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.utils.LFSTextUtils.toUTF8String

/*
struct IS_BTT // BuTton Type - sent back when user types into a text entry button
{
	byte	Size;		// 104
	byte	Type;		// ISP_BTT
	byte	ReqI;		// ReqI as received in the IS_BTN
	byte	UCID;		// connection that typed into the button (zero if local)

	byte	ClickID;	// button identifier originally sent in IS_BTN
	byte	Inst;		// used internally by InSim
	byte	TypeIn;		// from original button specification
	byte	Sp3;

	char	Text[96];	// typed text, zero to TypeIn specified in IS_BTN
};
 */

data class ButtonTypePacket(
    val requestId: UByte,
    val connectionId: UByte,
    val clickId: UByte,
    val internalValue: UByte,
    val maxChars: UByte,
    val text: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_BTT.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        connectionId = data[3].toUByte(),
        clickId =  data[4].toUByte(),
        internalValue = data[5].toUByte(),
        maxChars = data[6].toUByte(),
        text = data.toUTF8String(8, 96)
    )
}