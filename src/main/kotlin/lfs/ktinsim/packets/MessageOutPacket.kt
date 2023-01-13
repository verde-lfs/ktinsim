package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.utils.LFSTextUtils.toUTF8String

/*
struct IS_MSO // MSg Out - system messages and user messages - variable size
{
	byte	Size;		// 12, 16, 20... 136 depending on Msg
	byte	Type;		// ISP_MSO
	byte	ReqI;		// 0
	byte	Zero;

	byte	UCID;		// connection's unique id (0 = host)
	byte	PLID;		// player's unique id (if zero, use UCID)
	byte	UserType;	// set if typed by a user (see User Values below)
	byte	TextStart;	// first character of the actual text (after player name)

	char	Msg[128];	// 4, 8, 12... 128 characters - last byte is zero
};
 */

data class MessageOutPacket(
    var connectionId: UByte,
    var playerId: UByte,
    var userType: UserType,
    var message: String
) : Packet {

    companion object {
        val TYPE : Byte = InSim.PacketTypes.ISP_MSO.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[4].toUByte(),
        playerId = data[5].toUByte(),
        userType = get<UserType>(data[6]),
        message = data.toUTF8String(7, data[0]*4-7)
    )

    enum class UserType : ByteEnum {
        SYSTEM,			// 0 - system message
        USER,			// 1 - normal visible user message
        PREFIX,			// 2 - hidden message starting with special prefix (see ISI)
        O,				// 3 - hidden message typed on local pc with /o command
        NUM;
    }
}
/*
data class MessageOutPacket(
        val message: String,
        val connectionId: Byte = 0,
        val playerId: Byte = 0,
        val userType: UserType = UserType.SYSTEM) : Packet {

    companion object {
        val TYPE : Byte = InSim.PacketTypes.ISP_MSO.ordinal.toByte()
    }




    override fun getByteBuffer(): ByteBuffer {
        val messageSize = (message.length / 4 + 1) * 4
        val packetSize = messageSize + 8
        val result = initByteBuffer(packetSize)
        result.lfs.ktinsim.put(TYPE)
        result.lfs.ktinsim.put(0)
        result.lfs.ktinsim.put(0)

        result.lfs.ktinsim.put(connectionId)
        result.lfs.ktinsim.put(playerId)
        result.lfs.ktinsim.put(userType.byte())

        result.lfs.ktinsim.put(message.lfs.ktinsim.getFixedSizeByteArray(128))
        result.lfs.ktinsim.put(0)
        return result
    }


}

 */