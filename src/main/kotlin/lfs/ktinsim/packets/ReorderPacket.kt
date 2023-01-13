package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// IS_REO: REOrder - this packet can be sent in either direction

// LFS sends one at the start of every race or qualifying session, listing the start order

// You can send one to LFS in two different ways, to specify the starting order:
// 1) In the race setup screen, to immediately rearrange the grid when the packet arrives
// 2) In game, just before a restart or exit, to specify the order on the restart or exit
// If you are sending an IS_REO in game, you should send it when you receive the SMALL_VTA
// informing you that the Vote Action (VOTE_END / VOTE_RESTART / VOTE_QUALIFY) is about
// to take place.  Any IS_REO received before the SMALL_VTA is sent will be ignored.

struct IS_REO // REOrder (when race restarts after qualifying)
{
	byte	Size;		// 44
	byte	Type;		// ISP_REO
	byte	ReqI;		// 0 unless this is a reply to an TINY_REO request
	byte	NumP;		// number of players in race

	byte	PLID[40];	// all PLIDs in new order
};
 */
@OptIn(ExperimentalUnsignedTypes::class)
data class ReorderPacket(
    val requestID: UByte = 0u,
    val playerIds: UByteArray
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 44
        val TYPE : Byte = InSim.PacketTypes.ISP_REO.byte()
    }

    constructor(data: ByteArray) : this(
        requestID = data[2].toUByte(),
        playerIds = UByteArray(data[3].toInt()) {
            it.toUByte()
        }
    )

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(playerIds.size.toByte())

        for (b in playerIds) {
            result.put(b)
        }
        return result
    }
}
