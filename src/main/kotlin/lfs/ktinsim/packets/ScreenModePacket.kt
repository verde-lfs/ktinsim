package lfs.ktinsim.packets

import lfs.ktinsim.toByteArray
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct IS_MOD // MODe: send to LFS to change screen mode
{
	byte	Size;		// 20
	byte	Type;		// ISP_MOD
	byte	ReqI;		// 0
	byte	Zero;

	int		Bits16;		// set to choose 16-bit
	int		RR;			// refresh rate - zero for default
	int		Width;		// 0 means go to window
	int		Height;		// 0 means go to window
};
 */

data class ScreenModePacket(
    val mode16bit: Int,
    val refreshRate: Int,
    val width: Int,
    val height: Int
) : OutgoingPacket {
    companion object {
        const val SIZE : Int = 20
        val TYPE : Byte = InSim.PacketTypes.ISP_MOD.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        	.put(mode16bit.toUInt().toByteArray())
        	.put(refreshRate.toUInt().toByteArray())
        	.put(width.toUInt().toByteArray())
        	.put(height.toUInt().toByteArray())
        return result
    }


}