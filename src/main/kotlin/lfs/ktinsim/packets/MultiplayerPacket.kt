package lfs.ktinsim.packets

import lfs.ktinsim.utils.LFSTextUtils.toUTF8String
import lfs.ktinsim.InSim
/*

struct IS_ISM // InSim Multi
{
	byte	Size;		// 40
	byte	Type;		// ISP_ISM
	byte	ReqI;		// usually 0 / or if a reply: ReqI as received in the TINY_ISM
	byte	Zero;

	byte	Host;		// 0 = guest / 1 = host
	byte	Sp1;
	byte	Sp2;
	byte	Sp3;

	char	HName[32];	// the name of the host joined or started
};
 */

data class MultiplayerPacket(
    var requestId: UByte,
    var isHost: Boolean,
    var hostname: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_ISM.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        isHost = data[4] == 1.toByte(),
        hostname = data.toUTF8String(8, 32)
    )
}