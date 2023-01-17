# KtInSim

KtInSim is an InSim library for Live For Speed, written in Kotlin

### Gradle

In order to use the library in Gradle project, do the following

1. Open build.gradle.kts of your project
2. Add jitpack.io to list of repositories
```
repositories {
    ...
    maven { setUrl("https://jitpack.io") }
}
```
3. Add dependency
```
dependencies {
    implementation("com.github.verde-lfs:ktinsim:0.2.0")
}
```

After that, import dependencies in your source files like
```kotlin
import lfs.ktinsim.KtInSim
import lfs.ktinsim.outsim.*
```

### How to write InSim applications with it

> Check example of simple program at https://github.com/verde-lfs/ktinsim-examples

> This library is in WIP state, the documentation may change over time

First of all, you need to create an object of KtInSim class.
```kotlin
open class KtInSim(
    val host: String = "127.0.0.1",
    val port: Int = 29999,
    val udpPort: Int = 0,
    processorClass: KClass<out Processor> = Processor::class
) {
```
As you can see, it connects to `localhost:29999` by default, using TCP. Note that KtInSim doesn't support work with UDP only.

The last argument - `processorClass` is a reference to any Kotlin class which extends Processor. Processor is a class for processing incoming packets. 

KtInSim provides two simple implementations of it: [Processor](https://github.com/verde-lfs/ktinsim/blob/master/src/main/kotlin/lfs/ktinsim/Processor.kt) itself and [DebugProcessor](https://github.com/verde-lfs/ktinsim/blob/master/src/main/kotlin/lfs/ktinsim/DebugProcessor.kt). It is encouraged that you create your own custom class which extends one of these. KtInSim calls `Processor.process(packet)` after packet receival. If you want to handle a specific packet, create such function in your Processor class or override the existing one like:
```kotlin
fun process(packet: VoteNotifyPacket) {
    // your logic here
}
```
To handle other packets use
```kotlin
override fun process(packet: Packet) {
    // ...
}
```

So, to create a KtInSim object, do something like this
```kotlin
val ktInSim = KtInSim(
    udpPort = 29999,
    processorClass = CustomProcessor::class
)
```

To start KtInSim, do
```kotlin
val deferredResult = async {
    ktInSim.run(InitMessage("serverPassword"))
}

/* other logic goes here */

deferredResult.await() // end of program
```

To make requests, use one of these options:
1. Use predefined functions of KtInSim. Example:
```kotlin
ktInSim.setAllowedCars(arrayListOf(Car.XFG, Car.XRG))
```
2. Inherit KtInSim in custom class and define your own requests/modify existing ones.
3. Write KtInSim extensions

To submit outgoing packets to LFS, use `ktInSim.addPacketToQueue(packet: Packet)`. Here is an example of custom request extension
```kotlin
fun KtInSim.makeScreenshot(filename: String) {
    this.addPacketToQueue(
        ScreenshotPacket(
            screenshotName = filename
        )
    )
}
```