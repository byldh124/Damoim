package com.moondroid.project01_meetingapp.presentation.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.damoim.common.IntentParam.ACTIVITY
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.Extension.startActivityWithAnim
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.presentation.dialog.OneButtonDialog
import com.moondroid.project01_meetingapp.presentation.dialog.TwoButtonDialog
import com.moondroid.project01_meetingapp.presentation.dialog.WebViewDialog
import com.moondroid.project01_meetingapp.presentation.ui.activity.GroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.signin.SignInActivity
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import java.util.concurrent.Executor


/**
 * 의존성 주입을 위해 해당 클래스를 상속받는 액티비티는 @AndroidEntryPoint 어노테이션을 달아줘야 함
 **/
open class BaseActivity(@LayoutRes val layoutResId: Int) : AppCompatActivity() {
    protected lateinit var executor: Executor

    private var oneButtonDialog: OneButtonDialog? = null
    private var twoButtonDialog: TwoButtonDialog? = null
    private var loadingDialog: LoadingDialog? = null
    private var webViewDialog: WebViewDialog? = null

    private var onResult: (Intent) -> Unit? = {}

    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result?.resultCode == RESULT_OK) {
                    result.data?.let {
                        onResult(result.data!!)
                    }
                }
            } catch (e: Exception) {
                e.logException()
            }
        }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBack()
        }
    }

    open fun onBack() {
        finish()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    fun showNetworkError(code: Int) {
        showNetworkError(code, onClick = {})
    }

    fun activityResult(onResult: (Intent) -> Unit?, intent: Intent) {
        this@BaseActivity.onResult = onResult
        activityResult.launch(intent)
    }

    fun showNetworkError(code: Int, onClick: () -> Unit) {
        try {
            if (code != 0) {
                if (code == NETWORK_NOT_CONNECTED) {
                    showMessage(getString(R.string.error_network_not_connected), code.toString(), onClick)
                } else {
                    showMessage(getString(R.string.error_network_fail), code.toString(), onClick)
                }
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun showMessage(msg: String, onClick: () -> Unit = {}) {
        try {
            if (oneButtonDialog == null) {
                oneButtonDialog = OneButtonDialog(this, msg, onClick)
            } else {
                oneButtonDialog!!.msg = msg
                oneButtonDialog!!.onClick = onClick
            }

            oneButtonDialog!!.show()
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun showMessage(msg: String) {
        showMessage(msg) {}
    }

    fun showMessage(msg: String, code: String) {
        showMessage(String.format(msg, code))
    }

    fun showMessage(msg: String, code: String, onClick: () -> Unit) {
        showMessage(String.format(msg, code), onClick)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBack()
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
            e.logException()
        }
    }

    fun hideLoading() {
        try {
            if (loadingDialog?.isShowing == true) {
                loadingDialog?.cancel()
                loadingDialog = null
            }
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun goToHomeActivity(activityTy: Int) {
        try {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra(ACTIVITY, activityTy)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivityWithAnim(intent)
            finishAffinity()
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun goToSignInActivity(activityTy: Int) {
        try {
            val intent = Intent(this, SignInActivity::class.java).apply {
                putExtra(ACTIVITY, activityTy)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            e.logException()
        }
    }

    /**
     * GROUP_ITEM 클릭시 액티비티 전환
     */
    fun goToGroupActivity(activityType: Int) {
        try {
            val intent = Intent(this, GroupActivity::class.java).apply {
                putExtra(ACTIVITY, activityType)
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun showUseTerm() {
        try {
            if (webViewDialog == null) {
                webViewDialog = WebViewDialog(this, WebViewDialog.TYPE.USE_TERM)
            } else {
                webViewDialog!!.setType(WebViewDialog.TYPE.USE_TERM)
            }
            webViewDialog!!.show()
        } catch (e: Exception) {
            e.logException()
        }
    }

    fun showPrivacy() {
        try {
            if (webViewDialog == null) {
                webViewDialog = WebViewDialog(this, WebViewDialog.TYPE.PRIVACY)
            } else {
                webViewDialog!!.setType(WebViewDialog.TYPE.PRIVACY)
            }
            webViewDialog!!.show()
        } catch (e: Exception) {
            e.logException()
        }
    }
}