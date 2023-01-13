package lfs.ktinsim.packets

import lfs.ktinsim.InSim
/*
struct IS_FLG // FLaG (yellow or blue flag changed)
{
	byte	Size;		// 8
	byte	Type;		// ISP_FLG
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	OffOn;		// 0 = off / 1 = on
	byte	Flag;		// 1 = given blue / 2 = causing yellow
	byte	CarBehind;	// unique id of obstructed player
	byte	Sp3;
};
 */

data class FlagChangePacket(
    val playerId: UByte,
    val isOn: Boolean,
    val flag: Flag,
    val carBehind: UByte
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_FLG.byte()
    }

    constructor(data: ByteArray) : this(
        playerId = data[3].toUByte(),
        isOn = data[4].toInt() == 1,
        flag = get<Flag>(data[5]),
        carBehind = data[6].toUByte()
    )

    enum class Flag {
        NONE,
        GIVEN_BLUE,
        CAUSING_YELLOW
    }
}