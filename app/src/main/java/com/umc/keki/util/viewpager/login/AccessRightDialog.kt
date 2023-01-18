package com.umc.keki.util.viewpager.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.umc.keki.databinding.DialogAccessRightBinding
import com.umc.keki.src.main.login.IntroActivity


class AccessRightDialog(context: Context): Dialog(context) {
    private lateinit var  binding : com.umc.keki.databinding.DialogAccessRightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = com.umc.keki.databinding.DialogAccessRightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)

        checkBtn()
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    private fun checkBtn(){
        binding.btnCheck1.setOnClickListener{
            context.startActivity(Intent(context, IntroActivity::class.java))
        }
    }
}