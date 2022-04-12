package com.moondroid.damoim.base

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.damoim.ui.view.activity.HomeActivity
import com.moondroid.damoim.ui.view.activity.SignInActivity
import com.moondroid.damoim.utils.Constants
import com.moondroid.damoim.utils.DMLog
import com.moondroid.damoim.utils.view.logException

open class BaseActivity : AppCompatActivity() {

    fun goToHomActivity(activityTy: Int) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(Constants.ACTIVITY_TY, activityTy)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    fun goToSgnnActivity(activityTy: Int) {
        val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra(Constants.ACTIVITY_TY, activityTy)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, 0)
    }
}