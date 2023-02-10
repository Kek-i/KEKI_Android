package com.codepatissier.keki.src.main.seller.store.productfeed

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProductAddAndEditBinding

class SellerProductAdd:BaseActivity<ActivitySellerProductAddAndEditBinding>(ActivitySellerProductAddAndEditBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }
}