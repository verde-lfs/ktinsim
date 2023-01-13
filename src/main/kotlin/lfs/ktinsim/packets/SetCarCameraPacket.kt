package lfs.ktinsim.packets

import lfs.ktinsim.data.InGameCamera
import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// IN GAME camera control
// ----------------------

// You can set the viewed car and selected camera directly with a special packet
// These are the states normally set in game by using the TAB and V keys

struct IS_SCC // Set Car Camera - Simplified camera packet (not SHIFT+U mode)
{
	byte	Size;		// 8
	byte	Type;		// ISP_SCC
	byte	ReqI;		// 0
	byte	Zero;

	byte	ViewPLID;	// Unique ID of player to view
	byte	InGameCam;	// InGameCam (as reported in StatePack)
	byte	Sp2;
	byte	Sp3;
};

// NOTE: Set InGameCam or ViewPLID to 255 to leave that option unchanged.

 */

data class SetCarCameraPacket(
    val playerId: UByte,
    val ingameCamera: InGameCamera? = null
): OutgoingPacket
{
    companion object {
        val TYPE = InSim.PacketTypes.ISP_SCC.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(8)
        result.put(TYPE)
            .put(0)
            .put(0)

        result.put(playerId)
            .put(ingameCamera?.byte()?.toUByte() ?: 255u)
            .put(0)
            .put(0)

        return result
    }
}