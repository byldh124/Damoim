package com.moondroid.project01_meetingapp.presentation.view.signin

import android.content.Intent
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.user.UserApiClient
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignInBinding
import com.moondroid.project01_meetingapp.presentation.view.signup.SignUpActivity
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.log
import com.moondroid.project01_meetingapp.utils.logException
import com.moondroid.project01_meetingapp.utils.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint


/**
 * 로그인
 *
 * 1. 일반 회원 가입
 *  1) 기입 정보 유효성 체크
 *  2) Salt 값 요청
 *  3) 아이디, 해시값 체크
 *  4) 로그인
 *
 * 2. 카카오 로그인
 *  1) 카카오톡 로그인 요청
 *  2) (카카오톡이 설치되어있지 않은 경우) 카카오 계정 로그인 요청
 *  3) 기존 정보 확인
 *   3-1) 기존 정보 있을 경우 : 로그인
 *   3-2) 기존 정보 없을 경우 : 아이디, 이름, 썸네일 체크 후 회원가입 화면으로 전환
 *
 */
@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: SignInViewModel by viewModels()

    override fun init() {
        binding.model = viewModel
        initView()
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: Event) {
        when (event) {
            is Event.Loading -> {
                showLoading(event.show)
            }

            is Event.Message -> {
                showMessage(event.message)
            }

            is Event.Home -> {
                goToHomeActivity(ActivityTy.SIGN_IN)
            }

            is Event.SignUp -> {
                goToSignUp()
            }

            is Event.SignUpSocial -> {
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                intent.putExtra(RequestParam.ID, event.id)
                intent.putExtra(RequestParam.NAME, event.name)
                intent.putExtra(RequestParam.THUMB, event.thumb)

                startActivityWithAnim(intent)
            }

        }
    }

    private fun initView() {
        try {
            val textView = binding.icGoogle.getChildAt(0) as TextView
            textView.text = getString(R.string.cmn_sign_in_google)
            binding.txtUseTerm.setOnClickListener { showUseTerm() }
            binding.txtPrivacy.setOnClickListener { showPrivacy() }
            binding.icGoogle.setOnClickListener { getGoogleAccount() }
            binding.icKakao.setOnClickListener { getKakaoProfile() }

        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * 카카오톡 로그인 요청 [에러발생할 경우 -> 카카오 계정 로그인 요청]
     */
    private fun getKakaoProfile() {
        try {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    logException(error)
                    getKakaoProfile2()
                }
                if (token != null) {
                    UserApiClient.instance.me { user, error2 ->
                        if (error2 != null) {
                            logException(error2)
                        }
                        if (user != null) {
                            val id = user.id.toString()
                            val name = user.kakaoAccount?.profile?.nickname.toString()
                            val thumb = user.kakaoAccount?.profile?.profileImageUrl.toString()
                            if (name.isNotEmpty() && thumb.isNotEmpty()) {
                                viewModel.signInSocial(id, name, thumb)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 카카오 계정 로그인 요청
     */
    private fun getKakaoProfile2() {
        try {
            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                if (error != null) {
                    logException(error)
                }
                if (token != null) {
                    UserApiClient.instance.me { user, error2 ->
                        if (error2 != null) {
                            logException(error2)
                        }
                        if (user != null) {
                            val id = user.id.toString()
                            val name = user.kakaoAccount?.profile?.nickname.toString()
                            val thumb = user.kakaoAccount?.profile?.profileImageUrl.toString()
                            if (name.isNotEmpty() && thumb.isNotEmpty()) {
                                viewModel.signInSocial(id, name, thumb)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logException(e)
        }
    }

    //Google Account Activity Result
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        try {
            if (it.resultCode == RESULT_OK) {
                val data: Intent? = it.data
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                val id = account.id.toString()
                val name = account.displayName.toString()
                val thumb = if (account.photoUrl == null) {
                    DEFAULT_PROFILE_IMG
                } else {
                    account.photoUrl.toString()
                }

                viewModel.signInSocial(id, name, thumb)
            }
        } catch (e: Exception) {
            logException(e)
        }
    }


    private fun getGoogleAccount() {
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)

            val singInIntent = mGoogleSignInClient.signInIntent

            resultLauncher.launch(singInIntent)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 회원가입 화면 전환
     */
    private fun goToSignUp() {
        log("goToSignUp api call")
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra(IntentParam.ACTIVITY, ActivityTy.SIGN_IN)
        startActivityWithAnim(intent)
    }
}