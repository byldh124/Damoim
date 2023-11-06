package com.moondroid.project01_meetingapp.presentation.ui.common.crop

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.core.net.toUri
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.databinding.ActivityCropImageBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseActivity
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.utils.ViewExtension.setupToolbar
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class CropImageActivity : BaseActivity() {
    private val binding: ActivityCropImageBinding by viewBinding(ActivityCropImageBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setupToolbar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val uri = intent.data
        val ratio = intent.getIntExtra(IntentParam.CROP_IMAGE_WITH_RATIO, 1)

        binding.cropImageView.setImageUriAsync(uri)
        binding.cropImageView.setAspectRatio(ratio, 1)

        binding.btnSave.setOnClickListener {
            try {
                cropImageAndSaveCacheFile()
            } catch (e: Exception) {
                logException(e)
            }
        }
    }

    @Throws(FileNotFoundException::class, IOException::class, IllegalStateException::class)
    private fun cropImageAndSaveCacheFile() {
        val cropBitmap = binding.cropImageView.getCroppedImage()
            ?: throw IllegalStateException("Cropped image must not be null")

        val cacheStorage = cacheDir
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val tempFile = File(cacheStorage, fileName)

        if (!tempFile.exists()) {
            tempFile.createNewFile()
        }
        val out = FileOutputStream(tempFile)

        cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

        out.close()

        val recvIntent = Intent()
        recvIntent.data = tempFile.toUri()
        setResultAndFinish(recvIntent)
    }
}