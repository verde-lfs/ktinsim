package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
struct IS_SLC // SeLected Car - sent when a connection selects a car (empty if no car)
{
	byte	Size;		// 8
	byte	Type;		// ISP_SLC
	byte	ReqI;		// 0 unless this is a reply to a TINY_SLC request
	byte	UCID;		// connection's unique id (0 = host)

	char	CName[4];	// car name
};
 */

data class SelectedCarPacket(
    val requestId: UByte,
    val connectionID: UByte,
    val carName: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_SLC.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        connectionID = data[3].toUByte(),
        carName = data.getASCIIString(4, 4)
    )
}