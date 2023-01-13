package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.put
import lfs.ktinsim.toByteArray
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

data class SmallPacket(
    val subType: SubType,
    val value: UInt,
    val requestId: UByte = 0u,
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 8
        val TYPE : Byte = InSim.PacketTypes.ISP_SMALL.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        subType = get<SubType>(data[3]),
        value = data.getUIntAt(4)
    )

    enum class SubType : ByteEnum // the fourth byte of an IS_SMALL packet is one of these
    {
        NONE,		//  0					: not used
        SSP,		//  1 - instruction		: start sending positions
        SSG,		//  2 - instruction		: start sending gauges
        VTA,		//  3 - report			: vote action
        TMS,		//  4 - instruction		: time stop
        STP,		//  5 - instruction		: time step
        RTP,		//  6 - info			: race time packet (reply to GTH)
        NLI,		//  7 - instruction		: set node lap interval
        ALC,		//  8 - both ways		: set or get allowed cars (TINY_ALC)
        LCS;		//  9 - instruction		: set local car switches (lights, horn, siren)

    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(requestId) // RequestID, non-zero for info requests
        	.put(subType.byte())

        result.put(value.toByteArray())
        return result
    }

}