package com.dezzomorf.morseflashlight.manager

import android.app.Activity
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class InAppUpdateManager(activity: Activity): InstallStateUpdatedListener {

    companion object {
        private const val UPDATE_REQUEST_CODE = 500
    }

    private var parentActivity: Activity = activity
    private var appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(parentActivity)
    private val appUpdateInfoTask = appUpdateManager.appUpdateInfo
    private var currentType = AppUpdateType.FLEXIBLE

    init {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE)
            }
        }
    }

    private fun startUpdate(info: AppUpdateInfo, type: Int) {
        appUpdateManager.startUpdateFlowForResult(info, type, parentActivity, UPDATE_REQUEST_CODE)
        currentType = type
    }

    fun onResume() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (currentType == AppUpdateType.FLEXIBLE) {
                if (info.installStatus() == InstallStatus.DOWNLOADED)
                    updatingToast()
                    appUpdateManager.completeUpdate()
            } else if (currentType == AppUpdateType.IMMEDIATE) {
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    startUpdate(info, AppUpdateType.IMMEDIATE)
                }
            }
        }
        appUpdateManager.registerListener(this)
    }

    fun onDestroy() {
        appUpdateManager.unregisterListener(this)
    }

    private fun updatingToast() {
        Toast.makeText(parentActivity, "Updating", Toast.LENGTH_SHORT).show()
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            updatingToast()
            appUpdateManager.completeUpdate()
        }
    }
}