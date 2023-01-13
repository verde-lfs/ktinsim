package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
struct IS_ACR // Admin Command Report - a user typed an admin command - variable size
{
	byte	Size;		// 12, 16, 20... 72 depending on Text
	byte	Type;		// ISP_ACR
	byte	ReqI;		// 0
	byte	Zero;

	byte	UCID;		// connection's unique id (0 = host)
	byte	Admin;		// set if user is an admin
	byte	Result;		// 1 - processed / 2 - rejected / 3 - unknown command
	byte	Sp3;

	char	Text[64];	// 4, 8, 12... 64 characters - last byte is zero
};
 */

data class AdminCommandReportPacket(
    val connectionId: UByte,
    val isAdmin: Boolean,
    val result: Result,
    val text: String
): Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_ACR.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[4].toUByte(),
        isAdmin = data[5] > 0,
        result = get<Result>(data[6]),
        text = data.getASCIIString(8, data[0]*4-8)
    )

    enum class Result: ByteEnum {
        OK,
        PROCESSED,
        REJECTED,
        UNKNOWN_COMMAND;
    }
}