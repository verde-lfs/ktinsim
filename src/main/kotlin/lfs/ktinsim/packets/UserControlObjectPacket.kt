package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.getUIntAt

/*
// CONTROL - reports crossing an InSim checkpoint / entering an InSim circle (from layout)
// =======

struct IS_UCO // User Control Object
{
	byte	Size;		// 28
	byte	Type;		// ISP_UCO
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	Sp0;
	byte	UCOAction;
	byte	Sp2;
	byte	Sp3;

	unsigned	Time;	// hundredths of a second since start (as in SMALL_RTP)

	CarContOBJ	C;

	ObjectInfo	Info;	// Info about the checkpoint or circle (see below)
};

// UCOAction byte

enum
{
	UCO_CIRCLE_ENTER,	// entered a circle
	UCO_CIRCLE_LEAVE,	// left a circle
	UCO_CP_FWD,			// crossed cp in forward direction
	UCO_CP_REV,			// crossed cp in reverse direction
};

// Identifying an InSim checkpoint from the ObjectInfo:

// Index is 252.  Checkpoint index (seen in the layout editor) is stored in Flags bits 0 and 1

// 00 = finish line
// 01 = 1st checkpoint
// 10 = 2nd checkpoint
// 11 = 3rd checkpoint

// Note that the checkpoint index has no meaning in LFS and is provided only for your convenience.
// If you use many InSim checkpoints you may need to identify them with the X and Y values.

// Identifying an InSim circle from the ObjectInfo:

// Index is 253.  The circle index (seen in the layout editor) is stored in the Heading byte.
 */

data class UserControlObjectPacket(
    val playerId: UByte,
    val action: UByte,
    val time: UInt,
    val carObjectContact: CarObjectContact,
    val objectInfo: ObjectInfo,
): Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_UCO.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        action = data[5].toUByte(),
        time = data.getUIntAt(8),
        carObjectContact = CarObjectContact(data.sliceArray(12 until 20)),
        objectInfo = ObjectInfo(data.sliceArray(20 until 28))
    )
}