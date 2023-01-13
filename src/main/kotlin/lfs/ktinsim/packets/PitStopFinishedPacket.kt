package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.InSim
/*
struct IS_PSF // Pit Stop Finished
{
    byte	Size;		// 12
    byte	Type;		// ISP_PSF
    byte	ReqI;		// 0
    byte	PLID;		// player's unique id

    unsigned	STime;	// stop time (ms)
    unsigned	Spare;
};
*/

data class PitStopFinishedPacket(
    val playerId: UByte,
    val stopTime: UInt
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PSF.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        stopTime = data.getUIntAt(4)
    )
}