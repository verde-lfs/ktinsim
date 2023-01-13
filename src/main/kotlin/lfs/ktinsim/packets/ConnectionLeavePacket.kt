package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_CNL // ConN Leave
{
	byte	Size;		// 8
	byte	Type;		// ISP_CNL
	byte	ReqI;		// 0
	byte	UCID;		// unique id of the connection which left

	byte	Reason;		// leave reason (see below)
	byte	Total;		// number of connections including host
	byte	Sp2;
	byte	Sp3;
};
 */

data class ConnectionLeavePacket(
    val connectionId: UByte,
    val reason: LeaveReason,
    val totalConnections: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_CNL.byte()
    }

    constructor(data: ByteArray) : this(data[3].toUByte(), get<LeaveReason>(data[4]), data[5].toUByte())

    enum class LeaveReason : ByteEnum {
        DISCO,		    // 0 - none
        TIMEOUT,		// 1 - timed out
        LOSTCONN,		// 2 - lost connection
        KICKED,		    // 3 - kicked
        BANNED,		    // 4 - banned
        SECURITY,		// 5 - security
        CPW,			// 6 - cheat protection wrong
        OOS,			// 7 - out of sync with host
        JOOS,			// 8 - join OOS (initial sync failed)
        HACK,			// 9 - invalid packet
        NUM;
    }
}