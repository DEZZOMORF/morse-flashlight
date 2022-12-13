package com.lampa.morseflashlight.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.lampa.morseflashlight.ui.MainScreen
import com.lampa.morseflashlight.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val flashlightState by mainViewModel.flashlightState.collectAsState()
            MainScreen(
                flashlightState = flashlightState,
                onAction = mainViewModel::onAction
            )
        }
    }
}