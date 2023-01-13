package lfs.ktinsim.packets

import lfs.ktinsim.put
import java.nio.ByteBuffer
import lfs.ktinsim.InSim

/*
// OBJECT CONTROL - currently used for switching start lights
// ==============

struct IS_OCO // Object COntrol
{
	byte	Size;		// 8
	byte	Type;		// ISP_OCO
	byte	ReqI;		// 0
	byte	Zero;

	byte	OCOAction;	// see below
	byte	Index;		// see below
	byte	Identifier;	// identify particular start lights objects (0 to 63 or 255 = all)
	byte	Data;		// see below
};

// OCOAction byte

enum
{
	OCO_ZERO,			// reserved
	OCO_1,				//
	OCO_2,				//
	OCO_3,				//
	OCO_LIGHTS_RESET,	// give up control of all lights
	OCO_LIGHTS_SET,		// use Data byte to set the bulbs
	OCO_LIGHTS_UNSET,	// give up control of the specified lights
	OCO_NUM
};

// Index byte specifies which lights you want to override

// Currently the following values are supported:

// AXO_START_LIGHTS (149)	// overrides temporary start lights in the layout
#define OCO_INDEX_MAIN 240	// special value to override the main start light system

// Identifier byte can be used to override groups of temporary start lights

// It refers to the temporary lights identifier (0 to 63) seen in the layout editor

// Data byte specifies particular bulbs using the low 4 bits

// Bulb bit values for the currently available lights:

// OCO_INDEX_MAIN		AXO_START_LIGHTS

// bit 0 (1): red1		bit 0 (1): red
// bit 1 (2): red2		bit 1 (2): amber
// bit 2 (4): red3		-
// bit 3 (8): green		bit 3 (8): green
 */

data class ObjectControlPacket(
    val action: Action,
    val index: UByte,
    val id: UByte,
    val data: UByte,
): OutgoingPacket
{
    companion object {
        val TYPE = InSim.PacketTypes.ISP_OCO.byte()
    }

    override fun getByteBuffer(): ByteBuffer {
        val result = initByteBuffer(8)
        result.put(TYPE)
            .put(0)
            .put(action.byte())
            .put(index)
            .put(id)
            .put(data)

        return result
    }

    enum class Action: ByteEnum {
        RESERVED_0,			// reserved
        RESERVED_1,				//
        RESERVED_2,				//
        RESERVED_3,				//
        LIGHTS_RESET,	// give up control of all lights
        LIGHTS_SET,		// use Data byte to set the bulbs
        LIGHTS_UNSET,	// give up control of the specified lights
        NUM;
    }
}