package com.moondroid.project01_meetingapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.ActivityCreateGroupBinding

class CreateGroupActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_group)
        binding.activity = this


    }
}