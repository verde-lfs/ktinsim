package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import lfs.ktinsim.utils.LFSTextUtils.toUTF8String

/*
struct IS_CPR // Conn Player Rename
{
	byte	Size;		// 36
	byte	Type;		// ISP_CPR
	byte	ReqI;		// 0
	byte	UCID;		// unique id of the connection

	char	PName[24];	// new name
	char	Plate[8];	// number plate - NO ZERO AT END!
};
 */

data class ConnectionPlayerRenamePacket(
    val connectionId: UByte,
    val playerName: String,
    val numberPlate: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_CPR.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[3].toUByte(),
        playerName = data.toUTF8String(4, 24),
        numberPlate = data.toUTF8String(28, 6)
    )
}