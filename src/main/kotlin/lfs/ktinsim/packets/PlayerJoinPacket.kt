package lfs.ktinsim.packets

import lfs.ktinsim.getUShortAt
import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
struct IS_NPL // New PLayer joining race (if PLID already exists, then leaving pits)
{
	byte	Size;		// 76
	byte	Type;		// ISP_NPL
	byte	ReqI;		// 0 unless this is a reply to an TINY_NPL request
	byte	PLID;		// player's newly assigned unique id

	byte	UCID;		// connection's unique id
	byte	PType;		// bit 0: female / bit 1: AI / bit 2: remote
	word	Flags;		// player flags

	char	PName[24];	// nickname
	char	Plate[8];	// number plate - NO ZERO AT END!

	char	CName[4];	// car name
	char	SName[16];	// skin name - MAX_CAR_TEX_NAME
	byte	Tyres[4];	// compounds

	byte	H_Mass;		// added mass (kg)
	byte	H_TRes;		// intake restriction
	byte	Model;		// driver model
	byte	Pass;		// passengers byte

	byte	RWAdj;		// low 4 bits: tyre width reduction (rear)
	byte	FWAdj;		// low 4 bits: tyre width reduction (front)
	byte	Sp2;
	byte	Sp3;

	byte	SetF;		// setup flags (see below)
	byte	NumP;		// number in race - ZERO if this is a join request
	byte	Config;		// configuration (see below)
	byte	Fuel;		// /showfuel yes: fuel percent / no: 255
};

// NOTE: PType bit 0 (female) is not reported on dedicated host as humans are not loaded
// You can use the driver model byte instead if required (and to force the use of helmets)

// Setup flags (for SetF byte)

#define SETF_SYMM_WHEELS	1
#define SETF_TC_ENABLE		2
#define SETF_ABS_ENABLE		4

// Configuration (Config byte)

// UF1 / LX4 / LX6: 0 = DEFAULT / 1 = OPEN ROOF
// GTR racing cars: 0 = DEFAULT / 1 = ALTERNATE
 */

data class PlayerJoinPacket(
    val requestId: UByte,
    val playerId: UByte,
    val connectionId: UByte,
    val playerType: UByte,
    val flags: List<PlayerFlags>,

    val nickName: String,
    val numberPlate: String,
    val carName: String,
    val skinName: String,
    val tyres: List<TyreCompound>,

    val addedMass: UByte,
    val intakeRestriction: UByte,
    val driverModel: UByte,
    val passengers: UByte,

    val tyreWidthReductionRear: UByte,
    val tyreWidthReductionFront: UByte,

    val setupFlags: UByte,
    val playersInRace: UByte,
    val config: UByte,
    val fuel: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_NPL.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        playerId = data[3].toUByte(),
        connectionId = data[4].toUByte(),
        playerType = data[5].toUByte(),
        flags = PlayerFlags.getList(data.getUShortAt(6).toInt()),
        nickName = data.getASCIIString(8, 24),
        numberPlate = data.getASCIIString(32, 8),
        carName = data.getASCIIString(40, 4),
        skinName = data.getASCIIString(44, 16),
        tyres = TyreCompound.getList(data.sliceArray(60 until 64)),
        addedMass = data[64].toUByte(),
        intakeRestriction = data[65].toUByte(),
        driverModel = data[66].toUByte(),
        passengers = data[67].toUByte(),
        tyreWidthReductionRear = data[68].toUByte(),
        tyreWidthReductionFront = data[69].toUByte(),
        setupFlags = data[72].toUByte(),
        playersInRace = data[73].toUByte(),
        config = data[74].toUByte(),
        fuel = data[75].toUByte()
    )
}