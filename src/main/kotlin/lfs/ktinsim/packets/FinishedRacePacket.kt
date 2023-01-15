package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt

/*
struct IS_FIN // FINished race notification (not a final result - use IS_RES)
{
	byte	Size;		// 20
	byte	Type;		// ISP_FIN
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id (0 = player left before result was sent)

	unsigned	TTime;	// race time (ms)
	unsigned	BTime;	// best lap (ms)

	byte	SpA;
	byte	NumStops;	// number of pit stops
	byte	Confirm;	// confirmation flags: disqualified etc - see below
	byte	SpB;

	word	LapsDone;	// laps completed
	word	Flags;		// player flags: help settings etc - see below
};
 */

data class FinishedRacePacket(
    val playerId: UByte,
    val raceTime: UInt,
    val bestTime: UInt,
    val pitStops: UByte,
    val confirmationFlags: List<ConfirmationFlags>,
    val lapsDone: UShort,
    val flags: List<PlayerFlags>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_FIN.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        raceTime = data.getUIntAt(4),
        bestTime = data.getUIntAt(8),
        pitStops = data[13].toUByte(),
        confirmationFlags = ConfirmationFlags.getList(data[14].toUByte().toUInt()),
        lapsDone = data.getUShortAt(16),
        flags = PlayerFlags.getList(data.getUShortAt(18).toUInt())
    )
}