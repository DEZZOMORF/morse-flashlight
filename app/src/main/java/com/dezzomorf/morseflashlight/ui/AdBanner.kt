package com.dezzomorf.morseflashlight.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.dezzomorf.morseflashlight.BuildConfig
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdView() {
    val adaptiveAdSize = adaptiveAdSize()
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(adaptiveAdSize)
                adUnitId = BuildConfig.BANNER_ID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
private fun adaptiveAdSize(): AdSize {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    return AdSize.getPortraitAnchoredAdaptiveBannerAdSize(context, configuration.screenWidthDp)
}