package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.InSim
/*
struct IS_NCI // New Conn Info - sent on host only if an admin password has been set
{
	byte	Size;		// 16
	byte	Type;		// ISP_NCI
	byte	ReqI;		// 0 unless this is a reply to a TINY_NCI request
	byte	UCID;		// connection's unique id (0 = host)

	byte	Language;	// see below: Languages
	byte	Sp1;
	byte	Sp2;
	byte	Sp3;

	unsigned	UserID;		// LFS UserID
	unsigned	IPAddress;
};
 */

data class NewConnectionInfoPacket(
    val requestId: UByte,
    val connectionId: UByte,
    val language: UByte,
    val userId: UInt,
    val ip: UInt
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_NCI.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        connectionId = data[3].toUByte(),
        language = data[4].toUByte(),
        userId = data.getUIntAt(8),
        ip = data.getUIntAt(12)
    )
}