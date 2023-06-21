package com.moondroid.project01_meetingapp.presentation.ui.group.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.moondroid.damoim.common.Extension.debug
import com.moondroid.project01_meetingapp.DMApp
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.databinding.FragmentGroupGalleryBinding
import com.moondroid.project01_meetingapp.presentation.base.BaseFragment
import com.moondroid.project01_meetingapp.presentation.common.viewBinding
import com.moondroid.project01_meetingapp.presentation.ui.group.GroupActivity
import com.moondroid.project01_meetingapp.utils.BindingUtils.visible
import java.text.SimpleDateFormat

class GalleryFragment : BaseFragment(R.layout.fragment_group_gallery) {
    private val binding by viewBinding(FragmentGroupGalleryBinding::bind)

    private lateinit var activity: GroupActivity
    private lateinit var adapter: GalleryAdapter
    private lateinit var imgRef: StorageReference
    private var imgUri: Uri? = null
    private lateinit var firebaseDB: FirebaseDatabase
    private lateinit var galleryRef: DatabaseReference
    private val urlList = ArrayList<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as GroupActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.recycler.layoutManager =
            GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
        adapter = GalleryAdapter(activity)
        binding.recycler.adapter = adapter

        getImage()
    }

    @SuppressLint("SimpleDateFormat")
    fun add() {
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        activity.activityResult(intent) {
            val time = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
            imgRef = FirebaseStorage.getInstance().getReference("GalleryImgs").child(time)
            imgUri = it.data
            imgUri?.let { uri ->
                imgRef.putFile(uri).addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener { uri2 ->
                        val fdb = FirebaseDatabase.getInstance()
                        val dbRef = fdb.getReference("GalleryImgs/${DMApp.group.title}")
                        dbRef.child(time).setValue(uri2.toString()).addOnSuccessListener {
                            getImage()
                        }
                    }
                }
            }
        }
    }

    private fun getImage() {
        firebaseDB = FirebaseDatabase.getInstance()
        galleryRef = firebaseDB.getReference("GalleryImgs/${DMApp.group.title}")
        galleryRef.get().addOnSuccessListener {
            urlList.clear()
            it.children.forEach { ds ->
                urlList.add(ds.getValue(String::class.java)!!)
            }
            adapter.update(urlList)
        }
    }
}