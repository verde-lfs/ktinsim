package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.InSim

/*
struct IS_PIT // PIT stop (stop at pit garage)
{
	byte	Size;		// 24
	byte	Type;		// ISP_PIT
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	word	LapsDone;	// laps completed
	word	Flags;		// player flags

	byte	FuelAdd;	// /showfuel yes: fuel added percent / no: 255
	byte	Penalty;	// current penalty value (see below)
	byte	NumStops;	// number of pit stops
	byte	Sp3;

	byte	Tyres[4];	// tyres changed

	unsigned	Work;	// pit work
	unsigned	Spare;
};
 */

data class PitStopPacket(
    val playerId: UByte,
    val lapsDone: UShort,
    val flags: List<PlayerFlags>,
    val fuelAdd: UByte,
    val penalty: Penalty,
    val pitStops: UByte,
    val tyres: List<TyreCompound>,
    val pitWork: List<PitWorkFlag>
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_PIT.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        lapsDone = data.getUShortAt(4),
        flags = PlayerFlags.getList(data.getUShortAt(6).toUInt()),
        fuelAdd = data[8].toUByte(),
        penalty = get<Penalty>(data[9]),
        pitStops = data[10].toUByte(),
        tyres = TyreCompound.getList(data.sliceArray(12 until 16)),
        pitWork = PitWorkFlag.getList(data.getUIntAt(16))
    )
}