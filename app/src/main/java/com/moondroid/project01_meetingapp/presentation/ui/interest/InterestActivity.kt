package com.moondroid.project01_meetingapp.presentation.ui.interest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.moondroid.damoim.common.Extension.init
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityInterestBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.utils.DMUtils
import dagger.hilt.android.AndroidEntryPoint


/**
 * 관심사 선택
 *  -> 선택된 관심사를 Intent Extra 로 전달
 */

@AndroidEntryPoint
class InterestActivity : BaseActivity(R.layout.activity_interest) {
    private val binding by viewBinding(ActivityInterestBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            e.logException()
        }
    }
}