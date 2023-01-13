package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt

/*
struct IS_LAP // LAP time
{
	byte	Size;		// 20
	byte	Type;		// ISP_LAP
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	unsigned	LTime;	// lap time (ms)
	unsigned	ETime;	// total time (ms)

	word	LapsDone;	// laps completed
	word	Flags;		// player flags

	byte	Sp0;
	byte	Penalty;	// current penalty value (see below)
	byte	NumStops;	// number of pit stops
	byte	Fuel200;	// /showfuel yes: double fuel percent / no: 255
};
 */

data class LapTimePacket(
    val playerId: UByte,
    val lapTime: UInt,
    val totalTime: UInt,

    val lapsDone: UShort,
    val flags: List<PlayerFlags>,

    val penalty: Penalty,
    val pitStops: UByte,
    val fuel: UByte,
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_LAP.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        lapTime = data.getUIntAt(4),
        totalTime = data.getUIntAt(8),
        lapsDone = data.getUShortAt(12),
        flags = PlayerFlags.getList(data.getUShortAt(14).toInt()),
        penalty = get<Penalty>(data[17]),
        pitStops = data[18].toUByte(),
        fuel = data[19].toUByte()
    )
}