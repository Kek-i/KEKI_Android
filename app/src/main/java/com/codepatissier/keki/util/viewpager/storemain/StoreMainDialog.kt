package com.codepatissier.keki.util.viewpager.storemain


import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.databinding.DialogStoreMainBinding
import com.codepatissier.keki.src.main.consumer.store.info.ConsumerStoreInfoService
import com.codepatissier.keki.src.main.consumer.store.info.ConsumerStoreInfoView
import com.codepatissier.keki.src.main.consumer.store.info.model.ConsumerStoreInfoResponse

class StoreMainDialog(context: Context):Dialog(context), ConsumerStoreInfoView {
    private lateinit var  binding : DialogStoreMainBinding
    var storeIdx : Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogStoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        ConsumerStoreInfoService(this).tryGetStoreInfo(storeIdx)
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    @JvmName("setStoreIdx1")
    public fun setStoreIdx(storeIdx : Long){
        this.storeIdx = storeIdx
    }


    override fun onGetStoreInfoSuccess(response: ConsumerStoreInfoResponse) {
        binding.tvRepresentativeNameData.text = response.result.businessName
        binding.tvStoreNameData.text = response.result.businessName
        binding.tvStoreAddressData.text = response.result.businessAddress
        binding.tvStoreNumData.text = response.result.businessNumber
    }

    override fun onGetStoreInfoFailure(message: String) {
        Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
    }
}