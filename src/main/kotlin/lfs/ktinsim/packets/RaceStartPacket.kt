package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
import lfs.ktinsim.data.LapTiming

/*
struct IS_RST // Race STart
{
	byte	Size;		// 28
	byte	Type;		// ISP_RST
	byte	ReqI;		// 0 unless this is a reply to an TINY_RST request
	byte	Zero;

	byte	RaceLaps;	// 0 if qualifying
	byte	QualMins;	// 0 if race
	byte	NumP;		// number of players in race
	byte	Timing;		// lap timing (see below)

	char	Track[6];	// short track name
	byte	Weather;
	byte	Wind;

	word	Flags;		// race flags (must pit, can reset, etc - see below)
	word	NumNodes;	// total number of nodes in the path
	word	Finish;		// node index - finish line
	word	Split1;		// node index - split 1
	word	Split2;		// node index - split 2
	word	Split3;		// node index - split 3
};

// Lap timing info (for Timing byte)

// bits 6 and 7 (Timing & 0xc0):

// 0x40: standard lap timing is being used
// 0x80: custom timing - user checkpoints have been placed
// 0xc0: no lap timing - e.g. open config with no user checkpoints

// bits 0 and 1 (Timing & 0x03): number of checkpoints if lap timing is enabled
 */

data class RaceStartPacket(
    val requestId: UByte,
    val raceLaps: RaceLaps,
    val qualMinutes: UByte,
    val playersInRace: UByte,
    val lapTiming: LapTiming,
    val trackName: String,
    val weather: UByte,
    val wind: Wind,
    val flags: List<RaceFlag>,
    val nodesCount: UShort,
    val finish: UShort,
    val splitNodeIndexes: Array<UShort>,
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_RST.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        raceLaps = RaceLaps(data[4].toUByte().toInt()),
        qualMinutes = data[5].toUByte(),
        playersInRace = data[6].toUByte(),
        lapTiming = LapTiming(data[7].toUByte()),
        trackName = data.getASCIIString(8, 6),
        weather = data[14].toUByte(),
        wind = get<Wind>(data[15]),
        flags = RaceFlag.getList(data.getUShortAt(16).toUInt()),
        nodesCount = data.getUShortAt(18),
        finish = data.getUShortAt(20),
        splitNodeIndexes = Array(3) {
            data.getUShortAt(22 + it * 2)
        }
    )
}