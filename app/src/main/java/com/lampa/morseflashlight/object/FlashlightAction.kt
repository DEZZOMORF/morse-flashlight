package com.lampa.morseflashlight.`object`

sealed class FlashlightAction {
    object Off : FlashlightAction()
    object Torch : FlashlightAction()
    object Stroboscope: FlashlightAction()
    data class Morse(private val text: String) : FlashlightAction() {
        val textOnMorse = mutableListOf<MorseSymbol>().apply {
            text.map { char ->
                addAll(char.toMorse())
            }
        }.toList()

        private fun Char.toMorse() = MorseCode.convertCharToMorseCode(this)
    }
}