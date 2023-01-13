package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.getFixedSizeByteArray
import java.nio.ByteBuffer

/*

struct IS_MSX // MSg eXtended - like MST but longer (not for commands)
{
	byte	Size;		// 100
	byte	Type;		// ISP_MSX
	byte	ReqI;		// 0
	byte	Zero;

	char	Msg[96];	// last byte must be zero
};
*/

data class ExtendedMessageInPacket(val message: String) : OutgoingPacket {

    companion object {
        const val SIZE : Int = 100
        val TYPE : Byte = InSim.PacketTypes.ISP_MSX.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        result.put(message.toLFSBytes().getFixedSizeByteArray(95))
        	.put(0)
        return result
    }


}