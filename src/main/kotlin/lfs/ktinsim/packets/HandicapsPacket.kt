package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct CarHCP // Car handicaps in 2 bytes - there is an array of these in the HCP (below)
{
	byte	H_Mass;		// 0 to 200 - added mass (kg)
	byte	H_TRes;		// 0 to  50 - intake restriction
};

struct IS_HCP // HandiCaPs
{
	byte	Size;		// 68
	byte	Type;		// ISP_HCP
	byte	ReqI;		// 0
	byte	Zero;

	CarHCP	Info[32];	// H_Mass and H_TRes for each car: XF GTI = 0 / XR GT = 1 etc
};
 */

data class HandicapsPacket(val carHandicaps: Array<CarHandicap>): OutgoingPacket {

    companion object {
        const val SIZE : Int = 68
        val TYPE : Byte = InSim.PacketTypes.ISP_HCP.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        for (handicap in carHandicaps) {
            result.put(handicap.addedMass)
            result.put(handicap.intakeRestriction)
        }
        return result
    }

}