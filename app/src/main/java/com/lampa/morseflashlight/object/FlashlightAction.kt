package com.lampa.morseflashlight.`object`

sealed class FlashlightAction {
    object Off : FlashlightAction()
    object Torch : FlashlightAction()
    object Stroboscope : FlashlightAction()
    data class Morse(private val text: String, val loop: Boolean) : FlashlightAction() {
        val textOnMorse = text.toMorseSymbolList()

        private fun String.toMorseSymbolList() = this.setUpSpaces().map { char -> char.toMorse() }.flatten()
        private fun String.setUpSpaces() = this.trim().plus(" ")
        private fun Char.toMorse() = MorseCode.convertCharToMorseCode(this)
    }
}