package com.moondroid.project01_meetingapp.presentation.ui.group

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.ContextCompat
import com.moondroid.damoim.common.DMRegex
import com.moondroid.damoim.common.IntentParam
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.group.CreateGroupUseCase
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityCreateBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.group.main.GroupActivity
import com.moondroid.project01_meetingapp.presentation.ui.interest.InterestActivity
import com.moondroid.project01_meetingapp.presentation.ui.location.LocationActivity
import com.moondroid.project01_meetingapp.utils.ViewExtension.afterTextChanged
import com.moondroid.project01_meetingapp.utils.ViewExtension.glide
import com.moondroid.project01_meetingapp.utils.ViewExtension.init
import com.moondroid.project01_meetingapp.utils.ViewExtension.toast
import com.moondroid.project01_meetingapp.utils.ViewExtension.visible
import com.moondroid.project01_meetingapp.utils.image.ImageHelper.getPathFromUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class CreateGroupActivity : BaseActivity() {
    private val binding by viewBinding(ActivityCreateBinding::inflate)
    private var path: String? = null                                // 썸네일 이미지 Real path
    private var interest: String? = null                            // 관심사
    private var location: String? = null                            // 모임 지역
    private lateinit var title: String                              // 모임명
    private lateinit var purpose: String                            // 모임 목적

    @Inject
    lateinit var createGroupUseCase: CreateGroupUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        Handler(Looper.getMainLooper()).postDelayed(::toInterest, 500)
    }

    /**
     * View 초기화
     */
    private fun initView() {
        binding.toolbar.init(mContext)

        binding.tvMsgLength.text =
            String.format(getString(R.string.cmn_message_length), binding.etPurpose.length())

        binding.etPurpose.afterTextChanged {
            binding.tvMsgLength.text = String.format(getString(R.string.cmn_message_length), it.length)
        }

        binding.wrThumb.setOnClickListener {
            checkImagePermission()
        }

        binding.icInterest.setOnClickListener {
            toInterest()
        }

        binding.tvLocation.setOnClickListener {
            locationLauncher.launch(Intent(mContext, LocationActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            checkField()
        }
    }

    /**
     * 이미지 퍼미션 확인
     **/
    private fun checkImagePermission() {
        val imagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                mContext, imagePermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getImage()
        } else {
            requestPermissionLauncher.launch(imagePermission)
        }
    }

    /** 이미지 퍼미션 런쳐 **/
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getImage()
        } else {

        }
    }

    /**
     * 썸네일 이미지 선택
     */
    private fun getImage() {
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        imageLauncher.launch(intent)
    }

    private val imageLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                path = getPathFromUri(this, it)
                if (!path.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(path)
                    binding.ivThumb.glide(bitmap)
                    binding.tvEmptyImage.visible(false)
                }
            }
        }
    }


    private fun toInterest() {
        interestLauncher.launch(Intent(mContext, InterestActivity::class.java))
    }

    private val interestLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                interest = getString(intent.getIntExtra(IntentParam.INTEREST, 0))
                val resId = intent.getIntExtra(IntentParam.INTEREST_ICON, 0)
                binding.icInterest.glide(resId)
            }
        }
    }

    private val locationLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                location = intent.getStringExtra(IntentParam.LOCATION).toString()
                binding.tvLocation.text = location
            }
        }
    }

    /**
     * 데이터 유효성 체크
     */
    private fun checkField() {
        title = binding.etTitle.text.toString()
        purpose = binding.etPurpose.text.toString()

        if (!title.matches(DMRegex.TITLE)) {
            toast(getString(R.string.error_title_mismatch))
        } else if (purpose.isEmpty()) {
            toast(getString(R.string.error_purpose_empty))
        } else if (location.isNullOrEmpty()) {
            toast(getString(R.string.error_location_empty))
        } else if (interest.isNullOrEmpty()) {
            toast(getString(R.string.error_interest_empty))
            toInterest()
        } else if (path.isNullOrEmpty()) {
            toast(getString(R.string.error_thumb_empty))
        } else {
            createGroup(location!!, interest!!, File(path!!))
        }
    }

    /**
     * 모임 생성 요청
     */
    private fun createGroup(location: String, interest: String, file: File) {
        CoroutineScope(Dispatchers.Main).launch {
            createGroupUseCase(DMApp.profile.id, title, location, purpose, interest, file).collect { result ->
                result.onSuccess {
                    DMApp.group = it
                    val sIntent = Intent(mContext, GroupActivity::class.java)
                    sIntent.putExtra(IntentParam.SHOW_TUTORIAL, true)
                    startActivity(sIntent)
                    finish()
                }.onFail {
                    serverError(it)
                }.onError {
                    networkError(it)
                }
            }
        }
    }
}