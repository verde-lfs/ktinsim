package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim
/*
struct IS_AXI // AutoX Info
{
	byte	Size;		// 40
	byte	Type;		// ISP_AXI
	byte	ReqI;		// 0 unless this is a reply to an TINY_AXI request
	byte	Zero;

	byte	AXStart;	// autocross start position
	byte	NumCP;		// number of checkpoints
	word	NumO;		// number of objects

	char	LName[32];	// the name of the layout last loaded (if loaded locally)
};
 */

data class AutoXInfoPacket(
    val requestId: UByte,
    val autocrossStart: UByte,
    val numberOfCheckpoints: UByte,
    val numberOfObjects: UShort,
    val layoutName: String
): Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_AXI.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        autocrossStart = data[4].toUByte(),
        numberOfCheckpoints = data[5].toUByte(),
        numberOfObjects = data.getUShortAt(6),
        // TODO (layout name might have lfs encoding)
        layoutName = data.getASCIIString(8, 32)
    )
}