package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_PLL // PLayer Leave race (spectate - removed from player list)
{
	byte	Size;		// 4
	byte	Type;		// ISP_PLL
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id
};
 */

data class PlayerLeavePacket(
    val playerId: Byte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PLL.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3]
    )
}