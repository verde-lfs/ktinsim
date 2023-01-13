package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.data.InGameCamera

/*
// IS_CCH: Camera CHange

// To track cameras you need to consider 3 points

// 1) The default camera: VIEW_DRIVER
// 2) Player flags: CUSTOM_VIEW means VIEW_CUSTOM at start or pit exit
// 3) IS_CCH: sent when an existing driver changes camera

struct IS_CCH // Camera CHange
{
	byte	Size;		// 8
	byte	Type;		// ISP_CCH
	byte	ReqI;		// 0
	byte	PLID;		// player's unique id

	byte	Camera;		// view identifier (see below)
	byte	Sp1;
	byte	Sp2;
	byte	Sp3;
};
 */

data class CameraChangePacket(
    val playerId: UByte,
    val viewId: InGameCamera
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_CCH.byte()
    }

    constructor(data: ByteArray) : this(data[3].toUByte(), get<InGameCamera>(data[4]))
}