package com.moondroid.project01_meetingapp.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.activity.SignInActivity
import com.moondroid.project01_meetingapp.ui.view.dialog.ErrorDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim

open class BaseActivity : AppCompatActivity() {

    var errorDialog: ErrorDialog? = null
    var loadingDialog: LoadingDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun showError(msg: String, onClick: ()-> Unit){

        log("showError , msg = $msg")

        if (errorDialog == null){
            errorDialog = ErrorDialog(this, msg, onClick)
        } else {
            errorDialog!!.msg = msg
            errorDialog!!.onClick = onClick
        }

        errorDialog!!.show()
    }

    fun showError(msg: String){

        log("showError , msg = $msg")

        if (errorDialog == null){
            errorDialog = ErrorDialog(this, msg) {}
        } else {
            errorDialog!!.msg = msg
            errorDialog!!.onClick = {}
        }

        errorDialog!!.show()
    }

    fun showLoading(){
        if (loadingDialog == null){
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog!!.show()
    }

    fun hideLoading(){
        if (loadingDialog?.isShowing == true){
            loadingDialog?.cancel()
            loadingDialog = null
        }
    }

    fun goToHomeActivity(activityTy: Int) {
        val intent = Intent(this, HomeActivity::class.java)

        intent.putExtra(Constants.ACTIVITY_TY, activityTy)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivityWithAnim(intent)
        finishAffinity()
    }

    fun goToSgnnActivity(activityTy: Int) {
        val intent = Intent(this, SignInActivity::class.java)

        intent.putExtra(Constants.ACTIVITY_TY, activityTy)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        startActivityWithAnim(intent)
    }
}