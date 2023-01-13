package lfs.ktinsim.packets


import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.roundBy
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.InSim

import java.nio.ByteBuffer

/*
struct IS_MTC // Msg To Connection - hosts only - send to a connection / a player / all
{
	byte	Size;		// 8 + TEXT_SIZE (TEXT_SIZE = 4, 8, 12... 128)
	byte	Type;		// ISP_MTC
	byte	ReqI;		// 0
	byte	Sound;		// sound effect (see Message Sounds below)

	byte	UCID;		// connection's unique id (0 = host / 255 = all)
	byte	PLID;		// player's unique id (if zero, use UCID)
	byte	Sp2;
	byte	Sp3;

	char	Text[TEXT_SIZE]; // up to 128 characters of text - last byte must be zero
};
*/

data class MessageToConnectionPacket(
    val message: String,
    val sound : MessageSound = MessageSound.SILENT,
    val connectionId : Byte = 0,
    val playerId : Byte = 0
) : OutgoingPacket {

    companion object {
        val TYPE : Byte = InSim.PacketTypes.ISP_MTC.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val messageBytes = message.toLFSBytes()
        val messageSize = minOf(messageBytes.size.roundBy(4), 128)
        val packetSize = messageSize + 8
        val result = initByteBuffer(packetSize)
        result.put(TYPE)
        	.put(0)
        	.put(sound.byte())

        result.put(connectionId)
        	.put(playerId)
        	.put(0)
        	.put(0)
        result.put(messageBytes.getFixedSizeByteArray(messageSize-1))
        return result
    }
}