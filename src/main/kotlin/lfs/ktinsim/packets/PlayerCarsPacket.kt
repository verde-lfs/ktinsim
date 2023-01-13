package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct IS_PLC // PLayer Cars
{
	byte	Size;		// 12
	byte	Type;		// ISP_PLC
	byte	ReqI;		// 0
	byte	Zero;

	byte	UCID;		// connection's unique id (0 = host / 255 = all)
	byte	Sp1;
	byte	Sp2;
	byte	Sp3;

	unsigned	Cars;	// allowed cars - see below
};

 */

data class PlayerCarsPacket(
    val connectionId: UByte,
    val cars: List<Car>
): OutgoingPacket {
    companion object {
        const val SIZE : Int = 12
        val TYPE : Byte = InSim.PacketTypes.ISP_PLC.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(0)

        result.put(connectionId)
        	.put(0)
        	.put(0)
        	.put(0)

        result.put(cars.toByteArray())
        return result
    }
}