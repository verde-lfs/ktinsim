package lfs.ktinsim.packets

import lfs.ktinsim.toByteArray

enum class Car(val value: Int) {
    XFG(1),
    XRG(2),
    XRT(4),
    RB4(8),
    FXO(0x10),
    LX4(0x20),
    LX6(0x40),
    MRT(0x80),
    UF1(0x100),
    RAC(0x200),
    FZ5(0x400),
    FOX(0x800),
    XFR(0x1000),
    UFR(0x2000),
    FO8(0x4000),
    FXR(0x8000),
    XRR(0x10000),
    FZR(0x20000),
    BF1(0x40000),
    FBM(0x80000);

    companion object {
        fun getList(data: Int): List<Car> {
            return values().filter {
                (data and it.value) > 0
            }
        }
    }
}

fun List<Car>.toInt() : Int = this.fold(0) { acc, car -> acc + car.value }

fun List<Car>.toUInt() : UInt = this.toInt().toUInt()
fun List<Car>.toByteArray() : ByteArray = this.toUInt().toByteArray()