package com.dezzomorf.morseflashlight.activity

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
import com.dezzomorf.morseflashlight.BuildConfig
import com.dezzomorf.morseflashlight.manager.InAppUpdateManager
import com.dezzomorf.morseflashlight.ui.MainButtons
import com.dezzomorf.morseflashlight.ui.MorseUi
import com.dezzomorf.morseflashlight.ui.theme.DarkGray
import com.dezzomorf.morseflashlight.ui.theme.MorseFlashlightTheme
import com.dezzomorf.morseflashlight.ui.theme.defaultArrangementSpace
import com.dezzomorf.morseflashlight.viewmodel.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var updateManager: InAppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpCrashlytics()
        setUpUpdateManager()

        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(
                color = DarkGray
            )
            MorseFlashlightTheme(darkTheme = true) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    val flashlightState by mainViewModel.flashlightState.collectAsState()
                    val morseTextState by mainViewModel.morseTextState.collectAsState()
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
                                morseText = morseTextState,
                                onAction = mainViewModel::onAction
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        updateManager.onDestroy()
    }

    private fun setUpCrashlytics() {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    private fun setUpUpdateManager() {
        updateManager = InAppUpdateManager(this)
    }
}