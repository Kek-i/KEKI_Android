package com.codepatissier.keki.src.main.seller.mypage.orderInformation

import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerOrderInformationBinding
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.OrderImgs
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.model.SellerOrderInformationResponse
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.SellerOrderStatusEditService
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.SellerOrderStatusEditView
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditBody
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.orderStatus.model.SellerOrderStatusEditResponse
import com.codepatissier.keki.util.recycler.order.OrderImgListAdapter

class SellerOrderInformationActivity : BaseActivity<ActivitySellerOrderInformationBinding>(ActivitySellerOrderInformationBinding::inflate), SellerOrderInformationView, SellerOrderStatusEditView{
    var orderIdx : Int = 0
    val ORDERSTATUS:Array<String> = arrayOf("주문대기", "제작중", "픽업대기", "픽업완료")
    lateinit var status : String
    lateinit var orderImgListAdapter: OrderImgListAdapter
    private val orderImgListDatas = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getOrderIdx()
        showLoadingDialog(this)
        SellerOrderInformationService(this).tryGetSellerOrderInformation(orderIdx)
        clickOrderStatus()
    }

    private fun getOrderIdx(){
        orderIdx = intent.getIntExtra("orderIdx", 0)
    }

    // 주문 상태 변화에 따른 색변경
    private fun orderStatus(status:String){
        when (status) {
            ORDERSTATUS[0] -> {
                binding.ivWatingOrder.setColorFilter(R.color.tab_select)
                binding.ivMaking.setColorFilter(null)
                binding.ivWatingPickup.setColorFilter(null)
                binding.ivCompletePickup.setColorFilter(null)
            }
            ORDERSTATUS[1] -> {
                binding.ivWatingOrder.setColorFilter(null)
                binding.ivMaking.setColorFilter(R.color.tab_select)
                binding.ivWatingPickup.setColorFilter(null)
                binding.ivCompletePickup.setColorFilter(null)
            }
            ORDERSTATUS[2] -> {
                binding.ivWatingOrder.setColorFilter(null)
                binding.ivMaking.setColorFilter(null)
                binding.ivWatingPickup.setColorFilter(R.color.tab_select)
                binding.ivCompletePickup.setColorFilter(null)
            }
            ORDERSTATUS[3] -> {
                binding.ivWatingOrder.setColorFilter(null)
                binding.ivMaking.setColorFilter(null)
                binding.ivWatingPickup.setColorFilter(null)
                binding.ivCompletePickup.setColorFilter(R.color.tab_select)
            }
        }
    }

    // 주문 상태 변경 시 서버로 전송
    private fun changeOrderStatus(status : String){
        var sellerOrderStatusEditBody = SellerOrderStatusEditBody(orderIdx, status)
        showLoadingDialog(this)
        SellerOrderStatusEditService(this).tryPatchOrderStatus(sellerOrderStatusEditBody)
    }

    // 주문상태 클릭시
    private fun clickOrderStatus(){
        binding.ivWatingOrder.setOnClickListener{
            status = ORDERSTATUS[0]
            changeOrderStatus(status)
        }

        binding.ivMaking.setOnClickListener{
            status = ORDERSTATUS[1]
            changeOrderStatus(status)
        }

        binding.ivWatingPickup.setOnClickListener{
            status = ORDERSTATUS[2]
            changeOrderStatus(status)
        }

        binding.ivCompletePickup.setOnClickListener{
            status = ORDERSTATUS[3]
            changeOrderStatus(status)
        }
    }

    // 주문 정보 데이터 받아오기 성공 시
    override fun onGetSellerOrderInformationSuccess(response: SellerOrderInformationResponse) {
        dismissLoadingDialog()

        status = response.result.orderStatus
        orderStatus(status)

        binding.tvOrderDate.setText(response.result.orderDate)
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

    // 주문 상태 변경 성공 시
    override fun onPatchOrderStatusSuccess(response: SellerOrderStatusEditResponse) {
        dismissLoadingDialog()
        orderStatus(status)
    }

    // 주문 상태 변경 실패 시
    override fun onPatchOrderStatusFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}