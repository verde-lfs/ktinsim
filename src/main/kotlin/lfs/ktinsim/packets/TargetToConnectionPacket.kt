package lfs.ktinsim.packets

import lfs.ktinsim.InSim
import java.nio.ByteBuffer

data class TargetToConnectionPacket(
    val subType: SubType,
    val connectionId: Byte = 0,
    private var requestId : Byte = -1
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 8
        val TYPE : Byte = InSim.PacketTypes.ISP_TTC.byte()
    }

    private var b1 : Byte = 0
    private var b2 : Byte = 0
    private var b3 : Byte = 0

    init {
        if (requestId == (-1).toByte()) {
            requestId = when (subType) {
                SubType.NONE, SubType.SEL_STOP -> 0
                else -> 1
            }
        }
    }

    enum class SubType: ByteEnum // the fourth byte of an IS_TTC packet is one of these
    {
        NONE,		//  0					: not used
        SEL,		//  1 - info request	: send IS_AXM for a layout editor selection
        SEL_START,	//  2 - info request	: send IS_AXM every time the selection changes
        SEL_STOP;	//  3 - instruction		: switch off IS_AXM requested by TTC_SEL_START
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(requestId) // RequestID, non-zero for info requests
        	.put(subType.byte())

        result.put(connectionId)
        	.put(b1)
        	.put(b2)
        	.put(b3)
        return result
    }

}