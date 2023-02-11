package com.codepatissier.keki.util.viewpager.storemain.seller


import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.databinding.DialogStoreMainBinding
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageService
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageView
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse

class SellerStoreMainDialog(context: Context):Dialog(context), SellerMyPageView {
    private lateinit var  binding : DialogStoreMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogStoreMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        SellerMyPageService(this).tryGetSellerMyPage()
    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    override fun onGetMyPageSuccess(response: SellerMyPageResponse) {
        binding.tvRepresentativeNameData.text = response.result.businessName
        binding.tvStoreNameData.text = response.result.brandName
        binding.tvStoreAddressData.text = response.result.businessAddress
        binding.tvStoreNumData.text = response.result.businessNumber
    }

    override fun onGetMyPageFailure(message: String) {
        Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
    }
}