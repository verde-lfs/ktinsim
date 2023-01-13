package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_CRS // Car ReSet
{
	byte	Size;		// 4
	byte	Type;		// ISP_CRS
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id
};
 */

data class CarResetPacket(
    val playerId: UByte
) : Packet {

    constructor(data: ByteArray) : this(data[3].toUByte())

    companion object {
        val TYPE = InSim.PacketTypes.ISP_CRS.byte()
    }
}