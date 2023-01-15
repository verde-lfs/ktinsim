package lfs.ktinsim.packets

import lfs.ktinsim.data.InGameCamera
import lfs.ktinsim.getFloatAt
import lfs.ktinsim.getUShortAt
import lfs.ktinsim.getASCIIString
import lfs.ktinsim.InSim

/*
struct IS_STA // STAte
{
    byte	Size;			// 28
    byte	Type;			// ISP_STA
    byte	ReqI;			// ReqI if replying to a request packet
    byte	Zero;

    float	ReplaySpeed;	// 4-byte float - 1.0 is normal speed

    word	Flags;			// ISS state flags (see below)
    byte	InGameCam;		// Which type of camera is selected (see below)
    byte	ViewPLID;		// Unique ID of viewed player (0 = none)

    byte	NumP;			// Number of players in race
    byte	NumConns;		// Number of connections including host
    byte	NumFinished;	// Number finished or qualified
    byte	RaceInProg;		// 0 = no race / 1 = race / 2 = qualifying

    byte	QualMins;
    byte	RaceLaps;		// see "RaceLaps" near the top of this document
    byte	Sp2;
    byte	ServerStatus;	// 0 = unknown / 1 = success / > 1 = fail

    char	Track[6];		// short name for track e.g. FE2R
    byte	Weather;		// 0,1,2...
    byte	Wind;			// 0 = off / 1 = weak / 2 = strong
};
*/

data class StatePacket(
    val requestId: UByte,
    val replaySpeed: Float = 0f,
    val flags: Flags,
    val inGameCamera: InGameCamera,
    val playerId: UByte,

    val numberOfPlayers: UByte,
    val numberOfConnections: UByte,
    val numberFinished: UByte,
    val raceInProgress: UByte,

    val qualificationMinutes: UByte,
    val raceLaps: RaceLaps,
    val serverStatus: UByte,

    val trackName: String,
    val weather: UByte,
    val wind: Wind
) : Packet {
    companion object {
        val TYPE = InSim.PacketTypes.ISP_STA.byte()

        val ISS_GAME = 1u		// in game (or MPR)
        val ISS_REPLAY = 2u		// in SPR
        val ISS_PAUSED = 4u		// paused
        val ISS_SHIFTU = 8u		// SHIFT+U mode
        val ISS_DIALOG = 16u		// in a dialog
        val ISS_SHIFTU_FOLLOW = 32u		// FOLLOW view
        val ISS_SHIFTU_NO_OPT = 64u		// SHIFT+U buttons hidden
        val ISS_SHOW_2D	= 128u		// showing 2d display
        val ISS_FRONT_END = 256u		// entry screen
        val ISS_MULTI = 512u	// multiplayer mode
        val ISS_MPSPEEDUP = 1024u	// multiplayer speedup option
        val ISS_WINDOWED = 2048u	// LFS is running in a window
        val ISS_SOUND_MUTE = 4096u	// sound is switched off
        val ISS_VIEW_OVERRIDE = 8192u	// override user view
        val ISS_VISIBLE = 16384u	// InSim buttons visible
        val ISS_TEXT_ENTRY = 32768u	// in a text entry dialog
    }

    constructor(data: ByteArray) : this(
        requestId = data[2].toUByte(),
        replaySpeed = data.getFloatAt(4),
        flags = Flags(data.getUShortAt(8).toInt()),
        inGameCamera = InGameCamera.get(data[10].toUByte()),
        playerId = data[11].toUByte(),

        numberOfPlayers = data[12].toUByte(),
        numberOfConnections = data[13].toUByte(),
        numberFinished = data[14].toUByte(),
        raceInProgress = data[15].toUByte(),

        qualificationMinutes = data[16].toUByte(),
        raceLaps = RaceLaps(data[17].toUByte().toInt()),
        serverStatus = data[19].toUByte(),

        trackName = data.getASCIIString(20, 6),
        weather = data[26].toUByte(),
        wind = get<Wind>(data[27])
    )

    data class Flags(
        val inGame: Boolean,
        val inSPR: Boolean,
        val paused: Boolean,
        val shiftU: Boolean,
        val inDialog: Boolean,
        val shiftUFollow: Boolean,
        val shiftUNoOpt: Boolean,
        val show2d: Boolean,
        val frontEnd: Boolean,
        val multiplayer: Boolean,
        val mpSpeedup: Boolean,
        val windowed: Boolean,
        val soundMuted: Boolean,
        val overrideUserView: Boolean,
        val visibleButtons: Boolean,
        val inTextEntryDialog: Boolean,
    ) {
        constructor(value: Int) : this (
            inGame = (value and 0x1) > 0,
            inSPR = (value and 0x2) > 0,
            paused = (value and 0x4) > 0,
            shiftU = (value and 0x8) > 0,
            inDialog = (value and 0x10) > 0,
            shiftUFollow = (value and 0x20) > 0,
            shiftUNoOpt = (value and 0x40) > 0,
            show2d = (value and 0x80) > 0,
            frontEnd = (value and 0x100) > 0,
            multiplayer = (value and 0x200) > 0,
            mpSpeedup = (value and 0x400) > 0,
            windowed = (value and 0x800) > 0,
            soundMuted = (value and 0x1000) > 0,
            overrideUserView = (value and 0x2000) > 0,
            visibleButtons = (value and 0x4000) > 0,
            inTextEntryDialog = (value and 0x8000) > 0,
        )

    }
}