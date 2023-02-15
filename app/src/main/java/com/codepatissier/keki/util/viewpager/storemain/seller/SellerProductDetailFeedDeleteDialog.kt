package com.codepatissier.keki.util.viewpager.storemain.seller

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.codepatissier.keki.databinding.DialogSellerProductDetailFeedDeleteBinding


class SellerProductDetailFeedDeleteDialog(context: Context): Dialog(context) {
    private lateinit var  binding : DialogSellerProductDetailFeedDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSellerProductDetailFeedDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)

        checkBtn()
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    override fun onStop() {
        dismiss()
    }

    private fun checkBtn(){
        binding.btnDelete.setOnClickListener{
            this.dismiss()
        }
    }
}
