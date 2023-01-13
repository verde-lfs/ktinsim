package lfs.ktinsim.packets

import lfs.ktinsim.data.IntVector
import lfs.ktinsim.data.InGameCamera
import lfs.ktinsim.getFloatAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.toByteArray
import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// DIRECT camera control
// ---------------------

// A Camera Position Packet can be used for LFS to report a camera position and state.
// An InSim program can also send one to set LFS camera position in game or SHIFT+U mode.

// Type: "Vec": 3 ints (X, Y, Z) - 65536 means 1 metre

struct IS_CPP // Cam Pos Pack - Full camera packet (in car OR SHIFT+U mode)
{
	byte	Size;		// 32
	byte	Type;		// ISP_CPP
	byte	ReqI;		// instruction: 0 / or reply: ReqI as received in the TINY_SCP
	byte	Zero;

	Vec		Pos;		// Position vector

	word	H;			// heading - 0 points along Y axis
	word	P;			// pitch
	word	R;			// roll

	byte	ViewPLID;	// Unique ID of viewed player (0 = none)
	byte	InGameCam;	// InGameCam (as reported in StatePack)

	float	FOV;		// 4-byte float: FOV in degrees

	word	Time;		// Time in ms to get there (0 means instant)
	word	Flags;		// ISS state flags (see below)
};

// The ISS state flags that can be set are:

// ISS_SHIFTU			- in SHIFT+U mode
// ISS_SHIFTU_FOLLOW	- FOLLOW view
// ISS_VIEW_OVERRIDE	- override user view

// On receiving this packet, LFS will set up the camera to match the values in the packet,
// including switching into or out of SHIFT+U mode depending on the ISS_SHIFTU flag.

// If ISS_VIEW_OVERRIDE is set, the in-car view Heading, Pitch, Roll and FOV [not smooth]
// can be set using this packet.  Otherwise normal in game control will be used.

// Position vector (Vec Pos) - in SHIFT+U mode, Pos can be either relative or absolute.

// If ISS_SHIFTU_FOLLOW is set, it's a following camera, so the position is relative to
// the selected car.  Otherwise, the position is absolute, as used in normal SHIFT+U mode.

// NOTE: Set InGameCam or ViewPLID to 255 to leave that option unchanged.

 */

data class CameraPositionPacket(
    val requestId: UByte,
    val position: IntVector,
    val heading: UShort,
    val pitch: UShort,
    val roll: UShort,

    val playerId: UByte,
    val ingameCamera: InGameCamera? = null,
    val fov: Float,
    val time: UShort,
    val flags: UShort,
): OutgoingPacket {
    companion object {
        const val SIZE = 32
        val TYPE = InSim.PacketTypes.ISP_CPP.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        position = IntVector(data, 4),
        heading = data.getUShortAt(16),
        pitch = data.getUShortAt(18),
        roll = data.getUShortAt(20),

        playerId = data[22].toUByte(),
        ingameCamera = get<InGameCamera>(data[23]),
        fov = data.getFloatAt(24),
        time = data.getUShortAt(28),
        flags = data.getUShortAt(30)
    )

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
            .put(requestId)
            .put(0)
            .put(position.x.toUInt().toByteArray())
            .put(position.y.toUInt().toByteArray())
            .put(position.z.toUInt().toByteArray())
            .putShort(heading.toShort())
            .putShort(pitch.toShort())
            .putShort(roll.toShort())
            .put(playerId)
            .put(ingameCamera?.byte()?.toUByte() ?: 255u)
            .putFloat(fov)
            .putShort(time.toShort())
            .putShort(flags.toShort())

        return result
    }
}