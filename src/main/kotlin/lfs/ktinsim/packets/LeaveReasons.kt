package lfs.ktinsim.packets

enum class LeaveReasons {
    DISCO,		// 0 - none
    TIMEOUT,		// 1 - timed out
    LOSTCONN,		// 2 - lost connection
    KICKED,		// 3 - kicked
    BANNED,		// 4 - banned
    SECURITY,		// 5 - security
    CPW,			// 6 - cheat protection wrong
    OOS,			// 7 - out of sync with host
    JOOS,			// 8 - join OOS (initial sync failed)
    HACK,			// 9 - invalid packet
    NUM;
}