package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerOrderInformationBinding
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse

class SellerOrderInformationActivity : BaseActivity<ActivitySellerOrderInformationBinding>(ActivitySellerOrderInformationBinding::inflate), SellerOrderInformationView{
    var orderIdx : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getOrderIdx()
        SellerOrderInformationService(this).tryGetSellerOrderInformation(orderIdx)
    }

    private fun getOrderIdx(){
        orderIdx = intent.getIntExtra("orderIdx", 0)
    }

    override fun onGetSellerOrderInformationSuccess(response: SellerOrderInformationResponse) {
        println(response)
    }

    override fun onGetSellerOrderInformationFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}