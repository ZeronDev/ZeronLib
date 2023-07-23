package io.github.ZeronDev.util

import kotlin.math.max

// 각별님의 tap
enum class MavenVersionIdentifier(val priority: Int) {
    NONE(10),
    RELEASE(9),
    GA(8),
    FINAL(7),
    SNAPSHOT(6),
    RC(5),
    ZETA(4),
    BETA(3),
    ALPHA(2),
    DEV(1)
}
// 각별님의 tap
private val String.identifier: MavenVersionIdentifier
    get() {
        return if (equals("0")) {
            MavenVersionIdentifier.NONE
        } else {
            try {
                MavenVersionIdentifier.valueOf(uppercase())
            } catch (e: IllegalArgumentException) {
                throw RuntimeException("No such version identifier found: $this")
            }
        }
    }
// 각별님의 tap
private val String.isValidLong: Boolean
    get() {
        return try {
            toLong(0x10)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
// 각별님의 tap
infix fun String.compareVersion(other: String): Int {
    if (!matches("""^\d+(\.\d+)*(-[a-zA-Z]*)?$""".toRegex()) || !other.matches("""^\d+(\.\d+)*(-[a-zA-Z]*)?$""".toRegex())) {
        throw RuntimeException("The version format does not valid.")
    }
    val split = replace("-", ".").split('.')
    val otherSplit = other.replace("-", ".").split('.')

    loop@ for (i in 0 until max(split.count(), otherSplit.count())) {
        val a = split.getOrNull(i) ?: "0"
        val b = otherSplit.getOrNull(i) ?: "0"
        var compare = 0
        val isLastInIndex = i == max(split.count(), otherSplit.count()) - 1

        kotlin.runCatching {
            compare = if (isLastInIndex && (!a.isValidLong || !b.isValidLong)) {
                a.identifier.priority.compareTo(b.identifier.priority)
            } else {
                a.toLong(0x10).compareTo(b.toLong(0x10))
            }
        }.onFailure {
            compare = AlphanumComparator.compare(a, b)
        }
        if (compare != 0) return compare
    }

    return 0
}
// 각별님의 tap
object AlphanumComparator : Comparator<String?> {
    private fun isDigit(ch: Char): Boolean {
        return ch.code in 48..57
    }

    /** Length of string is passed in for improved efficiency (only need to calculate it once)  */
    private fun getChunk(s: String, slength: Int, marker: Int): String {
        var m = marker
        val chunk = StringBuilder()
        var c = s[m]
        chunk.append(c)
        m++
        if (isDigit(c)) {
            while (m < slength) {
                c = s[m]
                if (!isDigit(c)) break
                chunk.append(c)
                m++
            }
        } else {
            while (m < slength) {
                c = s[m]
                if (isDigit(c)) break
                chunk.append(c)
                m++
            }
        }
        return chunk.toString()
    }

    override fun compare(s1: String?, s2: String?): Int {
        if (s1 == null || s2 == null) {
            return 0
        }
        var thisMarker = 0
        var thatMarker = 0
        val s1Length = s1.length
        val s2Length = s2.length
        while (thisMarker < s1Length && thatMarker < s2Length) {
            val thisChunk = getChunk(s1, s1Length, thisMarker)
            thisMarker += thisChunk.length
            val thatChunk = getChunk(s2, s2Length, thatMarker)
            thatMarker += thatChunk.length

            // If both chunks contain numeric characters, sort them numerically
            var result: Int
            if (isDigit(thisChunk[0]) && isDigit(thatChunk[0])) {
                // Simple chunk comparison by length.
                val thisChunkLength = thisChunk.length
                result = thisChunkLength - thatChunk.length
                // If equal, the first different number counts
                if (result == 0) {
                    for (i in 0 until thisChunkLength) {
                        result = thisChunk[i] - thatChunk[i]
                        if (result != 0) {
                            return result
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk)
            }
            if (result != 0) return result
        }
        return s1Length - s2Length
    }
}