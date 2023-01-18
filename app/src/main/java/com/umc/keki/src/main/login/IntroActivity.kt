package com.umc.keki.src.main.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import com.umc.keki.databinding.ActivityIntroBinding

class IntroActivity: AppCompatActivity() {
    private lateinit var binding: com.umc.keki.databinding.ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectRole()
        clickCheck()

    }

    private fun selectRole(){
        binding.ibCustomerLogo.setOnClickListener{
            binding.ibCustomerSelect.visibility = View.VISIBLE
            binding.ibSellerSelect.visibility = View.INVISIBLE
        }
        binding.ibSellerLogo.setOnClickListener{
            binding.ibSellerSelect.visibility = View.VISIBLE
            binding.ibCustomerSelect.visibility = View.INVISIBLE
        }
    }

    private fun clickCheck(){
        binding.btnCheck.setOnClickListener{
            startActivity(Intent(this,EditProfileActivity::class.java))
            finish()
        }
    }

}