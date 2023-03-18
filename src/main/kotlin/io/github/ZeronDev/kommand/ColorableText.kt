package io.github.ZeronDev.kommand

import io.github.monun.kommand.KommandArgument.Companion.dynamic
import io.github.monun.kommand.StringType

class ColorableText(val text: String) {
    fun store() = text
    fun load() = text.replace("&", "§")

    companion object {
        val colorableText = dynamic { _, input ->
            ColorableText(input)
        }
        val colorableTextGreedy = dynamic(StringType.GREEDY_PHRASE) { _, input ->
            ColorableText(input)
        }
    }
}