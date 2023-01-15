package lfs.ktinsim.packets

enum class ConfirmationFlags(override val value: UInt): FlagEnum {
    MENTIONED(1u),
    CONFIRMED(2u),
    PENALTY_DT(4u),
    PENALTY_SG(8u),
    PENALTY_30(16u),
    PENALTY_45(32u),
    DID_NOT_PIT(64u);

    fun isDisqualified() : Boolean {
        return when (this) {
            PENALTY_DT, PENALTY_SG, DID_NOT_PIT -> true
            else -> false
        }
    }

    fun isTimePenalty() : Boolean {
        return when (this) {
            PENALTY_30, PENALTY_45 -> true
            else -> false
        }
    }

    companion object: FlagEnumCompanion<ConfirmationFlags> {
        override var values = values()
    }
}