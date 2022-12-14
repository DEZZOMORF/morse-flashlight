package com.lampa.morseflashlight.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lampa.morseflashlight.`object`.FlashlightAction
import com.lampa.morseflashlight.`object`.MorseSymbol
import com.lampa.morseflashlight.manager.TorchManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Application
) : AndroidViewModel(context) {

    companion object {
        const val MORSE_DOT_DELAY = 200L
        const val MORSE_DASH_DELAY = MORSE_DOT_DELAY * 3
        const val MORSE_SYMBOL_PAUSE_DELAY = MORSE_DOT_DELAY
        const val MORSE_LETTER_PAUSE_DELAY = MORSE_DOT_DELAY * 2
        const val MORSE_WORD_PAUSE_DELAY = MORSE_DOT_DELAY * 4
        const val STROBOSCOPE_DELAY = 50L
    }

    private val torchManager = TorchManager(context)

    private val _flashlightState = MutableStateFlow(false)
    val flashlightState: StateFlow<Boolean> = _flashlightState.asStateFlow()

    private var morseScope: Job = viewModelScope.launch {}

    fun onAction(action: FlashlightAction) {
        when (action) {
            is FlashlightAction.Torch -> turnFlashlight()
            is FlashlightAction.Morse -> turnOnMorse(action.textOnMorse, action.loop)
            is FlashlightAction.Stroboscope -> turnOnStroboscope()
            is FlashlightAction.Off -> {
                morseScope.cancel()
                turnOffFlashlight()
            }
        }
    }

    private fun turnOffFlashlight() {
        torchManager.turnOffFlashlight()
        _flashlightState.value = false
    }

    private fun turnOnFlashlight() {
        torchManager.turnOnFlashlight()
        _flashlightState.value = true
    }

    private fun turnFlashlight() {
        morseScope.cancel()
        if (!flashlightState.value) {
            turnOnFlashlight()
        } else {
            turnOffFlashlight()
        }
    }

    private fun turnOnMorse(morse: List<MorseSymbol>, loop: Boolean) {
        morseScope.cancel()
        morseScope = viewModelScope.launch {
            morse.forEach { morseSymbol ->
                when (morseSymbol) {
                    MorseSymbol.DOT -> {
                        turnOnFlashlight()
                        delay(MORSE_DOT_DELAY)
                        turnOffFlashlight()
                        delay(MORSE_SYMBOL_PAUSE_DELAY)
                    }
                    MorseSymbol.DASH -> {
                        turnOnFlashlight()
                        delay(MORSE_DASH_DELAY)
                        turnOffFlashlight()
                        delay(MORSE_SYMBOL_PAUSE_DELAY)
                    }
                    MorseSymbol.LETTER_END -> {
                        delay(MORSE_LETTER_PAUSE_DELAY)
                    }
                    MorseSymbol.WORD_END -> {
                        delay(MORSE_WORD_PAUSE_DELAY)
                    }
                }
            }
            if (loop) turnOnMorse(morse, loop)
        }
    }

    private fun turnOnStroboscope() {
        morseScope.cancel()
        morseScope = viewModelScope.launch {
            turnOnFlashlight()
            delay(STROBOSCOPE_DELAY)
            turnOffFlashlight()
            delay(STROBOSCOPE_DELAY)
            turnOnStroboscope()
        }
    }
}