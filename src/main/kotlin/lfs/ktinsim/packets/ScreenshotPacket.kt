package lfs.ktinsim.packets

import lfs.ktinsim.getASCIIString
import lfs.ktinsim.getFixedSizeByteArray
import lfs.ktinsim.put
import lfs.ktinsim.utils.LFSTextUtils.toLFSBytes
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
// SCREENSHOTS
// ===========

// You can instuct LFS to save a screenshot in data\shots using the IS_SSH packet.
// It will be saved as bmp / jpg / png as set in Misc Options.
// Name can be a filename (excluding extension) or zero - LFS will create a name.
// LFS will reply with another IS_SSH when the request is completed.

struct IS_SSH // ScreenSHot
{
	byte	Size;		// 40
	byte	Type;		// ISP_SSH
	byte	ReqI;		// request: non-zero / reply: same value returned
	byte	Error;		// 0 = OK / other values are listed below

	byte	Sp0;		// 0
	byte	Sp1;		// 0
	byte	Sp2;		// 0
	byte	Sp3;		// 0

	char	Name[32];	// name of screenshot file - last byte must be zero
};

// Error codes returned in IS_SSH replies:

enum
{
	SSH_OK,				//  0 - OK: completed instruction
	SSH_DEDICATED,		//  1 - can't save a screenshot - dedicated host
	SSH_CORRUPTED,		//  2 - IS_SSH corrupted (e.g. Name does not end with zero)
	SSH_NO_SAVE,		//  3 - could not save the screenshot
};

 */

data class ScreenshotPacket(
    val screenshotName: String = "",
    val requestId: UByte = 1u,
    val error: Error = Error.OK
): OutgoingPacket
{
    companion object {
        const val SIZE : Int = 40
        val TYPE : Byte = InSim.PacketTypes.ISP_SSH.byte()
    }

    constructor(data: ByteArray) : this(
        screenshotName = data.getASCIIString(8, 32),
        requestId = data[2].toUByte(),
        error = get<Error>(data[3])
    )

    init {
        // TODO("Add more checks")
//        if (screenshotName.isEmpty()) {
//            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//            screenshotName = LocalDateTime.now().format(formatter)
//        }
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(requestId)
            .put(error.byte())

        result.put(ByteArray(4))

        result.put(screenshotName.toLFSBytes().getFixedSizeByteArray(31))
        return result
    }

    enum class Error : ByteEnum
    {
        OK,				//  0 - OK: completed instruction
        DEDICATED,		//  1 - can't save a screenshot - dedicated host
        CORRUPTED,		//  2 - IS_SSH corrupted (e.g. Name does not end with zero)
        NO_SAVE;		//  3 - could not save the screenshot
    }

}