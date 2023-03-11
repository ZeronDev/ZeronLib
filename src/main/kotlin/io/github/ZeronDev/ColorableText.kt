package io.github.ZeronDev

import io.github.monun.kommand.KommandArgument.Companion.dynamic

class ColorableText(val text: String) {
    fun store() = text
    fun load() = text.replace("&", "§")

    companion object {
        val colorableText = dynamic { _, input ->
            ColorableText(input)
        }
    }
}