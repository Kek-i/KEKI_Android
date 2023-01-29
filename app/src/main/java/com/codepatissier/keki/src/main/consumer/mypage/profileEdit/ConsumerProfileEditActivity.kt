package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerProfileEditBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ConsumerProfileEditActivity :BaseActivity<ActivityConsumerProfileEditBinding>(ActivityConsumerProfileEditBinding::inflate){
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    var fbStorage : FirebaseStorage ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        backClicked()
        profileClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun profileClicked(){
        binding.ivProfile.setOnClickListener{
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.bg_oval_off_white
                val imageView = binding.ivProfile

                Glide.with(this)
                    .load(ProfileUri)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .circleCrop()
                    .into(imageView)

                if(ProfileUri != null){
                    profileUpload(ProfileUri!!)
                }
            }
        }
    }

    private fun profileUpload(uri:Uri){
           binding.tvEdit.setOnClickListener{
               var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
               var profileImgName = "PROFILE_IMAGE_"+timeStamp+"_.png"
               var storageRef = fbStorage?.reference?.child("profiles/$profileImgName")

               storageRef?.putFile(uri)?.addOnProgressListener {
//                        showLoadingDialog(this)
                    }
                   ?.addOnSuccessListener {
//                       dismissLoadingDialog()
                       Toast.makeText(this, "프로필이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                   }
                   ?.addOnFailureListener{
//                       dismissLoadingDialog()
                       Toast.makeText(this, "프로필이 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                   }
           }
       }



}