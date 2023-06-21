package com.moondroid.damoim.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Extension {
    fun Any.debug(msg: String) {
        Log.e("모임대장" , "[${this.javaClass.simpleName.trim()}] | $msg")
    }

    fun Throwable.logException() {
        FirebaseCrashlytics
            .getInstance()
            .log(stackTraceToString())
    }


    fun Activity.exitApp() {
        this.moveTaskToBack(true)
        this.finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun Activity.startActivityWithAnim(intent: Intent) {
        this.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    fun View.visible(isVisible: Boolean = true) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
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

    fun Context.toast(@StringRes resId: Int) {
        toast(getString(resId))
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    fun Toolbar.init(activity: AppCompatActivity) {
        try {
            activity.setSupportActionBar(this)
            activity.supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowTitleEnabled(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


