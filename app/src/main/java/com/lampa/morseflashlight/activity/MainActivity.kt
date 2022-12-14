package com.lampa.morseflashlight.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lampa.morseflashlight.ui.MainButtons
import com.lampa.morseflashlight.ui.MorseUi
import com.lampa.morseflashlight.ui.theme.MorseFlashlightTheme
import com.lampa.morseflashlight.ui.theme.defaultArrangementSpace
import com.lampa.morseflashlight.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorseFlashlightTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val flashlightState by mainViewModel.flashlightState.collectAsState()
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            MainButtons(
                                flashlightState = flashlightState,
                                onAction = mainViewModel::onAction
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            MorseUi(
                                onAction = mainViewModel::onAction
                            )
                        }
                    }
                }
            }
        }
    }
}