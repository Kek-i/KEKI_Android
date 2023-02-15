package com.codepatissier.keki.src.main.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityIntroBinding
import com.codepatissier.keki.src.main.auth.profilesetting.CustomerProfileSettingActivity
import com.codepatissier.keki.src.main.auth.profilesetting.SellerProfileSettingActivity

class IntroActivity: BaseActivity<ActivityIntroBinding>(ActivityIntroBinding::inflate) {
    private var isCustomer = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectRole()
        clickCheck()
    }


    private fun selectRole(){
        binding.ibCustomerLogo.setOnClickListener{
            binding.ibCustomerLogo.borderColor = resources.getColor(R.color.muted_pink)
            binding.ibSellerLogo.borderColor = resources.getColor(R.color.white)
            isCustomer = true
            Log.d("iscustomer", "{$isCustomer} 로그-구매자")
        }
        binding.ibSellerLogo.setOnClickListener{
            binding.ibSellerLogo.borderColor = resources.getColor(R.color.muted_pink)
            binding.ibCustomerLogo.borderColor = resources.getColor(R.color.white)
            isCustomer = false
            Log.d("iscustomer", "{$isCustomer} 로그-판매자")
        }
    }

    private fun clickCheck(){
        binding.btnCheck.setOnClickListener{
            if(isCustomer) {
                startActivity(Intent(this, CustomerProfileSettingActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, SellerProfileSettingActivity::class.java))
                finish()
            }
        }
    }

}