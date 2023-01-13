package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_PLA // Pit LAne
{
	byte	Size;		// 8
	byte	Type;		// ISP_PLA
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	Fact;		// pit lane fact (see below)
	byte	Sp1;
	byte	Sp2;
	byte	Sp3;
};
 */

data class PitLanePacket(
    val playerId: UByte,
    val fact: PitlaneFact
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PLA.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        fact = get<PitlaneFact>(data[4])
    )
}