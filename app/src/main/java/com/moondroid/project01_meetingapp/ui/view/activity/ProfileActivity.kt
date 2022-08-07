package com.moondroid.project01_meetingapp.ui.view.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.application.DMApp
import com.moondroid.project01_meetingapp.base.BaseActivity
import com.moondroid.project01_meetingapp.databinding.ActivityProfileBinding
import com.moondroid.project01_meetingapp.model.User
import com.moondroid.project01_meetingapp.ui.viewmodel.ProfileViewModel
import com.moondroid.project01_meetingapp.utils.Constants
import com.moondroid.project01_meetingapp.utils.DMLog
import com.moondroid.project01_meetingapp.utils.DMUtils
import com.moondroid.project01_meetingapp.utils.view.afterTextChanged
import com.moondroid.project01_meetingapp.utils.view.toReqBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeName
import java.io.File

class ProfileActivity : BaseActivity() {

    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: ActivityProfileBinding
    private var path: String? = null
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        user = DMApp.user
        binding.activity = this

        DMLog.e("[ProfileActivity::onCreate] User information : $user")

        initView()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.tvMsgLength.text = "${binding.etMsg.length()}/50자"
        binding.etMsg.afterTextChanged {
            binding.tvMsgLength.text = "${it.length}/50자"
        }

        if (DMApp.user.gender == getString(R.string.cmn_female)) {
            binding.rg.check(R.id.rbFemale)
        }
    }

    fun toLocation(@Suppress("UNUSED_PARAMETER") vw: View) {
        getLocation.launch(Intent(this, LocationActivity::class.java))
    }

    private val getLocation =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    binding.tvLocation.text =
                        it.getStringExtra(Constants.IntentParam.LOCATION).toString()
                }
            }
        }

    fun toBirth(@Suppress("UNUSED_PARAMETER") vw: View) {
        val array = user.birth.split(".")
        val year: Int = try {
            array[0].toInt()
        } catch (e: Exception) {
            1990
        }
        val month: Int = try {
            array[1].toInt() - 1
        } catch (e: Exception) {
            0
        }

        val date: Int = try {
            array[2].toInt()
        } catch (e: Exception) {
            1
        }

        val datePicker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                binding.tvBirth.text = String.format("%d.%d.%d", p1, p2 + 1, p3)
            }
        }, year, month, date)

        datePicker.show()
    }

    private val getImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val uri = it.data
                    uri?.let { u ->
                        path = DMUtils.getPathFromUri(this, u)
                        if (!path.isNullOrEmpty()) {
                            DMLog.e("image path : $path")
                            val bitmap = BitmapFactory.decodeFile(path)
                            Glide.with(this).load(bitmap).into(binding.thumb)
                        }
                    }
                }
            }
        }

    fun getImage(vw: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        getImage.launch(intent)
    }


    fun saveProfile(vw: View) {
        val id = DMApp.user.id
        val name = binding.etName.text.toString()
        val birth = binding.tvBirth.text.toString()
        val gender = if (binding.rbMale.isChecked) {
            getString(R.string.cmn_male)
        } else {
            getString(R.string.cmn_female)
        }
        val location = binding.tvLocation.text.toString()
        val thumb = DMApp.user.thumb
        val message = binding.etMsg.text.toString()

        val body = HashMap<String, RequestBody>()
        body["id"] = id.toReqBody()
        body["name"] = name.toReqBody()
        body["birth"] = birth.toReqBody()
        body["gender"] = gender.toReqBody()
        body["location"] = location.toReqBody()
        body["thumb"] = thumb.toReqBody()
        body["message"] = message.toReqBody()

        DMLog.e("[ProfileActivity::saveProfile] Request body = $body")

        var filePart: MultipartBody.Part? = null
        if (!path.isNullOrEmpty()) {

            DMLog.e("image path : $path")

            val file = path?.let { File(it) }
            file?.let {
                val requestBody = it.asRequestBody("image/*".toMediaType())
                filePart = MultipartBody.Part.createFormData("thumb", it.name, requestBody)
            }
        }

        viewModel.updateProfile(body, filePart)

        viewModel.profileResponse.observe(this) {
            when (it.code) {
                Constants.ResponseCode.SUCCESS -> {
                    showError("프로필 업데이트가 완료되었습니다.") {
                        DMApp.user = Gson().fromJson(it.body.asJsonObject, User::class.java)
                        DMApp.prefs.putString(Constants.PrefKey.USER_INFO, it.body.toString())
                        finish()
                    }
                }

                else -> {
                    showError("프로필 업데이트 실패 E01 [${it.code}]")
                }
            }
        }

    }
}