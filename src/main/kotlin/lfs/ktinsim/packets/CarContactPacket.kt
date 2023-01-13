package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim

/*
struct IS_CON // CONtact - between two cars (A and B are sorted by PLID)
{
	byte	Size;		// 40
	byte	Type;		// ISP_CON
	byte	ReqI;		// 0
	byte	Zero;

	word	SpClose;	// high 4 bits: reserved / low 12 bits: closing speed (10 = 1 m/s)
	word	Time;		// looping time stamp (hundredths - time since reset - like TINY_GTH)

	CarContact	A;
	CarContact	B;
};
 */

data class CarContactPacket(
    val closingSpeed: UShort,
    val time: UShort,
    val carContacts: Array<CarContact>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_CON.byte()
    }

    constructor(data: ByteArray) : this(
        closingSpeed = data.getUShortAt(4),
        time = data.getUShortAt(6),
        carContacts = Array(2) { i ->
            CarContact(data.sliceArray(8 + i * 16 until 24 + i * 16))
        }
    )
}