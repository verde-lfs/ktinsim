package lfs.ktinsim.packets

import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.put
import lfs.ktinsim.toByteArray
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct IS_ISI // InSim Init - packet to initialise the InSim system
{
	byte	Size;		// 44
	byte	Type;		// ISP_ISI
	byte	ReqI;		// If non-zero LFS will send an IS_VER packet
	byte	Zero;		// 0

	word	UDPPort;	// Port for UDP replies from LFS (0 to 65535)
	word	Flags;		// Bit flags for options (see below)

	byte	InSimVer;	// The INSIM_VERSION used by your program
	byte	Prefix;		// Special host message prefix character
	word	Interval;	// Time in ms between NLP or MCI (0 = none)

	char	Admin[16];	// Admin password (if set in LFS)
	char	IName[16];	// A short name for your program
};
 */
data class InitPacket(
    val password: String = "password",
    val programName: String = "ktinsim",
    val getVersionPacketOnInit: Boolean = true,
    val udpPort: Int = 0,
    val isLocal: Boolean = false,
    val keepColoursInMSO: Boolean = false,
    val receivePlayerInfo: PlayerInfo = PlayerInfo.NONE,
    val playerInfoInterval: Short = if (receivePlayerInfo == PlayerInfo.NONE) 0 else 200,
    val receiveCON: Boolean = false,
    val receiveOBH: Boolean = false,
    val receiveHLV: Boolean = false,
    val receiveAXMOnLoad: Boolean = false,
    val receiveAXMOnEdit: Boolean = false,
    val receiveJoinRequests: Boolean = false,
) : OutgoingPacket {
    companion object {
        const val SIZE: Int = 44
        val TYPE : Byte = InSim.PacketTypes.ISP_ISI.byte()
    }

    val receiveNLP: Boolean = receivePlayerInfo == PlayerInfo.NLP
    val receiveMCI: Boolean = receivePlayerInfo == PlayerInfo.MCI
    
    private fun getFlags(): Short {
        return arrayOf(isLocal, keepColoursInMSO, receiveNLP,
            receiveMCI, receiveCON, receiveOBH,
            receiveHLV, receiveAXMOnLoad, receiveAXMOnEdit, receiveJoinRequests)
            .foldIndexed(0) {
                index: Int, acc: Int, b: Boolean ->
                    acc + (if (b) 0x4 shl index else 0)
            }.toShort()
    }

    override fun getByteBuffer() : ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE) // type
        	.put(if (getVersionPacketOnInit) 1 else 0)
        	.put(0)


        val tempFlags = getFlags()
        result.put(udpPort.toUShort().toByteArray())
            .put(tempFlags.toUShort().toByteArray())

        result.put(InSim.VERSION)
        	.put(' '.code.toByte()) // prefix
        	.put(playerInfoInterval.toUShort().toByteArray())

        for (line in arrayOf(password, programName)) {
            result.put(line.toLFSBytes().getFixedSizeByteArray(16))
        }
        return result
    }

    enum class PlayerInfo { NONE, NLP, MCI }
}