package com.moondroid.project01_meetingapp.ui.view.activity

import android.view.View
import androidx.activity.viewModels
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityReportBinding
import com.moondroid.project01_meetingapp.ui.viewmodel.ReportViewModel
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.IntentParam
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BaseActivity<ActivityReportBinding>(R.layout.activity_report) {
    private lateinit var reportId: String
    private val viewModel: ReportViewModel by viewModels()


    override fun init() {
        binding.activity = this
        reportId = intent.getStringExtra(IntentParam.REPORT_ID).toString()
        initViewModel()
    }

    fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.reportResponse.observe(this) {

            log("reportResponse , observe() , Response => $it")
            when (it.code) {
                ResponseCode.SUCCESS -> {
                    showMessage(getString(R.string.alm_report_success)) {
                        finish()
                    }
                }

                else -> {

                }
            }
        }
    }

    fun getReportName(): String {
        val name = intent.getStringExtra(IntentParam.REPORT_NAME)

        val builder = StringBuilder()
        builder.append(getString(R.string.cmn_report_target))
            .append(" : ")
            .append(name)
        return builder.toString()
    }

    fun report(@Suppress("UNUSED_PARAMETER") vw: View) {
        val message = binding.etMessage.text.toString()

        if (message.isEmpty()) {
            toast(getString(R.string.error_input_report_reason))
            return
        }

        val builder: StringBuilder = StringBuilder()

        builder.append(getString(R.string.cmn_report_target))
            .append(" : ")
            .append(reportId)
            .append("\n")
            .append(getString(R.string.cmn_report_reason))
            .append(" : ")
            .append(message)
            .append("\n")
            .append(getString(R.string.cmn_report_date))
            .append(" : ")
            .append(DMUtils.getToday("yyyy-MM-dd HH:mm:ss"))

        showMessage2(getString(R.string.alm_report_user_check)) {
            viewModel.reportUser(user!!.id, builder.toString())
        }
    }
}