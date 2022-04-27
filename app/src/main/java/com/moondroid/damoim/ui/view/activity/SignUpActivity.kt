package com.moondroid.damoim.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.moondroid.damoim.R
import com.moondroid.damoim.databinding.ActivitySignUpBinding
import com.moondroid.damoim.ui.viewmodel.SignInViewModel
import com.moondroid.damoim.ui.viewmodel.SignUpViewModel
import com.moondroid.damoim.utils.view.logException
import com.moondroid.damoim.utils.view.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

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
        name = binding.etName.text.toString()
        birth = binding.tvBirth.text.toString()
    }

    fun checkId(@Suppress("UNUSED_PARAMETER") vw: View) {
        val id = binding.etId.text.toString()
        viewModel.checkId(id)

        viewModel.usableId.observe(this, Observer {
            if (it){
                toast("사용가능한 아이디 입니다.")
            } else {
                toast("이미 존재하는 아이디 입니다.")
            }
        })
    }

    fun goToLocationActivity(@Suppress("UNUSED_PARAMETER") vw: View){

    }

    fun showDateDialog(@Suppress("UNUSED_PARAMETER") vw: View){

    }

    fun goToInterestActivity(@Suppress("UNUSED_PARAMETER") vw: View){

    }
}