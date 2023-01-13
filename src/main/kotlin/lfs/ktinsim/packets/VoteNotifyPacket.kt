package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
// LFS notifies the external program of any votes to restart or qualify

// The Vote Actions are defined as:

enum
{
	VOTE_NONE,			// 0 - no vote
	VOTE_END,			// 1 - end race
	VOTE_RESTART,		// 2 - restart
	VOTE_QUALIFY,		// 3 - qualify
	VOTE_NUM
};

struct IS_VTN // VoTe Notify
{
	byte	Size;		// 8
	byte	Type;		// ISP_VTN
	byte	ReqI;		// 0
	byte	Zero;

	byte	UCID;		// connection's unique id
	byte	Action;		// VOTE_X (Vote Action as defined above)
	byte	Spare2;
	byte	Spare3;
};

 */

data class VoteNotifyPacket(
    var connectionId: UByte,
    var action: VoteType
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_VTN.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[4].toUByte(),
        action = get<VoteType>(data[5])
    )

    enum class VoteType: ByteEnum {
        NONE,
        END,
        RESTART,
        QUALIFY,
        NUM
    }
}