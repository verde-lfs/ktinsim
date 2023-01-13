package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.InSim

/*
struct IS_CSC // Car State Changed - reports a change in a car's state (currently start or stop)
{
	byte	Size;		// 20
	byte	Type;		// ISP_CSC
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	Sp0;
	byte	CSCAction;
	byte	Sp2;
	byte	Sp3;

	unsigned	Time;	// hundredths of a second since start (as in SMALL_RTP)

	CarContOBJ	C;
};

// CSCAction byte

enum
{
	CSC_STOP,
	CSC_START,
};

 */

data class CarStateChangedPacket(
    val playerId: UByte,
    val action: Action,
    val time: UInt,
    val carObjectContact: CarObjectContact
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_CSC.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        action = get<Action>(data[5]),
        time = data.getUIntAt(8),
        carObjectContact = CarObjectContact(data.sliceArray(12 until 20))
    )

    enum class Action {
        STOP,
        START;
    }
}