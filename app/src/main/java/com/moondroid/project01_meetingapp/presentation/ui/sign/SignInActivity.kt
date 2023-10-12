package com.moondroid.project01_meetingapp.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.moondroid.damoim.common.Constants.DEFAULT_PROFILE_IMG
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivitySignInBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInViewModel.SignInEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.repeatOnStarted
import com.moondroid.project01_meetingapp.utils.ViewExtension.snack
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
class SignInActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySignInBinding::inflate)
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect {
                handleEvent(it)
            }
        }

        binding.lifecycleOwner = this
        binding.model = viewModel
        initView()
    }


    private fun handleEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.Loading -> showLoading(event.show)
            is SignInEvent.Message -> showMessage(event.message)
            is SignInEvent.Home -> goToHomeActivity()

            is SignInEvent.SignUpSocial -> {
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                intent.putExtra(IntentParam.ID, event.id)
                intent.putExtra(IntentParam.NAME, event.name)
                intent.putExtra(IntentParam.THUMB, event.thumb)

                startActivity(intent)
            }

            is SignInEvent.Error -> networkError(event.throwable)
            is SignInEvent.Fail -> serverError(event.code)
            SignInEvent.InvalidPw -> binding.root.snack(getString(R.string.error_wrong_password))
            SignInEvent.NotExist -> binding.root.snack(getString(R.string.error_id_not_exist))
        }
    }

    private fun initView() {
        with(binding) {
            val textView = icGoogle.getChildAt(0) as TextView
            textView.text = getString(R.string.cmn_sign_in_google)
            txtUseTerm.setOnClickListener { showUseTerm() }
            txtPrivacy.setOnClickListener { showPrivacy() }
            icGoogle.setOnClickListener { getGoogleAccount() }
            icKakao.setOnClickListener { getKakaoAccount() }
            btnSignUp.setOnClickListener { goToSignUp() }
        }
    }


    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null && token != null)  {
            requestSign()
        }
    }

    private fun getKakaoAccount() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
            UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(mContext, callback = kakaoCallback)
                } else {
                    if (token != null) {
                        requestSign()
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(mContext, callback = kakaoCallback)
        }
    }

    private fun requestSign() {
        UserApiClient.instance.me { user, throwable ->
            if (throwable != null) {
                logException(throwable)
            } else {
                user?.let {
                    val id = it.id?.toString() ?: throw IllegalStateException("ID must not be null")
                    val name = it.kakaoAccount?.profile?.nickname ?: ""
                    val thumb = it.kakaoAccount?.profile?.profileImageUrl ?: ""
                    viewModel.signInSocial(id, name, thumb)
                } ?: run {
                    //showMessage(getString(R.string.error_kakao_user_info))
                }
            }
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
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}