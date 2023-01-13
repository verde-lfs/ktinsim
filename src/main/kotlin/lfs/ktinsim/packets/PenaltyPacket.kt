package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_PEN // PENalty (given or cleared)
{
	byte	Size;		// 8
	byte	Type;		// ISP_PEN
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	OldPen;		// old penalty value (see below)
	byte	NewPen;		// new penalty value (see below)
	byte	Reason;		// penalty reason (see below)
	byte	Sp3;
};
 */

data class PenaltyPacket(
    val playerId: UByte,
    val oldPenalty: Penalty,
    val newPenalty: Penalty,
    val reason: PenaltyReason,
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PEN.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        oldPenalty = get<Penalty>(data[4]),
        newPenalty = get<Penalty>(data[5]),
        reason = get<PenaltyReason>(data[6])
    )
}