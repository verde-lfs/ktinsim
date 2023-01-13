package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt

/*
// Set the ISF_OBH flag in the IS_ISI to receive object contact reports

struct CarContOBJ // 8 bytes: car in a contact with an object
{
	byte	Direction;	// car's motion if Speed > 0: 0 = world y direction, 128 = 180 deg
	byte	Heading;	// direction of forward axis: 0 = world y direction, 128 = 180 deg
	byte	Speed;		// m/s
	byte	Zbyte;

	short	X;			// position (1 metre = 16)
	short	Y;			// position (1 metre = 16)
};
 */

data class CarObjectContact(
    val direction: UByte,
    val heading: UByte,
    val speed: UByte,
    val z: UByte,
    val x: UShort,
    val y: UShort
) : Packet {

    constructor(data: ByteArray) : this(
        direction = data[0].toUByte(),
        heading = data[1].toUByte(),
        speed = data[2].toUByte(),
        z = data[3].toUByte(),
        x = data.getUShortAt(4),
        y = data.getUShortAt(6)
    )
}