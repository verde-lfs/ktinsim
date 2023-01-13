package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
struct IS_III // InsIm Info - /i message from user to host's InSim - variable size
{
	byte	Size;		// 12, 16, 20... 72 depending on Msg
	byte	Type;		// ISP_III
	byte	ReqI;		// 0
	byte	Zero;

	byte	UCID;		// connection's unique id (0 = host)
	byte	PLID;		// player's unique id (if zero, use UCID)
	byte	Sp2;
	byte	Sp3;

	char	Msg[64];	// 4, 8, 12... 64 characters - last byte is zero
};
 */

data class InfoPacket(
    var connectionId: UByte,
    var playerId: UByte,
    var message: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_III.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[4].toUByte(),
        playerId = data[5].toUByte(),
        message = data.getASCIIString(8, data[0]*4-8)
    )
}