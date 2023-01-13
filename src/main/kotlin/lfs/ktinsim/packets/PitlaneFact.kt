package lfs.ktinsim.packets

enum class PitlaneFact {
    EXIT,		// 0 - left pit lane
    ENTER,		// 1 - entered pit lane
    NO_PURPOSE,	// 2 - entered for no purpose
    DT,			// 3 - entered for drive-through
    SG,			// 4 - entered for stop-go
    NUM;
}