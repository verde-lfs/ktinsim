package lfs.ktinsim.extensions.commands

import lfs.ktinsim.KtInSim
import lfs.ktinsim.packets.Wind
import lfs.ktinsim.packets.MessageInPacket

fun KtInSim.ban(username: String, days: Int = 0) { // (days = 0) == 12 hours
    this.sendMessage("/ban $username $days")
}

fun KtInSim.unban(username: String) = this.sendMessage("/unban $username")

fun KtInSim.startRace() = this.sendMessage("/restart")

fun KtInSim.startQualification() = this.sendMessage("/qualify")

fun KtInSim.endRace() = this.sendMessage("/end")

fun KtInSim.setTrack(trackName : String) = this.sendMessage("/track $trackName")

fun KtInSim.setWeather(weather: Int) = this.sendMessage("/weather $weather")

fun KtInSim.setQualificationMinutes(minutes: Int) = this.sendMessage("/qual $minutes")

fun KtInSim.setLaps(laps: Int) = this.sendMessage("/laps $laps")

fun KtInSim.setHours(hours: Int) = this.sendMessage("/hours $hours")

fun KtInSim.setWind(wind: Wind) = this.sendMessage("/wind ${wind.byte().toInt()}")

fun KtInSim.setMaxGuests(n: Int) = this.sendMessage("/maxguests $n")

fun KtInSim.setAdminSlots(n: Int) = this.sendMessage("/adminslots $n")

fun KtInSim.setMaxCars(n: Int) = this.sendMessage("/carsmax $n")

fun KtInSim.setMaxHostCars(n: Int) = this.sendMessage("/carshost $n")

fun KtInSim.setMaxCarsPerGuest(n: Int) = this.sendMessage("/carsguest $n")

fun KtInSim.setCarUpdatesPerSecond(n: Int) {
    if (n in 3..6)
        this.sendMessage("/pps $n")
    else
        println("Car updates can be only in range of 3..6")
}

fun KtInSim.allowGuestVoting(b: Boolean) {
    this.sendMessage("/vote ${if (b) "yes" else "no"}")
}

fun KtInSim.allowGuestSelectingTrack(b: Boolean) {
    this.sendMessage("/select ${if (b) "yes" else "no"}")
}

fun KtInSim.blockRestartFor(seconds: Int, afterRace: Boolean = false) {
    val command = if (afterRace) "rstend" else "rstmin"
    this.sendMessage("/$command $seconds")
}

enum class WrongWayPunishment {
    NO,
    KICK,
    BAN,
    SPEC
}

fun KtInSim.setWrongWayPunishment(punishment: WrongWayPunishment) {
    this.sendMessage("/autokick ${punishment.name.toLowerCase()}")
}

fun KtInSim.allowMidRaceJoin(b: Boolean) {
    this.sendMessage("/midrace ${if (b) "yes" else "no"}")
}

fun KtInSim.requirePits(b: Boolean) {
    this.sendMessage("/mustpit ${if (b) "yes" else "no"}")
}

fun KtInSim.allowCarReset(b: Boolean) {
    this.sendMessage("/canreset ${if (b) "yes" else "no"}")
}

fun KtInSim.forceCockpitView(b: Boolean) {
    this.sendMessage("/fcv ${if (b) "yes" else "no"}")
}

fun KtInSim.allowWrongWay(b: Boolean) {
    this.sendMessage("/cruise ${if (b) "yes" else "no"}")
}

enum class RaceStartOrder {
    FIXED, FINISH, REVERSE, RANDOM
}

fun KtInSim.setDefaultRaceStartOrder(order: RaceStartOrder) {
    this.sendMessage("/start ${order.name.toLowerCase()}")
}

fun KtInSim.setPassword(password: String) = this.sendMessage("/pass ${password}")

enum class AutoSaveMode { NO, MANUAL, AUTO }

fun KtInSim.setMPRAutoSave(mode: AutoSaveMode) {
    this.sendMessage("/autosave ${mode.name.toLowerCase()}")
}

fun KtInSim.saveMPR(replayName: String) = this.sendMessage("/save_mpr $replayName")

fun KtInSim.spectate(username: String) = this.sendMessage("/spec $username")

fun KtInSim.kick(username: String) = this.sendMessage("/kick $username")

fun KtInSim.pit(username: String) = this.sendMessage("/pitlane $username")

fun KtInSim.pitAll() = this.sendMessage("/pit_all")

fun KtInSim.setRaceControlMessage(message: String) {
    this.sendMessage("/rcm $message")
}

fun KtInSim.sendRaceControlMessageToPlayer(username: String) {
    this.sendMessage("/rcm_ply $username")
}

fun KtInSim.sendRaceControlMessageToAll() {
    this.sendMessage("/rcm_all")
}

fun KtInSim.clearRaceControlMessages(username: String) {
    this.sendMessage("/rcc_ply $username")
}

fun KtInSim.clearAllRaceControlMessages() {
    this.sendMessage("/rcc_all")
}