package com.codepatissier.keki.util.viewpager.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.codepatissier.keki.src.main.auth.IntroActivity


class AccessRightDialog(context: Context): Dialog(context) {
    private lateinit var  binding : com.codepatissier.keki.databinding.DialogAccessRightBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = com.codepatissier.keki.databinding.DialogAccessRightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)

        checkBtn()
    }

    override fun onStop() {
        super.dismiss()
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }


    private fun checkBtn(){
        binding.btnCheck1.setOnClickListener{
            this.dismiss()
            //확인 버튼 눌렀을 때 종료 flag
            val intro = Intent(context, IntroActivity::class.java)
            intro.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intro)
        }
    }
}
