package com.moondroid.project01_meetingapp.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.room.UserDao
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.activity.SignInActivity
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.OneButtonDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.WebViewDialog
import com.moondroid.project01_meetingapp.utils.IntentParam.ACTIVITY
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: T

    @Inject
    protected lateinit var userDao: UserDao

    var user: User? = null

    private var oneButtonDialog: OneButtonDialog? = null
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
                logException(e)
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
        binding = DataBindingUtil.setContentView(this, layoutResId)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        resetUserInfo()
        this.onBackPressedDispatcher.addCallback(this, callback)
        init()
    }

    override fun onStart() {
        super.onStart()
        resetUserInfo()
    }

    private fun resetUserInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            if (userDao.getUser().isNotEmpty()) {
                user = userDao.getUser()[0]
            }
        }
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    abstract fun init()

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
                    showMessage(
                        String.format(getString(R.string.error_network_not_connected), code),
                        onClick
                    )
                } else {
                    showMessage(
                        String.format(getString(R.string.error_network_fail), code),
                        onClick
                    )
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showMessage(msg: String, onClick: () -> Unit) {
        try {
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

    fun showMessage(msg: String) {
        try {
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
                .apply {
                    putExtra(ACTIVITY, activityTy)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            startActivityWithAnim(intent)
            finishAffinity()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun goToSignInActivity(activityTy: Int) {
        try {
            val intent = Intent(this, SignInActivity::class.java)
                .apply {
                    putExtra(ACTIVITY, activityTy)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
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
            val intent = Intent(this, GroupActivity::class.java)
                .apply {
                    putExtra(ACTIVITY, activityType)
                    addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            startActivityWithAnim(intent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showUseTerm(@Suppress("UNUSED_PARAMETER") vw: View) {
        showUseTerm()
    }

    fun showPrivacy(@Suppress("UNUSED_PARAMETER") vw: View) {
        showPrivacy()
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
            logException(e)
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
            logException(e)
        }
    }

    fun restart() {
        startActivityWithAnim(intent)
        finish()
    }
}