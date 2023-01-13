package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.utils.LFSTextUtils.toUTF8String
import lfs.ktinsim.InSim
/*
struct IS_NCN // New ConN
{
	byte	Size;		// 56
	byte	Type;		// ISP_NCN
	byte	ReqI;		// 0 unless this is a reply to a TINY_NCN request
	byte	UCID;		// new connection's unique id (0 = host)

	char	UName[24];	// username
	char	PName[24];	// nickname

	byte	Admin;		// 1 if admin
	byte	Total;		// number of connections including host
	byte	Flags;		// bit 2: remote
	byte	Sp3;
};
 */

data class NewConnectionPacket(
    val requestId: UByte,
    val connectionId: UByte,
    val userName: String,
    val nickName: String,

    val isAdmin: Boolean,
    val totalConnections: UByte,
    val flags: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_NCN.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        connectionId = data[3].toUByte(),
        userName = data.getASCIIString(4, 24),
        nickName = data.toUTF8String(28, 24),
        isAdmin = data[52] > 0,
        totalConnections = data[53].toUByte(),
        flags = data[54].toUByte()
    )
}