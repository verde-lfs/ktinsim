package lfs.ktinsim.utils

object UnitConverter {

    fun toMeters(i : Int) : Float {
        return i.toFloat() / 65536
    }

    fun toMeters(i: Short) : Float {
        return i.toFloat() / 16
    }

    fun toMS(speed : UShort): Float {
        return speed.toFloat() / 32768 * 100
    }

    fun toKmH(speed: UShort): Float {
        return toMS(speed) * 3.6f
    }

    fun toKmH(speed: UByte): Float {
        return speed.toFloat() * 3.6f
    }

    fun toDegrees(angle: UShort): Float {
        return angle.toFloat() / 32768 * 180
    }

    fun toDegrees(angle: Byte) : Float {
        return angle.toFloat() / 128 * 180
    }

    fun toDegreesPerSecond(angleVelocity: Short) : Float {
        return angleVelocity.toFloat() / 8192 * 180
    }
}