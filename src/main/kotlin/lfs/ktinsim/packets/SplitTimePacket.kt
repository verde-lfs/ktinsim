package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.toRaceTime
import lfs.ktinsim.InSim

/*
struct IS_SPX // SPlit X time
{
	byte	Size;		// 16
	byte	Type;		// ISP_SPX
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	unsigned	STime;	// split time (ms)
	unsigned	ETime;	// total time (ms)

	byte	Split;		// split number 1, 2, 3
	byte	Penalty;	// current penalty value (see below)
	byte	NumStops;	// number of pit stops
	byte	Fuel200;	// /showfuel yes: double fuel percent / no: 255
};

 */

data class SplitTimePacket(
    val playerId: UByte,
    val splitTime: UInt,
    val totalTime: UInt,

    val split: UByte,
    val penalty: Penalty,
    val pitStops: UByte,
    val fuel: UByte,
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_SPX.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        splitTime = data.getUIntAt(4),
        totalTime = data.getUIntAt(8),
        split = data[12].toUByte(),
        penalty = get<Penalty>(data[13]),
        pitStops = data[14].toUByte(),
        fuel = data[15].toUByte()
    )

    override fun toString(): String {
        return "${super.toString()}\nsplitTime: ${splitTime.toRaceTime()}, totalTime: ${totalTime.toRaceTime()}"
    }
}