package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.put
import java.nio.ByteBuffer

/*
// JOIN REQUEST - allows external program to decide if a player can join
// ============

// Set the ISF_REQ_JOIN flag in the IS_ISI to receive join requests
// A join request is seen as an IS_NPL packet with ZERO in the NumP field
// An immediate response (e.g. within 1 second) is required using an IS_JRR packet

// In this case, PLID must be zero and JRRAction must be JRR_REJECT or JRR_SPAWN
// If you allow the join and it is successful you will then get a normal IS_NPL with NumP set
// You can also specify the start position of the car using the StartPos structure

// IS_JRR can also be used to move an existing car to a different location
// In this case, PLID must be set, JRRAction must be JRR_RESET or higher and StartPos must be set

struct IS_JRR // Join Request Reply - send one of these back to LFS in response to a join request
{
	byte	Size;		// 16
	byte	Type;		// ISP_JRR
	byte	ReqI;		// 0
	byte	PLID;		// ZERO when this is a reply to a join request - SET to move a car

	byte	UCID;		// set when this is a reply to a join request - ignored when moving a car
	byte	JRRAction;	// 1 - allow / 0 - reject (should send message to user)
	byte	Sp2;
	byte	Sp3;

	ObjectInfo	StartPos; // 0: use default start point / Flags = 0x80: set start point
};

// To use default start point, StartPos should be filled with zero values

// To specify a start point, StartPos X, Y, Zbyte and Heading should be filled like an autocross
// start position, Flags should be 0x80 and Index should be zero

// Values for JRRAction byte

enum
{
	JRR_REJECT,
	JRR_SPAWN,
	JRR_2,
	JRR_3,
	JRR_RESET,
	JRR_RESET_NO_REPAIR,
	JRR_6,
	JRR_7,
};
 */

data class JoinRequestReplyPacket(
    val playerId: UByte,
    val connectionId: UByte,
    val action: Action = Action.REJECT,
    val startPos: ObjectInfo? = null
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 16
        val TYPE = InSim.PacketTypes.ISP_JRR.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(playerId)

        result.put(connectionId)
        	.put(action.byte())
        	.put(0)
        	.put(0)

        startPos?.let {
            result.put(it.getByteArray())
        }
        return result
    }

    enum class Action: ByteEnum {
        REJECT,
        SPAWN,
        RESERVED_2,
        RESERVED_3,
        RESET,
        RESET_NO_REPAIR,
        RESERVED_6,
        RESERVED_7;
    }
}