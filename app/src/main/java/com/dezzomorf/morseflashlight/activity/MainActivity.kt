package com.dezzomorf.morseflashlight.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dezzomorf.morseflashlight.BuildConfig
import com.dezzomorf.morseflashlight.manager.InAppUpdateManager
import com.dezzomorf.morseflashlight.ui.BannerAdView
import com.dezzomorf.morseflashlight.ui.MainButtons
import com.dezzomorf.morseflashlight.ui.MorseSpeedSlider
import com.dezzomorf.morseflashlight.ui.MorseUi
import com.dezzomorf.morseflashlight.ui.theme.MorseFlashlightTheme
import com.dezzomorf.morseflashlight.ui.theme.defaultArrangementSpace
import com.dezzomorf.morseflashlight.ui.theme.defaultContentPadding
import com.google.android.gms.ads.MobileAds
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var updateManager: InAppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpCrashlytics()
        setUpUpdateManager()
        setUpAdMob()

        setContent {
            MorseFlashlightTheme(darkTheme = true) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(defaultArrangementSpace),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(defaultContentPadding)
                                .weight(1f)
                        ) {

                            MainButtons(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )

                            MorseSpeedSlider(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            MorseUi(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }

                        BannerAdView(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
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

    private fun setUpAdMob() {
        MobileAds.initialize(this)
    }
}