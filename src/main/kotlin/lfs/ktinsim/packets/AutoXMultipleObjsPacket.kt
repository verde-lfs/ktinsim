package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim

import java.nio.ByteBuffer

/*
// AUTOCROSS OBJECTS - reporting / adding / removing
// =================

// Set the ISF_AXM_LOAD flag in the IS_ISI for info about objects when a layout is loaded.
// Set the ISF_AXM_EDIT flag in the IS_ISI for info about objects edited by user or InSim.

// You can also add or remove objects by sending IS_AXM packets.
// Some care must be taken with these - please read the notes below.

// You can also get (TTC_SEL) or set (PMO_SELECTION) the current editor selection.

const int AXM_MAX_OBJECTS = 60;

struct IS_AXM // AutoX Multiple objects - variable size
{
	byte	Size;		// 8 + NumO * 8
	byte	Type;		// ISP_AXM
	byte	ReqI;		// 0 unless this is a reply to a TINY_AXM request
	byte	NumO;		// number of objects in this packet

	byte	UCID;		// unique id of the connection that sent the packet
	byte	PMOAction;	// see below
	byte	PMOFlags;	// see below
	byte	Sp3;

	ObjectInfo	Info[AXM_MAX_OBJECTS]; // info about each object, 0 to AXM_MAX_OBJECTS (NumO)
};

// Values for PMOAction byte

enum
{
	PMO_LOADING_FILE,	// 0 - sent by the layout loading system only
	PMO_ADD_OBJECTS,	// 1 - adding objects (from InSim or editor)
	PMO_DEL_OBJECTS,	// 2 - delete objects (from InSim or editor)
	PMO_CLEAR_ALL,		// 3 - clear all objects (NumO must be zero)
	PMO_TINY_AXM,		// 4 - a reply to a TINY_AXM request
	PMO_TTC_SEL,		// 5 - a reply to a TTC_SEL request
	PMO_SELECTION,		// 6 - set a connection's layout editor selection
	PMO_POSITION,		// 7 - user pressed O without anything selected
	PMO_GET_Z,			// 8 - request Z values / reply with Z values
	PMO_NUM
};

// Info about the PMOFlags byte

#define PMO_FILE_END			1
#define PMO_MOVE_MODIFY			2
#define PMO_SELECTION_REAL		4
#define PMO_AVOID_CHECK			8

// PMO_FILE_END

// If PMO_FILE_END is set in a PMO_LOADING_FILE packet, LFS has reached the end of
// a layout file which it is loading.  The added objects will then be optimised.

// Optimised in this case means that static vertex buffers will be created for all
// objects, to greatly improve the frame rate.  The problem with this is that when
// there are many objects loaded, optimisation causes a significant glitch which can
// be long enough to cause a driver who is cornering to lose control and crash.

// PMO_FILE_END can also be set in an IS_AXM with PMOAction of PMO_ADD_OBJECTS.
// This causes all objects to be optimised.  It is important not to set PMO_FILE_END
// in every packet you send to add objects or you will cause severe glitches on the
// clients computers.  It is ok to have some objects on the track which are not
// optimised.  So if you have a few objects that are being removed and added
// occasionally, the best advice is not to request optimisation at all.  Only
// request optimisation (by setting PMO_FILE_END) if you have added so many objects
// that it is needed to improve the frame rate.

// NOTE 1) LFS makes sure that all objects are optimised when the race restarts.
// NOTE 2) In the 'more' section of SHIFT+U there is info about optimised objects.

// If you are using InSim to send many packets of objects (for example loading an
// entire layout through InSim) then you must take care of the bandwidth and buffer
// overflows.  You must not try to send all the objects at once.  It's probably good
// to use LFS's method of doing this: send the first packet of objects then wait for
// the corresponding IS_AXM that will be output when the packet is processed.  Then
// you can send the second packet and again wait for the IS_AXM and so on.

// PMO_MOVE_MODIFY

// When objects are moved or modified in the layout editor, two IS_AXM packets are
// sent.  A PMO_DEL_OBJECTS followed by a PMO_ADD_OBJECTS.  In this case the flag
// PMO_MOVE_MODIFY is set in the PMOFlags byte of both packets.

// PMO_SELECTION_REAL

// If you send an IS_AXM with PMOAction of PMO_SELECTION it is possible for it to be
// either a selection of real objects (as if the user selected several objects while
// holding the CTRL key) or a clipboard selection (as if the user pressed CTRL+C after
// selecting objects).  Clipboard is the default selection mode.  A real selection can
// be set by using the PMO_SELECTION_REAL bit in the PMOFlags byte.

// PMO_AVOID_CHECK

// If you send an IS_AXM with PMOAction of PMO_ADD_OBJECTS you may wish to set the
// UCID to one of the guest connections (for example if that user's action caused the
// objects to be added).  In this case some validity checks are done on the guest's
// computer which may report "invalid position" or "intersecting object" and delete
// the objects.  This can be avoided by setting the PMO_AVOID_CHECK bit.


// To request IS_AXM packets for all layout objects and circles send this IS_TINY:

// ReqI: non-zero		(returned in the reply)
// SubT: TINY_AXM		(request IS_AXM packets for the entire layout)

// LFS will send as many IS_AXM packets as needed to describe the whole layout.
// If there are no objects or circles, there will be one IS_AXM with zero NumO.
// The final IS_AXM packet will have the PMO_FILE_END flag set.


// To request an IS_AXM for a connection's layout editor selection send this IS_TTC:

// ReqI: non-zero		(returned in the reply)
// SubT: TTC_SEL		(request an IS_AXM for the current selection)
// UCID: connection		(0 = local / non-zero = guest)

// An IS_AXM with PMO_POSITION is sent with a single object in the packet if a user
// presses O without any object type selected.  Information only - no object is added.
// The only valid values in Info are X, Y, Zbyte and Heading.

// PMO_GET_Z can be used to request the resulting Zbyte values for given X, Y, Zbyte
// positions listed in the IS_AXM.  A similar reply (information only) will be sent
// with adjusted Zbyte values.  Index and Heading are ignored and set to zero in the
// reply.  Flags is set to 0x80 if Zbyte was successfully adjusted, zero if not.
// Suggested input values for Zbyte are either 240 to get the highest point at X, Y
// or you may use the approximate altitude (see layout file format).

 */

