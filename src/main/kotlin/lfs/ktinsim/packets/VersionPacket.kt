package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
struct IS_VER // VERsion
{
	byte	Size;			// 20
	byte	Type;			// ISP_VERSION
	byte	ReqI;			// ReqI as received in the request packet
	byte	Zero;

	char	Version[8];		// LFS version, e.g. 0.3G
	char	Product[6];		// Product: DEMO / S1 / S2 / S3
	byte	InSimVer;		// InSim version (see below)
	byte	Spare;			// Spare
};
 */

data class VersionPacket(
    val requestId: UByte,
    val version: String,
    val product: String,
    val inSimVersion: UByte
): Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_VER.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        version = data.getASCIIString(4, 8),
        product = data.getASCIIString(12, 6),
        inSimVersion = data[18].toUByte()
    )
}