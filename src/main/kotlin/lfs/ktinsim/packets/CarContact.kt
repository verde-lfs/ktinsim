package lfs.ktinsim.packets

import lfs.ktinsim.getShortAt

/*
struct CarContact // 16 bytes: one car in a contact - two of these in the IS_CON (below)
{
	byte	PLID;
	byte	Info;		// like Info byte in CompCar (CCI_BLUE / CCI_YELLOW / CCI_LAG)
	byte	Sp2;		// spare
	char	Steer;		// front wheel steer in degrees (right positive)

	byte	ThrBrk;		// high 4 bits: throttle    / low 4 bits: brake (0 to 15)
	byte	CluHan;		// high 4 bits: clutch      / low 4 bits: handbrake (0 to 15)
	byte	GearSp;		// high 4 bits: gear (15=R) / low 4 bits: spare
	byte	Speed;		// m/s

	byte	Direction;	// car's motion if Speed > 0: 0 = world y direction, 128 = 180 deg
	byte	Heading;	// direction of forward axis: 0 = world y direction, 128 = 180 deg
	char	AccelF;		// m/s^2 longitudinal acceleration (forward positive)
	char	AccelR;		// m/s^2 lateral acceleration (right positive)

	short	X;			// position (1 metre = 16)
	short	Y;			// position (1 metre = 16)
};
 */

data class CarContact(
    val playerId: UByte,
    val info: UByte,
    val steeringAngle: Byte,
    val throttle: UByte,
    val brake: UByte,
    val clutch: UByte,
    val handbrake: UByte,
    val gear: UByte,
    val speed: UByte,
    val direction: UByte,
    val heading: UByte,
    val accelForward: Byte,
    val accelRight: Byte,
    val x: Short,
    val y: Short
) {

    constructor(data: ByteArray) : this(
        playerId = data[0].toUByte(),
        info = data[1].toUByte(),
        steeringAngle = data[3],
        throttle = ((data[4].toUInt() and 0xF0u) shr 4).toUByte(),
        brake = (data[4].toUInt() and 0x0Fu).toUByte(),
        clutch = ((data[5].toUInt() and 0xF0u) shr 4).toUByte(),
        handbrake = (data[5].toUInt() and 0x0Fu).toUByte(),
        gear = ((data[6].toUInt() and 0xF0u) shr 4).toUByte(),
        speed = data[7].toUByte(),
        direction = data[8].toUByte(),
        heading = data[9].toUByte(),
        accelForward = data[10],
        accelRight = data[11],
        x = data.getShortAt(12),
        y = data.getShortAt(14)
    )
}