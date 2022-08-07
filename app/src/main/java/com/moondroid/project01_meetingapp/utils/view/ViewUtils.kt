package com.moondroid.project01_meetingapp.utils.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.view.dialog.LoadingDialog
import com.moondroid.project01_meetingapp.ui.viewmodel.BaseViewModel
import com.moondroid.project01_meetingapp.utils.DMLog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

var dialog: LoadingDialog? = null

fun Activity.logException(exception: Exception) {
    FirebaseCrashlytics
        .getInstance()
        .log(exception.message.toString())
    DMLog.e("[${this.javaClass.simpleName} logException]::$exception")
}

fun Activity.logException(t: Throwable) {
    FirebaseCrashlytics
        .getInstance()
        .log(t.message.toString())
    DMLog.e("[${this.javaClass.simpleName} logException]::$t")
}


fun Activity.log(msg:String){
    DMLog.e("[${this.javaClass.simpleName}] , $msg ")
}

fun BaseViewModel.log(msg: String){
    DMLog.e("[${this.javaClass.simpleName}] , $msg ")
}

fun Activity.exitApp(){
    this.moveTaskToBack(true);
    this.finish();
    android.os.Process.killProcess(android.os.Process.myPid());
}

fun Activity.startActivityWithAnim(intent: Intent){
    this.startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, 0)
}

fun Fragment.logException(exception: Exception) {
    FirebaseCrashlytics
        .getInstance()
        .log(exception.message.toString())
    DMLog.e("[${this.javaClass.simpleName} logException]::$exception")
}

fun Fragment.logException(t: Throwable) {
    FirebaseCrashlytics
        .getInstance()
        .log(t.message.toString())
    DMLog.e("[${this.javaClass.simpleName} logException]::$t")
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone(shouldBeGone: Boolean) {
    if (shouldBeGone) visibility = View.GONE
    else visible()
}

fun Button.enabled(ctx: Context) {
    isEnabled = true
    background = ContextCompat.getDrawable(ctx, R.drawable.ic_launcher_background)
}

fun Button.disEnabled(ctx: Context) {
    isEnabled = false
    background = ContextCompat.getDrawable(ctx, R.drawable.ic_launcher_foreground)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

    })
}


fun String.toReqBody(): RequestBody {
    return toRequestBody("text-plain".toMediaTypeOrNull())
}

