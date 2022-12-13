package com.lampa.morseflashlight.manager

import android.content.Context
import android.hardware.camera2.CameraManager

class TorchManager(context: Context) {

    private val cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraID: String
        get() = try {
            cameraManager.cameraIdList[0]
        } catch (e: Exception) {
            e.printStackTrace()
            NO_CAMERA_ID
        }

    fun turnOnFlashlight() {
        try {
            cameraManager.setTorchMode(cameraID, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun turnOffFlashlight() {
        try {
            cameraManager.setTorchMode(cameraID, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val NO_CAMERA_ID = ""
    }
}