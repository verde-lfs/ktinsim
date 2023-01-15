package lfs.ktinsim.packets

import lfs.ktinsim.toByteArray

enum class Car(override val value: UInt): FlagEnum {
    XFG(1u),
    XRG(2u),
    XRT(4u),
    RB4(8u),
    FXO(0x10u),
    LX4(0x20u),
    LX6(0x40u),
    MRT(0x80u),
    UF1(0x100u),
    RAC(0x200u),
    FZ5(0x400u),
    FOX(0x800u),
    XFR(0x1000u),
    UFR(0x2000u),
    FO8(0x4000u),
    FXR(0x8000u),
    XRR(0x10000u),
    FZR(0x20000u),
    BF1(0x40000u),
    FBM(0x80000u);

    companion object : FlagEnumCompanion<Car> {
        override var values = Car.values()
    }
}

fun List<Car>.toUInt() : UInt = this.fold(0u) { acc, car -> acc + car.value }

fun List<Car>.toInt() : Int = this.toUInt().toInt()
fun List<Car>.toByteArray() : ByteArray = this.toUInt().toByteArray()