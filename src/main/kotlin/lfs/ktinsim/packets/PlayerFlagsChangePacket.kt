package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim

/*
struct IS_PFL // Player FLags (help flags changed)
{
	byte	Size;		// 8
	byte	Type;		// ISP_PFL
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	word	Flags;		// player flags (see below)
	word	Spare;
};
 */

data class PlayerFlagsChangePacket(
    val playerId: UByte,
    val flags: List<PlayerFlags>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PFL.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        flags = PlayerFlags.getList(data.getUShortAt(4).toUInt())
    )
}