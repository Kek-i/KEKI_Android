package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerOrderCancelBinding
import com.codepatissier.keki.databinding.ActivitySellerOrderInformationBinding
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.OrderImgs
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.SellerOrderStatusEditService
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.SellerOrderStatusEditView
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditBody
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditResponse
import com.codepatissier.keki.util.recycler.order.OrderImgListAdapter

class SellerCancelOrderInformationActivity : BaseActivity<ActivitySellerOrderCancelBinding>(ActivitySellerOrderCancelBinding::inflate), SellerOrderInformationView{
    var orderIdx : Int = 0
    lateinit var orderImgListAdapter: OrderImgListAdapter
    private val orderImgListDatas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getOrderIdx()
        showLoadingDialog(this)
        SellerOrderInformationService(this).tryGetSellerOrderInformation(orderIdx)
    }

    private fun getOrderIdx(){
        orderIdx = intent.getIntExtra("orderIdx", 0)
    }

    // 주문 정보 데이터 받아오기 성공 시
    override fun onGetSellerOrderInformationSuccess(response: SellerOrderInformationResponse) {
        dismissLoadingDialog()

        binding.tvOrderDate.setText(response.result.orderDate)
        binding.tvProductName.setText(response.result.dessertName)
        var optionPrice: Int = 0
        for(option in response.result.optionOrders){
            optionPrice +=  Integer.parseInt(option.optionPrice)
        }
        var totalPrice = response.result.totalPrice + optionPrice
        binding.tvOrderPrice.setText(totalPrice.toString() + '원')
        binding.tvPickupDateContent.setText(response.result.pickupDate.toString())
        binding.tvRequestContent.setText(response.result.request)
        binding.tvConsumerNameContent.setText(response.result.userInfo.userName)
        binding.tvConsumerContactContent.setText(response.result.userInfo.phone)

        orderImgRecyclerView(response.result.orderImgs)
    }

    // 주문 정보 데이터 받아오기 실패 시
    override fun onGetSellerOrderInformationFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 주문 정보 이미지 리싸이클러뷰
    private fun orderImgRecyclerView(imgList:ArrayList<OrderImgs>){
        orderImgListAdapter = OrderImgListAdapter(this)
        binding.rvRequestImg.adapter = orderImgListAdapter

        for (i in imgList.indices){
            orderImgListDatas.apply {
                add(imgList[i].orderImgUrl)
            }
        }

        orderImgListAdapter.orderImgListDatas = orderImgListDatas
        orderImgListAdapter.notifyDataSetChanged()
    }
}