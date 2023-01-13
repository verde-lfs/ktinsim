package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_AXO // AutoX Object
{
	byte	Size;		// 4
	byte	Type;		// ISP_AXO
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id
};
 */

data class AutoXObjectPacket(
    var playerId: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_AXO.byte()
    }

    constructor(data: ByteArray) : this(data[3].toUByte())
}