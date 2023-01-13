package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
// CAR TRACKING - car position info sent at constant intervals
// ============

// IS_NLP - compact, all cars in 1 variable sized packet
// IS_MCI - detailed, max 16 cars per variable sized packet

// To receive IS_NLP or IS_MCI packets at a specified interval:

// 1) Set the Interval field in the IS_ISI (InSimInit) packet (10, 20, 30... 8000 ms)
// 2) Set one of the flags ISF_NLP or ISF_MCI in the IS_ISI packet

// If ISF_NLP flag is set, one IS_NLP packet is sent...

const int NLP_MAX_CARS = 40;

struct IS_NLP // Node and Lap Packet - variable size
{
	byte	Size;		// 4 + NumP * 6 (PLUS 2 if needed to make it a multiple of 4)
	byte	Type;		// ISP_NLP
	byte	ReqI;		// 0 unless this is a reply to an TINY_NLP request
	byte	NumP;		// number of players in race

	NodeLap	Info[NLP_MAX_CARS];	// node and lap of each player, 1 to NLP_MAX_CARS (NumP)
};
 */

data class NodeLapPacket(
    val requestId: UByte,
    val nodeLapInfo: Array<NodeLap>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_NLP.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        nodeLapInfo = Array<NodeLap>(data[3].toInt()) {
            val start = 4 + it * 6
            val end = start + 6 - 1
            NodeLap(data.sliceArray(start..end))
        }
    )
}