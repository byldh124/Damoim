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
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.model.DMUser
import com.moondroid.project01_meetingapp.realm.DMRealm
import com.moondroid.project01_meetingapp.room.UserDao
import com.moondroid.project01_meetingapp.ui.view.activity.GroupActivity
import com.moondroid.project01_meetingapp.ui.view.activity.HomeActivity
import com.moondroid.project01_meetingapp.ui.view.activity.SignInActivity
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.OneButtonDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.TwoButtonDialog
import com.moondroid.project01_meetingapp.ui.view.dialog.WebViewDialog
import com.moondroid.project01_meetingapp.utils.IntentParam.ACTIVITY
import com.moondroid.project01_meetingapp.utils.NETWORK_NOT_CONNECTED
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import java.util.concurrent.Executor
import javax.inject.Inject


/**
 * 의존성 주입을 위해 해당 클래스를 상속받는 액티비티는 @AndroidEntryPoint 어노테이션을 달아줘야 함
 **/
abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) : AppCompatActivity() {
    var user: DMUser? = null  //Binding -> 접근제한자 : Public

    protected lateinit var binding: T
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
        executor = ContextCompat.getMainExecutor(this)
        resetUserInfo()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        this.onBackPressedDispatcher.addCallback(this, callback)
        init()
    }

    protected fun resetUserInfo() {
        try {
            val items: RealmResults<DMUser> = DMRealm.getInstance().query<DMUser>().find()
            if (items.isNotEmpty()) {
                if (items.count() == 1 && !items.last().isEmpty()) {
                    user = items.last()
                } else {
                    deleteRealm()
                }
            }
        } catch (e:Exception) {
            logException(e)
        }
    }

    protected fun deleteRealm() {
        try {
            DMRealm.getInstance().writeBlocking {
                val writeTransactionItems = query<DMUser>().find()
                delete(writeTransactionItems)
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    override fun onResume() {
        super.onResume()
        resetUserInfo()
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
                    showMessage(getString(R.string.error_network_not_connected), code.toString(), onClick)
                } else {
                    showMessage(getString(R.string.error_network_fail), code.toString(), onClick)
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
        showMessage(msg) {}
    }

    fun showMessage(msg: String, code: String) {
        showMessage(String.format(msg, code))
    }

    fun showMessage(msg: String, code: String, onClick: () -> Unit) {
        showMessage(String.format(msg, code), onClick)
    }

    fun showMessage2(msg: String, onClick: () -> Unit) {
        try {
            if (twoButtonDialog == null) {
                twoButtonDialog = TwoButtonDialog(this, msg, onClick)
            } else {
                twoButtonDialog!!.msg = msg
                twoButtonDialog!!.onClick = onClick
            }

            twoButtonDialog!!.show()
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun showMessage2(msg: String) {
        showMessage2(msg) {}
    }

    fun showMessage2(msg: String, code: String) {
        showMessage(String.format(msg, code))
    }

    fun showMessage2(msg: String, code: String, onClick: () -> Unit) {
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
            val intent = Intent(this, HomeActivity::class.java).apply {
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
            val intent = Intent(this, SignInActivity::class.java).apply {
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
            val intent = Intent(this, GroupActivity::class.java).apply {
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