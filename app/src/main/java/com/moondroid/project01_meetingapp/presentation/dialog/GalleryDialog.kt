package com.moondroid.project01_meetingapp.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.presentation.base.BaseDialog
import com.moondroid.project01_meetingapp.databinding.DialogGalleryBinding

class GalleryDialog(context: Context, private val list: List<String>) : BaseDialog(context) {
    var position: Int = 0
    private lateinit var binding: DialogGalleryBinding
    private lateinit var adapter: GalleryDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogGalleryBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        initView()

        binding.root.setOnClickListener {
            cancel()
        }

        binding.icExit.setOnClickListener {
            cancel()
        }
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
}