package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim
/*
struct IS_RES // RESult (qualify or confirmed finish)
{
	byte	Size;		// 84
	byte	Type;		// ISP_RES
	byte	ReqI;		// 0 unless this is a reply to a TINY_RES request
	byte	PLID;		// player's unique id (0 = player left before result was sent)

	char	UName[24];	// username
	char	PName[24];	// nickname
	char	Plate[8];	// number plate - NO ZERO AT END!
	char	CName[4];	// skin prefix

	unsigned	TTime;	// (ms) race or autocross: total time / qualify: session time
	unsigned	BTime;	// (ms) best lap

	byte	SpA;
	byte	NumStops;	// number of pit stops
	byte	Confirm;	// confirmation flags: disqualified etc - see below
	byte	SpB;

	word	LapsDone;	// laps completed
	word	Flags;		// player flags: help settings etc - see below

	byte	ResultNum;	// finish or qualify pos (0 = win / 255 = not added to table)
	byte	NumRes;		// total number of results (qualify doesn't always add a new one)
	word	PSeconds;	// penalty time in seconds (already included in race time)
};
 */

data class ResultPacket(
    val requestId: UByte,
	val playerId: UByte,
	
	val userName: String,
	val nickName: String,
	val numberPlate: String,
	val skinPrefix: String,
	
	val totalTime: UInt,
	val bestLapTime: UInt,
    
	val pitStops: UByte,
	val confirmationFlags: UByte,
	
	val lapsDone: UShort,
	val flags: List<PlayerFlags>,
	
	val result: UByte,
	val resultsCount: UByte,
	val penaltyTime: UShort
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_RES.byte()
    }

    constructor(data: ByteArray) : this(
		requestId = data[2].toUByte(),
		playerId = data[3].toUByte(),

		userName = data.getASCIIString(4, 24),
		nickName = data.getASCIIString(28, 24),
		numberPlate = data.getASCIIString(52, 8),
		skinPrefix = data.getASCIIString(60, 4),

		totalTime = data.getUIntAt(64),
		bestLapTime = data.getUIntAt(68),

		pitStops = data[73].toUByte(),
		confirmationFlags = data[74].toUByte(),

		lapsDone = data.getUShortAt(76),
		flags = PlayerFlags.getList(data.getUShortAt(78).toInt()),

		result = data[80].toUByte(),
		resultsCount = data[81].toUByte(),
		penaltyTime = data.getUShortAt(82)
	)
}