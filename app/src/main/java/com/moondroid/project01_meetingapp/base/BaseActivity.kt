package com.moondroid.project01_meetingapp.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.activity.SignInActivity
import com.moondroid.project01_meetingapp.ui.view.dialog.ErrorDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: T

    var errorDialog: ErrorDialog? = null
    var loadingDialog: LoadingDialog? = null


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
    }

    abstract fun init()

    fun showError(msg: String, onClick: () -> Unit) {
        try {
            log("showError , msg = $msg")

            if (errorDialog == null) {
                errorDialog = ErrorDialog(this, msg, onClick)
            } else {
                errorDialog!!.msg = msg
                errorDialog!!.onClick = onClick
            }

            errorDialog!!.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showError(msg: String) {
        try {
            log("showError , msg = $msg")

            if (errorDialog == null) {
                errorDialog = ErrorDialog(this, msg) {}
            } else {
                errorDialog!!.msg = msg
                errorDialog!!.onClick = {}
            }

            errorDialog!!.show()
        } catch (e: Exception) {
            logException(e)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoading(b: Boolean) {
        if (b) showLoading() else hideLoading()
    }

    fun showLoading() {
        try {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(this)
            }
            loadingDialog!!.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun hideLoading() {
        try {
            if (loadingDialog?.isShowing == true) {
                loadingDialog?.cancel()
                loadingDialog = null
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun goToHomeActivity(activityTy: Int) {
        try {
            val intent = Intent(this, HomeActivity::class.java)

            intent.putExtra(Constants.ACTIVITY_TY, activityTy)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityWithAnim(intent)
            finishAffinity()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun goToSgnnActivity(activityTy: Int) {
        try {
            val intent = Intent(this, SignInActivity::class.java)

            intent.putExtra(Constants.ACTIVITY_TY, activityTy)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * GROUP_ITEM 클릭시 액티비티 전환
     */
    fun goToGroupActivity(activityType: Int) {
        try {
            val newIntent = Intent(this, GroupActivity::class.java)
            newIntent.putExtra(
                Constants.ACTIVITY_TY,
                activityType
            )
            startActivityWithAnim(newIntent)

        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }
}