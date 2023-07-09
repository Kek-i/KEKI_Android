package com.codepatissier.keki.util.viewpager.storemain.consumer


import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.databinding.DialogOrderInformationBinding
import com.codepatissier.keki.databinding.DialogStoreMainBinding
import com.codepatissier.keki.src.main.consumer.store.info.ConsumerStoreInfoService
import com.codepatissier.keki.src.main.consumer.store.info.ConsumerStoreInfoView
import com.codepatissier.keki.src.main.consumer.store.info.model.ConsumerStoreInfoResponse

class ConsumerOrderInformationDialog(context: Context):Dialog(context) {
    private lateinit var  binding : DialogOrderInformationBinding
    var storeIdx : Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogOrderInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        binding.ivDelete.setOnClickListener{
            dismiss()
        }
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    override fun dismiss() {
        if(this.isShowing) super.dismiss()
    }
}