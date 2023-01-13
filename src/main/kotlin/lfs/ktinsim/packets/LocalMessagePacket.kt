package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import java.nio.ByteBuffer

/*
struct IS_MSL // MSg Local - message to appear on local computer only
{
	byte	Size;		// 132
	byte	Type;		// ISP_MSL
	byte	ReqI;		// 0
	byte	Sound;		// sound effect (see Message Sounds below)

	char	Msg[128];	// last byte must be zero
};
*/

data class LocalMessagePacket(
    val message: String,
    val sound : MessageSound = MessageSound.SILENT
) : OutgoingPacket {

    companion object {
        const val SIZE : Int = 132
        val TYPE : Byte = InSim.PacketTypes.ISP_MSL.byte()
    }


    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(sound.byte())

        result.put(message.toLFSBytes().getFixedSizeByteArray(127))
        return result
    }


}