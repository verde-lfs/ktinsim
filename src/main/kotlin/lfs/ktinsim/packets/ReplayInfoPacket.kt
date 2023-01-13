package lfs.ktinsim.packets

import lfs.ktinsim.getUIntAt
import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim
/*
// REPLAY CONTROL
// ==============

// You can load a replay or set the position in a replay with an IS_RIP packet.
// Replay positions and lengths are specified in hundredths of a second.
// LFS will reply with another IS_RIP packet when the request is completed.

struct IS_RIP // Replay Information Packet
{
	byte	Size;		// 80
	byte	Type;		// ISP_RIP
	byte	ReqI;		// request: non-zero / reply: same value returned
	byte	Error;		// 0 or 1 = OK / other values are listed below

	byte	MPR;		// 0 = SPR / 1 = MPR
	byte	Paused;		// request: pause on arrival / reply: paused state
	byte	Options;	// various options - see below
	byte	Sp3;

	unsigned	CTime;	// (hundredths) request: destination / reply: position
	unsigned	TTime;	// (hundredths) request: zero / reply: replay length

	char	RName[64];	// zero or replay name - last byte must be zero
};

// NOTE about RName:
// In a request, replay RName will be loaded.  If zero then the current replay is used.
// In a reply, RName is the name of the current replay, or zero if no replay is loaded.

// You can request an IS_RIP packet at any time with this IS_TINY:

// ReqI: non-zero		(returned in the reply)
// SubT: TINY_RIP		(Replay Information Packet)

// Error codes returned in IS_RIP replies:

enum
{
	RIP_OK,				//  0 - OK: completed instruction
	RIP_ALREADY,		//  1 - OK: already at the destination
	RIP_DEDICATED,		//  2 - can't run a replay - dedicated host
	RIP_WRONG_MODE,		//  3 - can't start a replay - not in a suitable mode
	RIP_NOT_REPLAY,		//  4 - RName is zero but no replay is currently loaded
	RIP_CORRUPTED,		//  5 - IS_RIP corrupted (e.g. RName does not end with zero)
	RIP_NOT_FOUND,		//  6 - the replay file was not found
	RIP_UNLOADABLE,		//  7 - obsolete / future / corrupted
	RIP_DEST_OOB,		//  8 - destination is beyond replay length
	RIP_UNKNOWN,		//  9 - unknown error found starting replay
	RIP_USER,			// 10 - replay search was terminated by user
	RIP_OOS,			// 11 - can't reach destination - SPR is out of sync
};

// Options byte: some options

#define RIPOPT_LOOP			1		// replay will loop if this bit is set
#define RIPOPT_SKINS		2		// set this bit to download missing skins
#define RIPOPT_FULL_PHYS	4		// use full physics when searching an MPR

// NOTE: RIPOPT_FULL_PHYS makes MPR searching much slower so should not normally be used.
// This flag was added to allow high accuracy MCI packets to be output when fast forwarding.

 */

data class ReplayInfoPacket(
    val requestId: UByte,
    val error: UByte,
    val isMPR: Boolean,
    val paused: UByte,
    val options: UByte,
    val currentTime: UInt,
    val totalTime: UInt,
    val replayName: String
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_RIP.byte()
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        error = data[3].toUByte(),
        isMPR = data[4].toInt() == 1,
        paused = data[5].toUByte(),
        options = data[6].toUByte(),
        currentTime = data.getUIntAt(8),
        totalTime = data.getUIntAt(12),
        replayName = data.getASCIIString(16, 64),
    )
}