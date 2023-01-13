package lfs.ktinsim.packets

data class RaceLaps(
    val isPractice: Boolean = false,
    val laps: Int = 0,
    val hours: Int = 0
) {
    constructor(value: Int) : this(
        isPractice = value == 0,
        laps =
            if (value < 100) {
                value
            } else if (value <= 190) {
                (value - 100) * 10 + 100
            } else {
                0
            },
        hours =
            if (value in 191..238) {
                value - 190
            } else {
                0
            }
    )
}