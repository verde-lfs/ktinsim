package lfs.ktinsim.packets


import lfs.ktinsim.InSim
/*
struct IS_TOC // Take Over Car
{
	byte	Size;		// 8
	byte	Type;		// ISP_TOC
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	OldUCID;	// old connection's unique id
	byte	NewUCID;	// new connection's unique id
	byte	Sp2;
	byte	Sp3;
};
 */

data class TakeOverCarPacket(
    val requestId: UByte,
    val playerId: UByte,
    val oldConnectionId: UByte,
    val newConnectionId: UByte

) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_TOC.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        playerId = data[3].toUByte(),
        oldConnectionId = data[4].toUByte(),
        newConnectionId = data[5].toUByte()
    )
}