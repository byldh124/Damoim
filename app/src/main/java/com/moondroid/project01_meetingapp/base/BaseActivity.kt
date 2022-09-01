package com.moondroid.project01_meetingapp.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.activity.SignInActivity
import com.moondroid.project01_meetingapp.ui.view.dialog.OneButtonDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.utils.IntentParam.ACTIVITY
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: T

    var oneButtonDialog: OneButtonDialog? = null
    var loadingDialog: LoadingDialog? = null


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        init()
    }

    abstract fun init()

    fun showNetworkError(code: Int) {
        showNetworkError(code, onClick = {})
    }

    fun showNetworkError(code: Int, onClick: () -> Unit) {
        try {
            if (code != 0) {
                if (code == NETWORK_NOT_CONNECTED) {
                    showError(
                        String.format(getString(R.string.error_network_not_connected), code),
                        onClick
                    )
                } else {
                    showError(String.format(getString(R.string.error_network_fail), code), onClick)
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showError(msg: String, onClick: () -> Unit) {
        try {
            log("showError , msg = $msg")

            if (oneButtonDialog == null) {
                oneButtonDialog = OneButtonDialog(this, msg, onClick)
            } else {
                oneButtonDialog!!.msg = msg
                oneButtonDialog!!.onClick = onClick
            }

            oneButtonDialog!!.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showError(msg: String) {
        try {
            log("showError , msg = $msg")

            if (oneButtonDialog == null) {
                oneButtonDialog = OneButtonDialog(this, msg) {}
            } else {
                oneButtonDialog!!.msg = msg
                oneButtonDialog!!.onClick = {}
            }

            oneButtonDialog!!.show()
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

            intent.putExtra(ACTIVITY, activityTy)
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

            intent.putExtra(ACTIVITY, activityTy)
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
                ACTIVITY,
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