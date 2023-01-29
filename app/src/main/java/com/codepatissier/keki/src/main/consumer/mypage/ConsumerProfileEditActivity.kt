package com.codepatissier.keki.src.main.consumer.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerProfileEditBinding

class ConsumerProfileEditActivity :BaseActivity<ActivityConsumerProfileEditBinding>(ActivityConsumerProfileEditBinding::inflate){
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backClicked()
        profileClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun profileClicked(){
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
            }
        }
        binding.ivProfile.setOnClickListener{
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }
    }
}