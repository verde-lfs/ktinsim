package lfs.ktinsim.packets

object ConfirmationFlags {
    const val MENTIONED = 1
    const val CONFIRMED = 2
    const val PENALTY_DT = 4
    const val PENALTY_SG = 8
    const val PENALTY_30 = 16
    const val PENALTY_45 = 32
    const val DID_NOT_PIT = 64

    const val DISQ = PENALTY_DT or PENALTY_SG or DID_NOT_PIT
    const val TIME = PENALTY_30 or PENALTY_45
}