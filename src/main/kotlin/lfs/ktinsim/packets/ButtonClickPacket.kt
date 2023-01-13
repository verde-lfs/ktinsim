package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
// Replies: If the user clicks on a clickable button, this packet will be sent:

struct IS_BTC // BuTton Click - sent back when user clicks a button
{
	byte	Size;		// 8
	byte	Type;		// ISP_BTC
	byte	ReqI;		// ReqI as received in the IS_BTN
	byte	UCID;		// connection that clicked the button (zero if local)

	byte	ClickID;	// button identifier originally sent in IS_BTN
	byte	Inst;		// used internally by InSim
	byte	CFlags;		// button click flags - see below
	byte	Sp3;
};

// CFlags byte: click flags

#define ISB_LMB			1		// left click
#define ISB_RMB			2		// right click
#define ISB_CTRL		4		// ctrl + click
#define ISB_SHIFT		8		// shift + click
 */

data class ButtonClickPacket(
    val requestId: UByte,
    val connectionId: UByte,
    val clickId: UByte,
    val internalValue: UByte,
    val flags: List<Flag>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_BTC.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        connectionId = data[3].toUByte(),
        clickId = data[4].toUByte(),
        internalValue = data[5].toUByte(),
        flags = arrayListOf<Flag>().apply {
            for (flag in Flag.values()) {
                if ((data[6].toInt() and flag.value) > 0) this.add(flag)
            }
        }
    )

    enum class Flag(val value: Int) {
        LMB(1),
        RMB(2),
        CTRL(4),
        SHIFT(8);
    }
}