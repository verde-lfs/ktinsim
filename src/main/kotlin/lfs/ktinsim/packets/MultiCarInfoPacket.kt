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

const int MCI_MAX_CARS = 16;

struct IS_MCI // Multi Car Info - if more than MCI_MAX_CARS in race then more than one is sent
{
	byte	Size;		// 4 + NumC * 28
	byte	Type;		// ISP_MCI
	byte	ReqI;		// 0 unless this is a reply to an TINY_MCI request
	byte	NumC;		// number of valid CompCar structs in this packet

	CompCar	Info[MCI_MAX_CARS]; // car info for each player, 1 to MCI_MAX_CARS (NumC)
};
 */

data class MultiCarInfoPacket(
    val requestId: UByte,
    val compCarInfo: Array<CompCar>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_MCI.byte()
    }

    constructor(data: ByteArray) : this(
        data[2].toUByte(),
        Array<CompCar>(data[3].toInt()) {
            val start = 4 + it * 28
            val end = start + 28
            CompCar(data.sliceArray(start until end))
        }
    )
}