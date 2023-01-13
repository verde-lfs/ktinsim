package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.put
import lfs.ktinsim.toByteArray
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*

struct IS_MAL // Mods ALlowed - variable size
{
	byte	Size;		// 8 + NumM * 4
	byte	Type;		// ISP_MAL
	byte	ReqI;		// 0 unless this is a reply to a TINY_MAL request
	byte	NumM;		// number of mods in this packet

	byte	UCID;		// unique id of the connection that updated the list
	byte	Flags;		// zero (for now)
	byte	Sp2;
	byte	Sp3;

	unsigned	SkinID	[MAL_MAX_MODS]; // SkinID of each mod in compressed format, 0 to MAL_MAX_MODS (NumM)
};

const int MAL_MAX_MODS = 120;
 */

@OptIn(ExperimentalUnsignedTypes::class)
data class ModsAllowedPacket(
    val requestId: UByte,
    val skinIds: UIntArray,
    val connectionId: UByte = 0u
): OutgoingPacket {
    companion object {
        val TYPE : Byte = InSim.PacketTypes.ISP_MAL.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        skinIds = UIntArray(data[3].toInt()) {
            data.getUIntAt(8+it*4)
        },
        connectionId = data[4].toUByte()
    )

    override fun getByteBuffer(): ByteBuffer {
        val packetSize = 8 + skinIds.size * 4
        val result = initByteBuffer(packetSize)
        result.put(TYPE)
        	.put(requestId)
        	.put(skinIds.size.toByte())

        result.put(connectionId)
        	.put(0)
        	.put(0)
        	.put(0)

        for (id in skinIds) {
            result.put(id.toByteArray())
        }
        return result
    }

}