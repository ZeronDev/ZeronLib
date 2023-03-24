package io.github.ZeronDev.coroutine

class Time {
    fun ofTicks(time: Int, timeUnit: Unit) : Long = time.toLong() * timeUnit.tick
    fun ofMilliSeconds(time: Int, timeUnit: Unit) : Long = time.toLong() * timeUnit.ms

    enum class Unit(val tick: Long, val ms: Long) {
        Second(20, 1_000),
        Minute(1_200, 60_000),
        Hour(72_000, 3_600_000),
    }
}