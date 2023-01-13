package lfs.ktinsim

import java.nio.ByteBuffer
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.getFixedSizeByteArray(size: Int) : ByteArray {
    val arr = this.toByteArray(Charsets.US_ASCII)
    return arr.getFixedSizeByteArray(size)
}

fun ByteArray.getFixedSizeByteArray(size: Int) : ByteArray {
    val arrSize = this.size
    return if (arrSize > size) {
        this.sliceArray(0 until size)
    } else if (arrSize < size) {
        this + ByteArray(size - arrSize)
    } else {
        this
    }
}

fun ByteArray.getUIntAt(i: Int): UInt {
    return ((this[i+3].toUInt() and 0xFFu) shl 24) or
            ((this[i+2].toUInt() and 0xFFu) shl 16) or
            ((this[i+1].toUInt() and 0xFFu) shl 8) or
            (this[i].toUInt() and 0xFFu)
}

fun ByteArray.getIntAt(i: Int) = this.getUIntAt(i).toInt()

fun ByteArray.getUShortAt(i: Int): UShort {
    return (((this[i+1].toUInt() and 0xFFu) shl 8) or (this[i].toUInt() and 0xFFu)).toUShort()
}

fun ByteArray.getShortAt(i: Int): Short {
    return (((this[i+1].toUInt() and 0xFFu) shl 8) or (this[i].toUInt() and 0xFFu)).toShort()
}

fun ByteArray.getFloatAt(i: Int): Float {
    val intBits = this.getUIntAt(i).toInt()
    return Float.fromBits(intBits)
}

fun ByteArray.trim(): ByteArray {
    val start = this.indexOfFirst { it != 0.toByte() }
    if (start == -1) {
        return ByteArray(0)
    }
    var end = this.indexOfLast { it != 0.toByte() }
    if (end == -1)
        end = this.size-1
    return this.sliceArray(start .. end)
}

fun ByteArray.trimBefore(): ByteArray {
    val start = this.indexOfFirst { it != 0.toByte() }
    if (start == -1) {
        return ByteArray(0)
    }
    return this.sliceArray(start until this.size)
}

fun ByteArray.getASCIIString(i: Int, size: Int): String {
    return this.sliceArray(i until i+size).trim().toString(Charsets.US_ASCII)
}

fun UShort.toByteArray(): ByteArray {
    val arr = ByteArray(2)
    val d = this.toUInt()
    arr[1] = ((d and 0xFF00u) shr 8).toByte()
    arr[0] = (d and 0xFFu).toByte()
    return arr
}

fun UInt.toByteArray(): ByteArray {
    val arr = ByteArray(4)
    arr[3] = ((this and 0xFF000000u) shr 24).toByte()
    arr[2] = ((this and 0xFF0000u) shr 16).toByte()
    arr[1] = ((this and 0xFF00u) shr 8).toByte()
    arr[0] = (this and 0xFFu).toByte()
    return arr
}


fun Int.roundBy(num : Int) =
    if (this % num == 0) this
    else (this / num + 1) * num


fun UInt.toRaceTime() : String {
    val time = this.toLong()
    val millis = (time % 1000).toInt()
    val seconds = (time / 1000 % 60).toInt()
    val minutes = (time / 60000 % 60).toInt()
    val hours = (time / 3_600_000).toInt()
    val localTime = LocalTime.of(hours, minutes, seconds, millis*1_000_000)
    val pattern = if (hours == 0) "mm:ss.SSS" else "HH:mm:ss.SSS"
    return localTime.format(DateTimeFormatter.ofPattern(pattern))
}

fun ByteBuffer.put(b : UByte) : ByteBuffer = this.put(b.toByte())