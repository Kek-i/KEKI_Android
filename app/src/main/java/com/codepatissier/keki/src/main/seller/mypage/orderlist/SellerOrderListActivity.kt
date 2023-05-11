package com.codepatissier.keki.src.main.seller.mypage.orderlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerOrderListBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.seller.mypage.orderlist.model.SellerOrderListResponse
import com.codepatissier.keki.util.recycler.order.SellerOrderListAdapter
import com.codepatissier.keki.util.recycler.order.SellerOrderListData

class SellerOrderListActivity : BaseActivity<ActivitySellerOrderListBinding>(ActivitySellerOrderListBinding::inflate)
    , SellerOrderListView{

    lateinit var sellerOrderListAdapter: SellerOrderListAdapter
    private val sellerOrderListDatas = mutableListOf<SellerOrderListData>()
    private var orderStr = mutableListOf("주문대기", "주문중", "픽업대기", null)
    private var orderNum = 0
    private var orderTextList = mutableListOf<TextView>()
    private var orderNumberList = mutableListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToStoreMain()
        initOrderList()
        getOrderList()
        setOrderNum()
    }

    private fun getOrderList(){
        showLoadingDialog(this)
        SellerOrderListService(this).tryGetSellerOrderList(orderStr[orderNum])
    }

    private fun initOrderList(){
        orderTextList = mutableListOf(binding.tvOrderListWaiting, binding.tvOrderListMaking, binding.tvOrderListPickUpWaiting, binding.tvOrderListAllHistory)
        orderNumberList = mutableListOf(binding.tvOrderListWaitingNumber, binding.tvOrderListMakingNumber, binding.tvOrderListPickUpWaitingNumber, binding.tvOrderListNumberAllHistory)

        // 전체 내역
        orderNum = 0
        changeOrderNumberTitleColor()
    }

    private fun orderListRecyclerView(response: SellerOrderListResponse){
        binding.tvOrderListWaitingNumber.text = response.result.orderWaiting.toString()
        binding.tvOrderListMakingNumber.text = response.result.progressing.toString()
        binding.tvOrderListPickUpWaitingNumber.text = response.result.pickupWaiting.toString()
        binding.tvOrderListNumberAllHistory.text = response.result.allOrderHistory.toString()

        sellerOrderListAdapter = SellerOrderListAdapter(this)
        binding.recyclerOrderList.adapter = sellerOrderListAdapter

        for(i in response.result.orderHistory.indices){
            sellerOrderListDatas.apply {
                add(SellerOrderListData(
                    orderIdx = response.result.orderHistory[i].orderIdx,
                    userName = response.result.orderHistory[i].userName,
                    userProfileImg = response.result.orderHistory[i].userProfileImg,
                    totalPrice = response.result.orderHistory[i].totalPrice,
                    dessertName = response.result.orderHistory[i].dessertName,
                    pickUpDate = response.result.orderHistory[i].pickupDate))
            }
        }

        sellerOrderListAdapter.sellerOrderListDatas = sellerOrderListDatas
        sellerOrderListAdapter.notifyDataSetChanged()
    }

    private fun setOrderNum(){
        binding.llOrderListWaiting.setOnClickListener {
            orderNum = 0

            // 주문 대기
            changeOrderNumberTitleColor()
            getOrderList()
        }

        binding.llOrderListMaking.setOnClickListener {
            orderNum = 1

            // 주문 중
            changeOrderNumberTitleColor()
            getOrderList()
        }

        binding.llOrderListPickUpWaiting.setOnClickListener {
            orderNum = 2

            // 픽업 대기
            changeOrderNumberTitleColor()
            getOrderList()
        }

        binding.llOrderListAllHistory.setOnClickListener {
            orderNum = 3

            // 전체 내역
            changeOrderNumberTitleColor()
            getOrderList()
        }
    }

    private fun changeOrderNumberTitleColor(){
        // 해당 항목에 맞추어 글씨 색상 변경
        orderTextList[orderNum].setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))
        orderNumberList[orderNum].setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))

        for(i in orderTextList.indices){
            if(orderNum != i){
                orderTextList[i].setTextColor(ContextCompat.getColor(applicationContext!!, R.color.brown_grey))
                orderNumberList[i].setTextColor(ContextCompat.getColor(applicationContext!!, R.color.brown_grey))
            }
        }
    }

    private fun navigateToStoreMain(){
        // 주문 내역 -> 마이페이지
        binding.ivOrderListLeftChevron.setOnClickListener {
            finish()
        }
    }

    override fun onGetSellerOrderListSuccess(response: SellerOrderListResponse) {
        dismissLoadingDialog()
        orderListRecyclerView(response)
    }

    override fun onGetSellerOrderListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}