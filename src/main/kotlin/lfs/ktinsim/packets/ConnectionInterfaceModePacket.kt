package lfs.ktinsim.packets

import lfs.ktinsim.put
import lfs.ktinsim.InSim
import java.nio.ByteBuffer

/*
struct IS_CIM // Conn Interface Mode
{
	byte	Size;		// 8
	byte	Type;		// ISP_CIM
	byte	ReqI;		// 0
	byte	UCID;		// connection's unique id (0 = local)

	byte	Mode;		// mode identifier (see below)
	byte	SubMode;	// submode identifier (see below)
	byte	SelType;	// selected object type (see below)
	byte	Sp3;
};

// Mode identifiers

enum
{
	CIM_NORMAL,				// 0 - not in a special mode
	CIM_OPTIONS,			// 1
	CIM_HOST_OPTIONS,		// 2
	CIM_GARAGE,				// 3
	CIM_CAR_SELECT,			// 4
	CIM_TRACK_SELECT,		// 5
	CIM_SHIFTU,				// 6 - free view mode
	CIM_NUM
};

// Submode identifiers for CIM_NORMAL

enum
{
	NRM_NORMAL,
	NRM_WHEEL_TEMPS,		// F9
	NRM_WHEEL_DAMAGE,		// F10
	NRM_LIVE_SETTINGS,		// F11
	NRM_PIT_INSTRUCTIONS,	// F12
	NRM_NUM
};

// SubMode identifiers for CIM_GARAGE

enum
{
	GRG_INFO,
	GRG_COLOURS,
	GRG_BRAKE_TC,
	GRG_SUSP,
	GRG_STEER,
	GRG_DRIVE,
	GRG_TYRES,
	GRG_AERO,
	GRG_PASS,
	GRG_NUM
};

// SubMode identifiers for CIM_SHIFTU

enum
{
	FVM_PLAIN,				// no buttons displayed
	FVM_BUTTONS,			// buttons displayed (not editing)
	FVM_EDIT,				// edit mode
	FVM_NUM
};

// SelType is the selected object type or zero if unselected
// It may be an AXO_x as in ObjectInfo or one of these:

const int MARSH_IS_CP		= 252; // insim checkpoint
const int MARSH_IS_AREA		= 253; // insim circle
const int MARSH_MARSHAL		= 254; // restricted area
const int MARSH_ROUTE		= 255; // route checker

 */

data class ConnectionInterfaceModePacket(
    val connectionId: UByte,
    val mode: Mode,
    var submode: SubMode? = null,
    val selectedObjType: UByte
) : OutgoingPacket {
    companion object {
        const val SIZE : Int = 8
        val TYPE : Byte = InSim.PacketTypes.ISP_CIM.byte()
    }

    constructor(data: ByteArray) : this(
        connectionId = data[3].toUByte(),
        mode = get<Mode>(data[4]),
        submode = null,
        selectedObjType = data[6].toUByte()
    ) {
        submode = when (mode) {
            Mode.NORMAL -> get<SubModeNormal>(data[5])
            Mode.GARAGE -> get<SubModeGarage>(data[5])
            Mode.SHIFTU -> get<SubModeShiftU>(data[5])
            else -> null
        }
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(SIZE)
        result.put(TYPE)
        	.put(0)
        	.put(connectionId)

        result.put(mode.byte())
        	.put(submode?.byte() ?: 0)
        	.put(selectedObjType)
        return result
    }

    enum class Mode : ByteEnum {
        NORMAL,				// 0 - not in a special mode
        OPTIONS,			// 1
        HOST_OPTIONS,		// 2
        GARAGE,				// 3
        CAR_SELECT,			// 4
        TRACK_SELECT,		// 5
        SHIFTU,				// 6 - free view mode
        NUM;
    }

    interface SubMode: ByteEnum

    enum class SubModeNormal : SubMode
    {
        NORMAL,
        WHEEL_TEMPS,		// F9
        WHEEL_DAMAGE,		// F10
        LIVE_SETTINGS,		// F11
        PIT_INSTRUCTIONS,	// F12
        NUM;

    }

    enum class SubModeGarage : SubMode
    {
        INFO,
        COLOURS,
        BRAKE_TC,
        SUSP,
        STEER,
        DRIVE,
        TYRES,
        AERO,
        PASS,
        NUM;
    }

    enum class SubModeShiftU : SubMode
    {
        PLAIN,				// no buttons displayed
        BUTTONS,			// buttons displayed (not editing)
        EDIT,				// edit mode
        NUM;

    }

    val MARSH_IS_CP = 252 // insim checkpoint
    val MARSH_IS_AREA = 253 // insim circle
    val MARSH_MARSHAL = 254 // restricted area
    val MARSH_ROUTE = 255 // route checker
}