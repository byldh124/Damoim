package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogGalleryBinding
import com.moondroid.project01_meetingapp.presentation.view.adapter.GalleryDialogAdapter

class GalleryDialog(context: Context, val list: List<String>) : BaseDialog(context) {
    var position: Int = 0
    lateinit var binding: DialogGalleryBinding
    lateinit var adapter: GalleryDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_gallery, null, false)
        binding.dialog = this
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        adapter = GalleryDialogAdapter(context)
        adapter.update(list)
        binding.pager.adapter = adapter
    }

    override fun show() {
        super.show()
        binding.pager.currentItem = position
    }

    fun exit(@Suppress("UNUSED_PARAMETER")vw: View) {
        cancel()
    }
}