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
//        binding.tvOrderDate.setText(response.result.date)
        binding.tvProductName.setText(response.result.dessertName)
        var optionDescription : String = ""
        var optionPrice: Int = 0
        for(option in response.result.optionOrders){
            optionDescription = optionDescription + "\n" +option.optionDescription
            optionPrice +=  Integer.parseInt(option.optionPrice)
        }
        var totalPrice = response.result.totalPrice + optionPrice
        binding.tvDesignAddContent.setText(optionDescription)
        binding.tvProductPriceContent.setText(response.result.totalPrice.toString() +'원')
        binding.tvExtraPriceContent.setText(optionPrice.toString() + '원')
        binding.tvTotalContent.setText(totalPrice.toString() + '원')
        binding.tvPickupDateContent.setText(response.result.pickupDate.toString())
        binding.tvRequestContent.setText(response.result.request)
    }

    override fun onGetSellerOrderInformationFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}