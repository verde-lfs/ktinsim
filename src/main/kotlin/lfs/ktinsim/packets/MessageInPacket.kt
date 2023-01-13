package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import java.nio.ByteBuffer

/*

struct IS_MST // MSg Type - send to LFS to type message or command
{
	byte	Size;		// 68
	byte	Type;		// ISP_MST
	byte	ReqI;		// 0
	byte	Zero;

	char	Msg[64];	// last byte must be zero
};
*/

data class MessageInPacket(val message: String) : OutgoingPacket {

    companion object {
        const val SIZE = 68
        val TYPE : Byte = InSim.PacketTypes.ISP_MST.byte()
    }


    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        result.put(message.toLFSBytes().getFixedSizeByteArray(63))
        return result
    }


}