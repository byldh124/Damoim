package com.moondroid.project01_meetingapp.ui.view.activity

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivitySignInBinding
import com.moondroid.project01_meetingapp.model.DMUser
import com.moondroid.project01_meetingapp.realm.DMRealm
import com.moondroid.project01_meetingapp.ui.viewmodel.SignInViewModel
import com.moondroid.project01_meetingapp.utils.*
import com.moondroid.project01_meetingapp.utils.firebase.DMAnalyze
import com.moondroid.project01_meetingapp.utils.firebase.DMCrash
import com.moondroid.project01_meetingapp.utils.view.log
import com.moondroid.project01_meetingapp.utils.view.logException
import com.moondroid.project01_meetingapp.utils.view.startActivityWithAnim
import com.moondroid.project01_meetingapp.utils.view.toast
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

    private lateinit var id: String                     // ID
    private lateinit var pw: String                     // 비밀번호
    private lateinit var name: String                   // 이름   [카카오]
    private lateinit var thumb: String                  // 썸네일 [카카오]

    private val idRegex =
        Regex("^[a-zA-Z0-9]{5,16}$")                                      // 아이디 정규식 [영문, 숫자 5-16 글자]
    private val pwRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,}\$")          // 비밀번호 정규식 [영문, 숫자, 특수기혹 8글자 이상]

    override fun init() {
        binding.activity = this
        initViewModel()
        initView()
    }

    private fun initView() {
        try {
            val textView = binding.icGoogle.getChildAt(0) as TextView
            textView.text = getString(R.string.cmn_sign_in_google)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * Observe ViewModel
     */
    private fun initViewModel() {
        viewModel.showLoading.observe(this) {
            showLoading(it)
        }

        viewModel.showError.observe(this) {
            showNetworkError(it)
        }

        viewModel.saltResponse.observe(this) {
            try {
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        val salt = it.body.asString
                        val hashPw = DMUtils.hashingPw(pw, salt)
                        signIn(hashPw)
                    }

                    ResponseCode.NOT_EXIST -> {
                        showMessage(getString(R.string.error_id_not_exist)) {
                            binding.etId.requestFocus()
                        }
                    }
                    else -> {
                        showMessage(getString(R.string.error_sign_in_fail), "E01 : ${it.code}")
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

        viewModel.signInResponse.observe(this) {
            try {
                log("signIn() , Response = $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        DMAnalyze.logEvent("SignIn_Success")
                        val userInfo = it.body
                        user = Gson().fromJson(userInfo, DMUser::class.java)
                        DMAnalyze.setProperty(user!!)
                        DMCrash.setProperty(user!!.id)
                        DMRealm.getInstance().writeBlocking {
                            copyToRealm(user!!)
                        }

                        if (binding.checkBox.isChecked) {
                            DMApp.prefs.putBoolean(PrefsKey.AUTO_LOGIN, true)
                        }

                        goToHomeActivity()
                    }

                    ResponseCode.NOT_EXIST -> {
                        showMessage(getString(R.string.error_id_not_exist)) {
                            binding.etId.requestFocus()
                        }
                    }

                    ResponseCode.INVALID_VALUE -> {
                        showMessage(getString(R.string.error_wrong_password)) {
                            binding.etPw.requestFocus()
                        }
                    }

                    else -> {
                        showMessage(getString(R.string.error_sign_in_fail, "E02 : ${it.code}"))
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }

        viewModel.kakaoSignInResponse.observe(this) {
            try {
                log("signInKakao() , Response : $it")
                when (it.code) {
                    ResponseCode.SUCCESS -> {
                        DMAnalyze.logEvent("SignIn_Success[Kakao]")
                        val userInfo = it.body
                        user = Gson().fromJson(userInfo, DMUser::class.java)
                        DMRealm.getInstance().writeBlocking {
                            copyToRealm(user!!)
                        }
                        goToHomeActivity()
                    }

                    ResponseCode.NOT_EXIST -> {
                        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                        intent.putExtra(RequestParam.ID, id)
                        intent.putExtra(RequestParam.NAME, name)
                        intent.putExtra(RequestParam.THUMB, thumb)

                        startActivityWithAnim(intent)
                    }

                    else -> {
                        showMessage(getString(R.string.error_sign_in_fail), "E03 : ${it.code}")
                    }
                }
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    /**
     * 입력값 유효성 확인
     */
    fun checkField(@Suppress("UNUSED_PARAMETER") vw: View) {
        try {
            id = binding.etId.text.toString()
            pw = binding.etPw.text.toString()

            if (!id.matches(idRegex)) {
                toast(getString(R.string.error_id_mismatch))
            } else if (!pw.matches(pwRegex)) {
                toast(getString(R.string.error_password_mismatch))
            } else {
                getSalt()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }


    /**
     * Salt 값 요청 [회원가입에서 사용했던 값]
     */
    private fun getSalt() {
        try {
            viewModel.getSalt(id)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 로그인
     */
    private fun signIn(hashPw: String) {
        try {

            val body = JsonObject()
            body.addProperty(RequestParam.ID, id)
            body.addProperty(RequestParam.HASH_PW, hashPw)

            viewModel.signIn(body)
        } catch (e: Exception) {
            logException(e)
        }
    }

    /**
     * 카카오톡 로그인 요청 [에러발생할 경우 -> 카카오 계정 로그인 요청]
     */
    fun getKakaoProfile(vw: View) {
        try {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    logException(error)
                    getKakaoProfile2(vw)
                }
                if (token != null) {
                    UserApiClient.instance.me { user, error2 ->
                        if (error2 != null) {
                            logException(error2)
                        }
                        if (user != null) {
                            id = user.id.toString()
                            name = user.kakaoAccount?.profile?.nickname.toString()
                            thumb = user.kakaoAccount?.profile?.profileImageUrl.toString()
                            if (name.isNotEmpty()) {
                                if (thumb.isNotEmpty()) {
                                    signInWithKakao()
                                }
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
    private fun getKakaoProfile2(@Suppress("UNUSED_PARAMETER") vw: View) {
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
                            id = user.id.toString()
                            name = user.kakaoAccount?.profile?.nickname.toString()
                            thumb = user.kakaoAccount?.profile?.profileImageUrl.toString()
                            if (name.isNotEmpty()) {
                                if (thumb.isNotEmpty()) {
                                    signInWithKakao()
                                }
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
     * 카카오 로그인
     * [a] 기존 데이터 o : 회원 정보 요청
     * [b] 기존 데이터 x : 회원가입 화면 전환
     */
    private fun signInWithKakao() {
        try {
            val body = JsonObject()

            body.addProperty(RequestParam.ID, id)

            viewModel.signInKakao(body)

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

                id = account.id.toString()
                name = account.displayName.toString()
                thumb = if (account.photoUrl == null) {
                    DEFAULT_PROFILE_IMG
                } else {
                    account.photoUrl.toString()
                }

                signInWithKakao()
            }
        } catch (e: Exception) {
            logException(e)
        }
    }


    fun getGoogleAccount() {
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

    private fun goToHomeActivity() {
        goToHomeActivity(ActivityTy.SIGN_IN)
    }

    /**
     * 회원가입 화면 전환
     */
    fun goToSignUp(@Suppress("UNUSED_PARAMETER") view: View) {
        log("goToSignUp api call")
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra(IntentParam.ACTIVITY, ActivityTy.SIGN_IN)
        startActivityWithAnim(intent)
    }
}