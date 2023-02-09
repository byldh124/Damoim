package com.moondroid.project01_meetingapp.presentation.view.interest

import android.app.Activity
import android.content.Intent
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityInterestBinding
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.init
import com.moondroid.project01_meetingapp.utils.logException
import dagger.hilt.android.AndroidEntryPoint


/**
 * 관심사 선택
 *  -> 선택된 관심사를 Intent Extra 로 전달
 */

@AndroidEntryPoint
class InterestActivity : BaseActivity<ActivityInterestBinding>(R.layout.activity_interest) {
    override fun init() {
        intiView()
    }

    /**
     * Initialize View
     */
    private fun intiView() {
        try {
            binding.toolbar.init(this)

            val adapter = InterestAdapter{
                val intent = Intent()
                intent.putExtra(IntentParam.INTEREST, DMUtils.getStringId(this, String.format("interest_%02d", it + 1 )))
                intent.putExtra(IntentParam.INTEREST_ICON, DMUtils.getDrawableId(this, String.format("ic_interest_%02d", it + 1)))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            binding.recycler.adapter = adapter
        } catch (e: Exception) {
            logException(e)
        }
    }
}