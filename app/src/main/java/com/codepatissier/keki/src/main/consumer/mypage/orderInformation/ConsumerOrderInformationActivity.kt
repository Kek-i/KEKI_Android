package com.codepatissier.keki.src.main.consumer.mypage.orderInformation

import android.os.Bundle
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderInformationBinding
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.ConsumerOrderInformationResponse
import com.codepatissier.keki.src.main.consumer.mypage.orderInformation.model.OrderImgs
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainService
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainView
import com.codepatissier.keki.src.main.consumer.store.model.ConsumerStoreMainResponse
import com.codepatissier.keki.util.recycler.order.OrderImgListAdapter
import com.google.firebase.storage.FirebaseStorage

class ConsumerOrderInformationActivity : BaseActivity<ActivityConsumerOrderInformationBinding>(ActivityConsumerOrderInformationBinding::inflate), ConsumerOrderInformationView,
    ConsumerStoreMainView {
    var orderIdx : Int = 0
    val ORDERSTATUS:Array<String> = arrayOf("주문대기", "제작중", "픽업대기", "픽업완료")
    lateinit var orderImgListAdapter: OrderImgListAdapter
    private val orderImgListDatas = mutableListOf<String>()
    var fbStorage : FirebaseStorage?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()

        getOrderIdx()
        showLoadingDialog(this)
        ConsumerOrderInformationService(this).tryGetConsumerOrderInformation(orderIdx)
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

    // 주문 정보 데이터 받아오기 성공 시
    override fun onGetConsumerOrderInformationSuccess(response: ConsumerOrderInformationResponse) {
        dismissLoadingDialog()

        orderStatus(response.result.orderStatus)

        showLoadingDialog(this)
        ConsumerStoreMainService(this).tryGetStoreMain(response.result.storeInfo.storeIdx)

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
        binding.tvStoreName.setText(response.result.storeInfo.storeName)
        binding.tvStoreAccountContent.setText(response.result.storeInfo.accountNumber + " " + response.result.storeInfo.accountHolder)

        orderImgRecyclerView(response.result.orderImgs)
    }

    // 주문 정보 데이터 받아오기 실패 시
    override fun onGetConsumerOrderInformationFailure(message: String) {
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

    // 판매자 프로필 가져오기 성공 시
    override fun onGetStoreMainSuccess(response: ConsumerStoreMainResponse) {
        dismissLoadingDialog()

        val defaultImg = R.drawable.ic_seller
        val imageView = binding.ivStoreProfile
        if(!response.result.storeImgUrl.isNullOrEmpty()) {
            if (response.result.storeImgUrl!!.startsWith("http")){
                Glide.with(this)
                    .load(response.result.storeImgUrl)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .circleCrop()
                    .into(imageView)
                dismissLoadingDialog()
            } else {
                // 프로필 이미지 띄우기
                var storageRef = fbStorage?.reference?.child(response.result.storeImgUrl)
                storageRef?.downloadUrl?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Glide.with(this)
                            .load(it.result)
                            .placeholder(defaultImg)
                            .error(defaultImg)
                            .fallback(defaultImg)
                            .circleCrop()
                            .into(imageView)
                        dismissLoadingDialog()
                    }else{
                        dismissLoadingDialog()
                    }
                }
            }
        }else{
            dismissLoadingDialog()
        }
    }

    // 판매자 프로필 가져오기 실패 시
    override fun onGetStoreMainFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}