package com.moondroid.project01_meetingapp.presentation.ui.profile

import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityReportBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BaseActivity() {
    private lateinit var reportId: String
    private val binding by viewBinding(ActivityReportBinding::inflate)
    private val viewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        reportId = intent.getStringExtra(IntentParam.REPORT_ID).toString()
    }

    fun getReportName(): String {
        val name = intent.getStringExtra(IntentParam.REPORT_NAME)

        val builder = StringBuilder()
        builder.append(getString(R.string.cmn_report_target))
            .append(" : ")
            .append(name)
        return builder.toString()
    }

    fun report() {
        try {
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


                viewModel.reportUser(builder.toString())
        } catch (e: Exception) {
            logException(e)
        }
    }
}