package com.lampa.morseflashlight.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lampa.morseflashlight.manager.TorchManager
import com.lampa.morseflashlight.`object`.FlashlightAction
import com.lampa.morseflashlight.`object`.MorseSymbol
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
        const val MORSE_DOT_DELAY = 100L
        const val MORSE_DASH_DELAY = 500L
        const val MORSE_PAUSE_DELAY = 1000L
        const val STROBOSCOPE_DELAY = 50L
    }

    private val torchManager = TorchManager(context)

    private val _flashlightState = MutableStateFlow(false)
    val flashlightState: StateFlow<Boolean> = _flashlightState.asStateFlow()

    private var morseScope: Job = viewModelScope.launch {}

    fun onAction(action: FlashlightAction) {
        when (action) {
            is FlashlightAction.Torch -> turnFlashlight()
            is FlashlightAction.Morse -> turnOnMorse(action.textOnMorse)
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

    private fun turnOnMorse(morse: List<MorseSymbol>) {
        morseScope.cancel()
        morseScope = viewModelScope.launch {
            morse.forEach { morseSymbol ->
                when (morseSymbol) {
                    MorseSymbol.DOT -> {
                        turnOnFlashlight()
                        delay(MORSE_DOT_DELAY)
                        turnOffFlashlight()
                    }
                    MorseSymbol.DASH -> {
                        turnOnFlashlight()
                        delay(MORSE_DASH_DELAY)
                        turnOffFlashlight()
                    }
                }
                delay(MORSE_PAUSE_DELAY)
            }
            turnOnMorse(morse)
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