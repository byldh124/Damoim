package com.moondroid.project01_meetingapp.presentation.ui.sign

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
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
import com.moondroid.project01_meetingapp.presentation.base.viewModel
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.sign.SignInViewModel.SignInEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.collectEvent
import com.moondroid.project01_meetingapp.utils.ViewExtension.snack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID


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
    private val viewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel

        collectEvent(viewModel.eventFlow, ::handleEvent)

        initView()
    }


    private fun handleEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.Home -> goToHomeActivity()

            is SignInEvent.SignUpSocial -> {
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                intent.putExtra(IntentParam.ID, event.id)
                intent.putExtra(IntentParam.NAME, event.name)
                intent.putExtra(IntentParam.THUMB, event.thumb)

                startActivity(intent)
            }

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
        if (token != null) {
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

    private fun getGoogleAccount() {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { acc, byte ->
            acc + "%02x".format(byte)
        }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("1025278143222-6jiup8ffv4hf72jc5pc45a4avdgf3hbu.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val result = CredentialManager.create(mContext).getCredential(
                    context = mContext,
                    request = request
                )
                handleSignIn(result)

            } catch (e: NoCredentialException) {
                // Credential 없으면 구글 계정 추가 요청
                val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
                intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                startActivityForResult(intent) {
                    getGoogleAccount()
                }
            } catch (e: GetCredentialException) {
                logException(e)
            }
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        debug("credential : ${credential.javaClass.simpleName}}")
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        /*val id = googleIdTokenCredential.id
                        val name = googleIdTokenCredential.displayName.toString()
                        val thumb = if (googleIdTokenCredential.profilePictureUri == null) {
                            DEFAULT_PROFILE_IMG
                        } else {
                            googleIdTokenCredential.profilePictureUri.toString()
                        }*/
                        //viewModel.signInSocial(id, name, thumb)

                        //
                        val googleIdToken = googleIdTokenCredential.idToken
                        val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                        val user = Firebase.auth.signInWithCredential(authCredential).await().user
                        user?.let {
                            val id = it.uid
                            val name = it.displayName.toString()
                            val thumb = it.photoUrl?.toString() ?: DEFAULT_PROFILE_IMG

                            viewModel.signInSocial(id, name, thumb)
                        }
                    } catch (e: GoogleIdTokenParsingException) {
                        logException(e)
                    }
                }
            }

            else -> {
                showMessage("지원하지 않는 자격 증명 방식입니다.\n다른 방법으로 로그인해주세요.")
            }

        }
    }

    /*private fun getGoogleAccount() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val singInIntent = mGoogleSignInClient.signInIntent

        startActivityForResult(singInIntent) { intent ->

            debug("get google Account")
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
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
    }*/

    /**
     * 회원가입 화면 전환
     */
    private fun goToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}