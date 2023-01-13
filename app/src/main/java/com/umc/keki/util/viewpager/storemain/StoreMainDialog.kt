package com.umc.keki.util.viewpager.storemain


import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.umc.keki.databinding.DialogStoreMainBinding

class StoreMainDialog(context: Context):Dialog(context) {
    private lateinit var  binding : DialogStoreMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogStoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }
}