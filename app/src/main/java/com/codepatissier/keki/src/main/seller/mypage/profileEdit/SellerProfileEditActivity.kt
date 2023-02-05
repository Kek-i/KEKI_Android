package com.codepatissier.keki.src.main.seller.mypage.profileEdit

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProfileEditBinding
import com.google.firebase.storage.FirebaseStorage

class SellerProfileEditActivity :BaseActivity<ActivitySellerProfileEditBinding>(ActivitySellerProfileEditBinding::inflate) {

    var fbStorage : FirebaseStorage ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }


}