package lfs.ktinsim.packets

enum class PenaltyReason {
    UNKNOWN,		// 0 - unknown or cleared penalty
    ADMIN,			// 1 - penalty given by admin
    WRONG_WAY,		// 2 - wrong way driving
    FALSE_START,	// 3 - starting before green light
    SPEEDING,		// 4 - speeding in pit lane
    STOP_SHORT,	// 5 - stop-go pit stop too short
    STOP_LATE,		// 6 - compulsory stop is too late
    NUM;
}