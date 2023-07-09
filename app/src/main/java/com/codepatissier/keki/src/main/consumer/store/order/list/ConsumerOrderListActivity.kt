package com.codepatissier.keki.src.main.consumer.store.order.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.inflate
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderListBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.consumer.store.order.list.model.ConsumerOrderListResponse
import com.codepatissier.keki.util.recycler.order.ConsumerOrderListAdapter
import com.codepatissier.keki.util.recycler.order.ConsumerOrderListData

class ConsumerOrderListActivity : BaseActivity<ActivityConsumerOrderListBinding>(ActivityConsumerOrderListBinding::inflate)
    , ConsumerOrderListView{

    lateinit var consumerOrderListAdapter: ConsumerOrderListAdapter
    private val consumerOrderListDatas = mutableListOf<ConsumerOrderListData>()
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
        ConsumerOrderListService(this).tryGetConsumerOrderList(orderStr[orderNum])
    }

    private fun initOrderList(){
        orderTextList = mutableListOf(binding.tvOrderListWaiting, binding.tvOrderListMaking, binding.tvOrderListPickUpWaiting, binding.tvOrderListAllHistory)
        orderNumberList = mutableListOf(binding.tvOrderListWaitingNumber, binding.tvOrderListMakingNumber, binding.tvOrderListPickUpWaitingNumber, binding.tvOrderListNumberAllHistory)

        // 전체 내역
        orderNum = 0
        changeOrderNumberTitleColor()
    }

    private fun orderListRecyclerView(response: ConsumerOrderListResponse){
        binding.tvOrderListWaitingNumber.text = response.result.orderWaiting.toString()
        binding.tvOrderListMakingNumber.text = response.result.progressing.toString()
        binding.tvOrderListPickUpWaitingNumber.text = response.result.pickupWaiting.toString()
        binding.tvOrderListNumberAllHistory.text = response.result.allOrderHistory.toString()

        consumerOrderListAdapter = ConsumerOrderListAdapter(this)
        binding.recyclerOrderList.adapter = consumerOrderListAdapter

        for(i in response.result.orderHistory.indices){
            consumerOrderListDatas.apply {
                add(ConsumerOrderListData(orderIdx = response.result.orderHistory[i].orderIdx,
                    userName = response.result.orderHistory[i].userName,
                    userProfileImg = response.result.orderHistory[i].userProfileImg,
                    totalPrice = response.result.orderHistory[i].totalPrice,
                    dessertName = response.result.orderHistory[i].dessertName,
                    pickUpDate = response.result.orderHistory[i].pickupDate))
            }
        }

        consumerOrderListAdapter.consumerOrderListDatas = consumerOrderListDatas
        consumerOrderListAdapter.notifyDataSetChanged()
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
        // 주문 내역 -> 메인 스토어
        binding.ivOrderListLeftChevron.setOnClickListener {
            var intent = Intent(this, ConsumerStoreMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onGetConsumerOrderListSuccess(response: ConsumerOrderListResponse) {
        dismissLoadingDialog()
        orderListRecyclerView(response)
    }

    override fun onGetConsumerOrderListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}