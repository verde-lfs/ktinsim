package lfs.ktinsim.packets

import lfs.ktinsim.getShortAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim
/*
struct IS_OBH // OBject Hit - car hit an autocross object or an unknown object
{
	byte	Size;		// 24
	byte	Type;		// ISP_OBH
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	word	SpClose;	// high 4 bits: reserved / low 12 bits: closing speed (10 = 1 m/s)
	word	Time;		// looping time stamp (hundredths - time since reset - like TINY_GTH)

	CarContOBJ	C;

	short	X;			// as in ObjectInfo
	short	Y;			// as in ObjectInfo

	byte	Zbyte;		// if OBH_LAYOUT is set: Zbyte as in ObjectInfo
	byte	Sp1;
	byte	Index;		// AXO_x as in ObjectInfo or zero if it is an unknown object
	byte	OBHFlags;	// see below
};

// OBHFlags byte

#define OBH_LAYOUT		1		// an added object
#define OBH_CAN_MOVE	2		// a movable object
#define OBH_WAS_MOVING	4		// was moving before this hit
#define OBH_ON_SPOT		8		// object in original position

 */

data class ObjectHitPacket(
    val playerId: UByte,
    val closingSpeed: UShort,
    val time: UShort,
    val carObjectContact: CarObjectContact,
    val x: Short,
    val y: Short,

    val z: UByte,
    val index: UByte,
    val flags: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_OBH.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        closingSpeed = data.getUShortAt(4),
        time = data.getUShortAt(6),
        carObjectContact = CarObjectContact(data.sliceArray(8 until 16)),
        x = data.getShortAt(16),
        y = data.getShortAt(18),
        z = data[20].toUByte(),
        index = data[22].toUByte(),
        flags = data[23].toUByte()
    )
}