package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim

/*
// Set the ISF_HLV flag in the IS_ISI to receive reports of incidents that would violate HLVC

struct IS_HLV // Hot Lap Validity - off track / hit wall / speeding in pits / out of bounds
{
	byte	Size;		// 16
	byte	Type;		// ISP_HLV
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	HLVC;		// 0: ground / 1: wall / 4: speeding / 5: out of bounds
	byte	Sp1;
	word	Time;		// looping time stamp (hundredths - time since reset - like TINY_GTH)

	CarContOBJ	C;
};
 */

data class HotlapValidityPacket(
    val playerId: UByte,
    val hlvc: HLVC,
    val time: UShort,
    val carObjectContact: CarObjectContact
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_HLV.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        hlvc = get<HLVC>(data[4]),
        time = data.getUShortAt(6),
        carObjectContact = CarObjectContact(data.sliceArray(8 until 16))
    )

    enum class HLVC {
        GROUND,
        WALL,
        RESERVED_2,
        RESERVED_3,
        SPEEDING,
        OUT_OF_BOUNDS;
    }
}