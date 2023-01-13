package lfs.ktinsim.data

import lfs.ktinsim.getFloatAt

data class FloatVector(val x: Float, val y: Float, val z: Float) {
    constructor(data: ByteArray, index: Int) : this(
        x = data.getFloatAt(index),
        y = data.getFloatAt(index + 4),
        z = data.getFloatAt(index + 8)
    )
}