data class AutoXMultipleObjsPacket(
    val requestId: UByte,
    val objectsCount: UByte,
    val connectionId: UByte,
    val action: Action,
    val flags: Flags,
    val objectInfos: Array<ObjectInfo>
): OutgoingPacket
{
    companion object {
        val TYPE = InSim.PacketTypes.ISP_AXM.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        objectsCount = data[3].toUByte(),
        connectionId = data[4].toUByte(),
        action = get<Action>(data[5]),
        flags = Flags(data[6].toInt()),
        objectInfos = Array((data[0].toInt()*4 - 8)/8) {
            val start = 8 + it*8
            ObjectInfo(data.sliceArray(start until start+8))
        }
    )

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(8 + objectInfos.size * 8)
        result.put(TYPE)
            .put(requestId)
            .put(objectsCount)

        result.put(connectionId)
            .put(action.byte())
            .put(flags.toByte())
            .put(0)

        objectInfos.forEach {
            result.put(it.getByteArray())
        }
        return result
    }

    enum class Action : ByteEnum {
        LOADING_FILE,	// 0 - sent by the layout loading system only
        ADD_OBJECTS,	// 1 - adding objects (from InSim or editor)
        DEL_OBJECTS,	// 2 - delete objects (from InSim or editor)
        CLEAR_ALL,		// 3 - clear all objects (NumO must be zero)
        TINY_AXM,		// 4 - a reply to a TINY_AXM request
        TTC_SEL,		// 5 - a reply to a TTC_SEL request
        SELECTION,		// 6 - set a connection's layout editor selection
        POSITION,		// 7 - user pressed O without anything selected
        GET_Z,			// 8 - request Z values / reply with Z values
        NUM;
    }

    data class Flags(
        val fileEnd: Boolean = false,
        val moveModify: Boolean = false,
        val selectionReal: Boolean = false,
        val avoidCheck: Boolean = false
    ) {
        constructor(data: Int) : this(
            fileEnd = (data and 0x1) > 0,
            moveModify = (data and 0x2) > 0,
            selectionReal = (data and 0x4) > 0,
            avoidCheck = (data and 0x8) > 0
        )

        fun toByte() = arrayOf(fileEnd, moveModify, selectionReal, avoidCheck)
            .foldIndexed(0) {
                index: Int, acc: Int, b: Boolean ->
                    acc + (if (b) 1 shl index else 0)
            }.toByte()

    }
}