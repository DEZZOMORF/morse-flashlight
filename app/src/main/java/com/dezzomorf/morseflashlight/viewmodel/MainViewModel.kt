package com.dezzomorf.morseflashlight.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dezzomorf.morseflashlight.`object`.FlashlightAction
import com.dezzomorf.morseflashlight.`object`.MorseCode
import com.dezzomorf.morseflashlight.`object`.MorseSymbol
import com.dezzomorf.morseflashlight.manager.TorchManager
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
    @ApplicationContext private val context: Application
) : AndroidViewModel(context) {

    companion object {
        private const val MORSE_DEFAULT_DELAY = 700
        var SPEED = 0.5f
        val MORSE_DOT_DELAY: Long get() = (MORSE_DEFAULT_DELAY * SPEED).toLong()
        val MORSE_DASH_DELAY: Long get() = MORSE_DOT_DELAY * 3
        val MORSE_SYMBOL_PAUSE_DELAY: Long get() = MORSE_DOT_DELAY
        val MORSE_LETTER_PAUSE_DELAY: Long get() = MORSE_DOT_DELAY * 2
        val MORSE_WORD_PAUSE_DELAY: Long get() = MORSE_DOT_DELAY * 4
        const val STROBOSCOPE_DELAY = 50L
        const val DEFAULT_TEXT = " "
    }

    private val torchManager = TorchManager(context)

    private val _flashlightState = MutableStateFlow(false)
    val flashlightState: StateFlow<Boolean> = _flashlightState.asStateFlow()

    private val _morseTextState = MutableStateFlow(DEFAULT_TEXT)
    val morseTextState: StateFlow<String> = _morseTextState.asStateFlow()

    private var morseScope: Job = viewModelScope.launch {}

    fun onAction(action: FlashlightAction) {
        when (action) {
            is FlashlightAction.Torch -> turnFlashlight()
            is FlashlightAction.Morse -> turnOnMorse(action.textOnMorse, action.loop)
            is FlashlightAction.Stroboscope -> turnOnStroboscope()
            is FlashlightAction.Off -> {
                stop()
                turnOffFlashlight()
            }
        }
    }

    fun setSpeed(s:Float) {
        SPEED = s
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
        stop()
        if (!flashlightState.value) {
            turnOnFlashlight()
        } else {
            turnOffFlashlight()
        }
    }

    private fun turnOnMorse(morseCodes: List<MorseCode>, loop: Boolean) {
        stop()
        morseScope = viewModelScope.launch {
            morseCodes.forEach { morseCode ->

                _morseTextState.value += when (morseCode) {
                    MorseCode.SPACE -> " "
                    else -> morseCode.name
                }

                MorseCode.getMorseSymbols(morseCode).forEach { morseSymbol ->
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
            }
            if (loop) {
                delay(MORSE_WORD_PAUSE_DELAY)
                turnOnMorse(morseCodes, loop)
            }
        }
    }

    private fun turnOnStroboscope() {
        stop()
        morseScope = viewModelScope.launch {
            turnOnFlashlight()
            delay(STROBOSCOPE_DELAY)
            turnOffFlashlight()
            delay(STROBOSCOPE_DELAY)
            turnOnStroboscope()
        }
    }

    private fun stop() {
        morseScope.cancel()
        _morseTextState.value = DEFAULT_TEXT
    }
}