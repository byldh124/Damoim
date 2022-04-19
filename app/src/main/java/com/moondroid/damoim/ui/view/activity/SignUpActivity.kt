package com.moondroid.damoim.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.moondroid.damoim.R
import com.moondroid.damoim.databinding.ActivitySignUpBinding
import com.moondroid.damoim.utils.view.logException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var id: String
    private lateinit var name: String
    private lateinit var birth: String
    private lateinit var gender: String
    private lateinit var address: String
    private lateinit var interest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
            binding.activity = this
        } catch (e: Exception) {
            logException(e)
        }
    }

    fun sgnp(@Suppress("UNUSED_PARAMETER") vw: View) {
        id = binding.etId.text.toString()
    }

    fun checkId(@Suppress("UNUSED_PARAMETER") vw: View) {

    }

    fun goToLocationActivity(@Suppress("UNUSED_PARAMETER") vw: View){

    }

    fun showDateDialog(@Suppress("UNUSED_PARAMETER") vw: View){

    }

    fun goToInterestActivity(@Suppress("UNUSED_PARAMETER") vw: View){

    }
}