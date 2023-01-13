package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct IS_SFP // State Flags Pack
{
	byte	Size;		// 8
	byte	Type;		// ISP_SFP
	byte	ReqI;		// 0
	byte	Zero;

	word	Flag;		// the state to set
	byte	OffOn;		// 0 = off / 1 = on
	byte	Sp3;		// spare
};
 */

data class StateFlagPacket(
    val state: Short,
    val enabled: Boolean
) : OutgoingPacket {
    companion object {
        const val SIZE : Int = 8
        val TYPE : Byte = InSim.PacketTypes.ISP_SFP.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        result.putShort(state)
        	.put(if (enabled) 1 else 0 )
        	.put(0)
        return result
    }


}