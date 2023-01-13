package lfs.ktinsim

import lfs.ktinsim.packets.*
import kotlin.reflect.KClass

object InSim {
    const val VERSION : UByte = 9u

    enum class PacketTypes: ByteEnum // the second byte of any packet is one of these
    {
        ISP_NONE,		//  0					: not used
        ISP_ISI,		//  1 - instruction		: insim initialise
        ISP_VER,		//  2 - info			: version info
        ISP_TINY,		//  3 - both ways		: multi purpose
        ISP_SMALL,		//  4 - both ways		: multi purpose
        ISP_STA,		//  5 - info			: state info
        ISP_SCH,		//  6 - instruction		: single character
        ISP_SFP,		//  7 - instruction		: state flags pack
        ISP_SCC,		//  8 - instruction		: set car camera
        ISP_CPP,		//  9 - both ways		: cam pos pack
        ISP_ISM,		// 10 - info			: start multiplayer
        ISP_MSO,		// 11 - info			: message out
        ISP_III,		// 12 - info			: hidden /i message
        ISP_MST,		// 13 - instruction		: type message or /command
        ISP_MTC,		// 14 - instruction		: message to a connection
        ISP_MOD,		// 15 - instruction		: set screen mode
        ISP_VTN,		// 16 - info			: vote notification
        ISP_RST,		// 17 - info			: race start
        ISP_NCN,		// 18 - info			: new connection
        ISP_CNL,		// 19 - info			: connection left
        ISP_CPR,		// 20 - info			: connection renamed
        ISP_NPL,		// 21 - info			: new player (joined race)
        ISP_PLP,		// 22 - info			: player pit (keeps slot in race)
        ISP_PLL,		// 23 - info			: player leave (spectate - loses slot)
        ISP_LAP,		// 24 - info			: lap time
        ISP_SPX,		// 25 - info			: split x time
        ISP_PIT,		// 26 - info			: pit stop start
        ISP_PSF,		// 27 - info			: pit stop finish
        ISP_PLA,		// 28 - info			: pit lane enter / leave
        ISP_CCH,		// 29 - info			: camera changed
        ISP_PEN,		// 30 - info			: penalty given or cleared
        ISP_TOC,		// 31 - info			: take over car
        ISP_FLG,		// 32 - info			: flag (yellow or blue)
        ISP_PFL,		// 33 - info			: player flags (help flags)
        ISP_FIN,		// 34 - info			: finished race
        ISP_RES,		// 35 - info			: result confirmed
        ISP_REO,		// 36 - both ways		: reorder (info or instruction)
        ISP_NLP,		// 37 - info			: node and lap packet
        ISP_MCI,		// 38 - info			: multi car info
        ISP_MSX,		// 39 - instruction		: type message
        ISP_MSL,		// 40 - instruction		: message to local computer
        ISP_CRS,		// 41 - info			: car reset
        ISP_BFN,		// 42 - both ways		: delete buttons / receive button requests
        ISP_AXI,		// 43 - info			: autocross layout information
        ISP_AXO,		// 44 - info			: hit an autocross object
        ISP_BTN,		// 45 - instruction		: show a button on local or remote screen
        ISP_BTC,		// 46 - info			: sent when a user clicks a button
        ISP_BTT,		// 47 - info			: sent after typing into a button
        ISP_RIP,		// 48 - both ways		: replay information packet
        ISP_SSH,		// 49 - both ways		: screenshot
        ISP_CON,		// 50 - info			: contact between cars (collision report)
        ISP_OBH,		// 51 - info			: contact car + object (collision report)
        ISP_HLV,		// 52 - info			: report incidents that would violate HLVC
        ISP_PLC,		// 53 - instruction		: player cars
        ISP_AXM,		// 54 - both ways		: autocross multiple objects
        ISP_ACR,		// 55 - info			: admin command report
        ISP_HCP,		// 56 - instruction		: car handicaps
        ISP_NCI,		// 57 - info			: new connection - extra info for host
        ISP_JRR,		// 58 - instruction		: reply to a join request (allow / disallow)
        ISP_UCO,		// 59 - info			: report InSim checkpoint / InSim circle
        ISP_OCO,		// 60 - instruction		: object control (currently used for lights)
        ISP_TTC,		// 61 - instruction		: multi purpose - target to connection
        ISP_SLC,		// 62 - info			: connection selected a car
        ISP_CSC,		// 63 - info			: car state changed
        ISP_CIM,		// 64 - info			: connection's interface mode
        ISP_MAL;		// 65 - both ways		: set mods allowed

