package com.dezzomorf.morseflashlight.`object`

sealed class FlashlightAction {
    object Off : FlashlightAction()
    object Torch : FlashlightAction()
    object Stroboscope : FlashlightAction()
    data class Morse(private val text: String, val loop: Boolean) : FlashlightAction() {
        val textOnMorse = text.getMorseCode()
        private fun String.getMorseCode() = this.map { char ->
            MorseCode.getMorseCode(char)
        }.filterNotNull()
    }
}