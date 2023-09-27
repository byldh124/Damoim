package com.moondroid.project01_meetingapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

internal object ViewExtension {
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.toast(@StringRes resId: Int) {
        toast(getString(resId))
    }


    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }


    fun View.snack(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }

    fun View.snack(@StringRes resId: Int) {
        snack(context.resources.getString(resId))
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

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Activity.exitApp() {
        this.moveTaskToBack(true)
        this.finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun View.visible(visible: Boolean = true) {
        visibility = if (visible) View.VISIBLE
        else View.GONE
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

    fun Activity.isWideScreen(): Boolean {
        return try {
            val metrics = resources.displayMetrics
            if (metrics == null) {
                false
            } else {
                val width = metrics.widthPixels
                width >= 580.dpToPixel(this)
            }
        } catch (e: Exception) {
            false
        }
    }

    fun ImageView.glide(@DrawableRes id: Int) {
        Glide.with(context).load(id).into(this)
    }

    fun ImageView.glide(url: String) {
        Glide.with(context)
            .load(url)
            .into(this)
    }

    fun ImageView.glide(bitmap: Bitmap) {
        Glide.with(context).load(bitmap).into(this)
    }

    fun Int.dpToPixel(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).roundToInt()
    }

    @SuppressLint("DiscouragedApi")
    fun getStringId(context: Context, name: String): Int {
        return try {
            context.resources.getIdentifier(name, "string", context.packageName)
        } catch (e: Exception) {
            0
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getDrawableId(context: Context, name: String): Int {
        return try {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        } catch (e: Exception) {
            0
        }
    }


    fun getInterestNum(context: Context, interest: String): Int {
        for (i: Int in 1..19) {
            val query = context.getString(getStringId(context, String.format("interest_%02d", i)))

            if (query == interest) {
                return i
            }
        }
        return 0
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val id = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (id > 0) {
            result = context.resources.getDimensionPixelSize(id)
        }

        return result
    }
}