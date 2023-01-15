package lfs.ktinsim.packets

import lfs.ktinsim.getIntAt
import lfs.ktinsim.getShortAt
import lfs.ktinsim.getUShortAt

/*
struct CompCar // Car info in 28 bytes - there is an array of these in the MCI (below)
{
	word	Node;		// current path node
	word	Lap;		// current lap
	byte	PLID;		// player's unique id
	byte	Position;	// current race position: 0 = unknown, 1 = leader, etc...
	byte	Info;		// flags and other info - see below
	byte	Sp3;
	int		X;			// X map (65536 = 1 metre)
	int		Y;			// Y map (65536 = 1 metre)
	int		Z;			// Z alt (65536 = 1 metre)
	word	Speed;		// speed (32768 = 100 m/s)
	word	Direction;	// car's motion if Speed > 0: 0 = world y direction, 32768 = 180 deg
	word	Heading;	// direction of forward axis: 0 = world y direction, 32768 = 180 deg
	short	AngVel;		// signed, rate of change of heading: (16384 = 360 deg/s)
};

// NOTE 1) Info byte - the bits in this byte have the following meanings:

#define CCI_BLUE		1		// this car is in the way of a driver who is a lap ahead
#define CCI_YELLOW		2		// this car is slow or stopped and in a dangerous place

#define CCI_LAG			32		// this car is lagging (missing or delayed position packets)

#define CCI_FIRST		64		// this is the first compcar in this set of MCI packets
#define CCI_LAST		128		// this is the last compcar in this set of MCI packets

// NOTE 2) Heading : 0 = world y axis direction, 32768 = 180 degrees, anticlockwise from above
// NOTE 3) AngVel  : 0 = no change in heading,    8192 = 180 degrees per second anticlockwise
 */

data class CompCar(
    val node: UShort,
    val lap: UShort,
    val playerId: UByte,
    val position: UByte,
    val info: List<CarInfoByte>,
    val x: Int,
    val y: Int,
    val z: Int,
    val speed: UShort,
    val direction: UShort,
    val heading: UShort,
    val angleVelocity: Short
) {

    constructor(data: ByteArray) : this(
        node = data.getUShortAt(0),
        lap = data.getUShortAt(2),
        playerId = data[4].toUByte(),
        position = data[5].toUByte(),
        info = CarInfoByte.getList(data[6].toUInt()),
        x = data.getIntAt(8),
        y = data.getIntAt(12),
        z = data.getIntAt(16),
        speed = data.getUShortAt(20),
        direction = data.getUShortAt(22),
        heading = data.getUShortAt(24),
        angleVelocity = data.getShortAt(26)
    )


}