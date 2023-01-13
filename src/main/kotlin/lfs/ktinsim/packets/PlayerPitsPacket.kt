package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_PLP // PLayer Pits (go to settings - stays in player list)
{
	byte	Size;		// 4
	byte	Type;		// ISP_PLP
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id
};

 */

data class PlayerPitsPacket(
    val playerId: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PLP.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte()
    )
}