        companion object {
            fun get(ord: Byte) = values()[ord.toInt()]
            fun get(ord: Int) = values()[ord]
        }

        fun getPacketClass() : KClass<out Packet> {
            return when (this) {
                ISP_NONE -> NoPacket::class
                ISP_ISI -> InitPacket::class
                ISP_VER -> VersionPacket::class
                ISP_TINY -> TinyPacket::class
                ISP_SMALL -> SmallPacket::class
                ISP_STA -> StatePacket::class
                ISP_SCH -> SingleCharacterPacket::class
                ISP_SFP -> StateFlagPacket::class
                ISP_SCC -> SetCarCameraPacket::class
                ISP_CPP -> CameraPositionPacket::class
                ISP_ISM -> MultiplayerPacket::class
                ISP_MSO -> MessageOutPacket::class
                ISP_III -> InfoPacket::class
                ISP_MST -> MessageInPacket::class
                ISP_MTC -> MessageToConnectionPacket::class
                ISP_MOD -> ScreenModePacket::class
                ISP_VTN -> VoteNotifyPacket::class
                ISP_RST -> RaceStartPacket::class
                ISP_NCN -> NewConnectionPacket::class
                ISP_CNL -> ConnectionLeavePacket::class
                ISP_CPR -> ConnectionPlayerRenamePacket::class
                ISP_NPL -> PlayerJoinPacket::class
                ISP_PLP -> PlayerPitsPacket::class
                ISP_PLL -> PlayerLeavePacket::class
                ISP_LAP -> LapTimePacket::class
                ISP_SPX -> SplitTimePacket::class
                ISP_PIT -> PitStopPacket::class
                ISP_PSF -> PitStopFinishedPacket::class
                ISP_PLA -> PitLanePacket::class
                ISP_CCH -> CameraChangePacket::class
                ISP_PEN -> PenaltyPacket::class
                ISP_TOC -> TakeOverCarPacket::class
                ISP_FLG -> FlagChangePacket::class
                ISP_PFL -> PlayerFlagsChangePacket::class
                ISP_FIN -> FinishedRacePacket::class
                ISP_RES -> ResultPacket::class
                ISP_REO -> ReorderPacket::class
                ISP_NLP -> NodeLapPacket::class
                ISP_MCI -> MultiCarInfoPacket::class
                ISP_MSX -> ExtendedMessageInPacket::class
                ISP_MSL -> LocalMessagePacket::class
                ISP_CRS -> CarResetPacket::class
                ISP_BFN -> ButtonFunctionPacket::class
                ISP_AXI -> AutoXInfoPacket::class
                ISP_AXO -> AutoXObjectPacket::class
                ISP_BTN -> ButtonPacket::class
                ISP_BTC -> ButtonClickPacket::class
                ISP_BTT -> ButtonTypePacket::class
                ISP_RIP -> ReplayInfoPacket::class
                ISP_SSH -> ScreenshotPacket::class
                ISP_CON -> CarContactPacket::class
                ISP_OBH -> ObjectHitPacket::class
                ISP_HLV -> HotlapValidityPacket::class
                ISP_PLC -> PlayerCarsPacket::class
                ISP_AXM -> AutoXMultipleObjsPacket::class
                ISP_ACR -> AdminCommandReportPacket::class
                ISP_HCP -> HandicapsPacket::class
                ISP_NCI -> NewConnectionInfoPacket::class
                ISP_JRR -> JoinRequestReplyPacket::class
                ISP_UCO -> UserControlObjectPacket::class
                ISP_OCO -> ObjectControlPacket::class
                ISP_TTC -> TargetToConnectionPacket::class
                ISP_SLC -> SelectedCarPacket::class
                ISP_CSC -> CarStateChangedPacket::class
                ISP_CIM -> ConnectionInterfaceModePacket::class
                ISP_MAL -> ModsAllowedPacket::class
            }
        }
    }
}