package com.moondroid.project01_meetingapp.presentation.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.dialog.ButtonDialog
import com.moondroid.project01_meetingapp.presentation.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.presentation.dialog.WebViewDialog
import com.moondroid.project01_meetingapp.presentation.ui.common.crop.CropImageActivity
import com.moondroid.project01_meetingapp.presentation.ui.group.main.GroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.home.HomeActivity
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInActivity
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.image.ImageHelper


/**
 * 의존성 주입을 위해 해당 클래스를 상속받는 액티비티는 @AndroidEntryPoint 어노테이션을 달아줘야 함
 **/
open class BaseActivity : AppCompatActivity() {

    protected val mContext by lazy { this }

    private var loadingDialog: LoadingDialog? = null
    private var webViewDialog: WebViewDialog? = null

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBack()
        }
    }

    open fun onBack() = finish()

    private var onResult: (Intent?) -> Unit = {}

    private val resultLauncher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            onResult(it.data)
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onBackPressedDispatcher.addCallback(this, callback)
    }

    @SuppressLint("WrongConstant")
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    fun startActivityForResult(intent: Intent, onResult: (Intent?) -> Unit) {
        this.onResult = onResult
        resultLauncher.launch(intent)
    }

    /**
     * 이미지 퍼미션 확인
     **/
    private fun checkImagePermission(ratio: Int = 1, onResult: (Uri) -> Unit) {
        val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                mContext, imagePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getImage(ratio, onResult)
        } else {
            registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    getImage(ratio, onResult)
                }
            }.launch(imagePermission)
        }
    }

    fun getCroppedImage(ratio: Int = 1, onResult: (Uri) -> Unit) {
        checkImagePermission(ratio, onResult)
    }

    private fun getImage(ratio: Int = 1, onResult: (Uri) -> Unit) {
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        ) { intent ->
            intent?.data?.let { uri ->
                startActivityForResult(Intent(mContext, CropImageActivity::class.java).apply {
                    putExtra(IntentParam.CROP_IMAGE_WITH_RATIO, ratio)
                    data = uri
                }) {
                    debug("debug page1")
                    it?.data?.let(onResult)
                }
            }
        }
    }

    fun showMessage(msg: String, onClick: () -> Unit = {}) {
        val builder = ButtonDialog.Builder(mContext).apply {
            message = msg
            setPositiveButton("OK", onClick)
        }

        builder.build()
    }

    fun setResultAndFinish(intent: Intent? = null) {
        setResult(RESULT_OK, intent)
        finish()
    }

    fun serverError(code: Int, onClick: () -> Unit = {}) {
        showMessage(getString(R.string.error_server_request_fail, code), onClick)
    }

    fun networkError(throwable: Throwable, onClick: () -> Unit = {}) {
        showMessage("네트워크 에러\n${throwable.message.toString()}", onClick)
        logException(throwable)
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

    private fun showLoading() {
        loadingDialog?.show() ?: run {
            loadingDialog = LoadingDialog(mContext).apply {
                show()
            }
        }
    }

    private fun hideLoading() {
        loadingDialog?.cancel()
    }

    protected fun goToHomeActivity() {
        val intent = Intent(mContext, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        finishAffinity()
    }

    fun goToSignInActivity() {
        val intent = Intent(mContext, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
        finishAffinity()
    }

    /**
     * GROUP_ITEM 클릭시 액티비티 전환
     */
    fun goToGroupActivity() {
        val intent = Intent(mContext, GroupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    fun showUseTerm() {
        if (webViewDialog == null) {
            webViewDialog = WebViewDialog(mContext, WebViewDialog.TYPE.USE_TERM)
        } else {
            webViewDialog?.setType(WebViewDialog.TYPE.USE_TERM)
        }
        webViewDialog?.show()
    }

    fun showPrivacy() {
        if (webViewDialog == null) {
            webViewDialog = WebViewDialog(mContext, WebViewDialog.TYPE.PRIVACY)
        } else {
            webViewDialog?.setType(WebViewDialog.TYPE.PRIVACY)
        }
        webViewDialog?.show()
    }
}