package com.codepatissier.keki.src.main.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityIntroBinding
import com.codepatissier.keki.src.main.login.profilesetting.EditProfileActivity

class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {
    private var isCustomer = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectRole()
        clickCheck()

    }


    private fun selectRole(){
        binding.ibCustomerLogo.setOnClickListener{
            binding.ibCustomerSelect.visibility = View.VISIBLE
            binding.ibSellerSelect.visibility = View.INVISIBLE
            isCustomer = true
            Log.d("iscustomer", "{$isCustomer} 로그-구매자")
        }
        binding.ibSellerLogo.setOnClickListener{
            binding.ibSellerSelect.visibility = View.VISIBLE
            binding.ibCustomerSelect.visibility = View.INVISIBLE
            isCustomer = false
            Log.d("iscustomer", "{$isCustomer} 로그-판매자")
        }
    }

    private fun clickCheck(){
        binding.btnCheck.setOnClickListener{
            startActivity(Intent(this, EditProfileActivity::class.java))
            finish()
        }
    }

}