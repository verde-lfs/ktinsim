package lfs.ktinsim.data

import lfs.ktinsim.getIntAt

data class IntVector(val x: Int, val y: Int, val z: Int) {
    constructor(data: ByteArray, index: Int) : this(
        x = data.getIntAt(index),
        y = data.getIntAt(index + 4),
        z = data.getIntAt(index + 8)
    )
}