package com.codepatissier.keki.src.main.seller.mypage.orderlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerOrderListBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.util.recycler.order.SellerOrderListAdapter
import com.codepatissier.keki.util.recycler.order.SellerOrderListData

class SellerOrderListActivity : BaseActivity<ActivitySellerOrderListBinding>(ActivitySellerOrderListBinding::inflate) {

    lateinit var sellerOrderListAdapter: SellerOrderListAdapter
    private val sellerOrderListDatas = mutableListOf<SellerOrderListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderListRecyclerView()
        changeOrderNumberTitleColor()
        navigateToStoreMain()
    }

    private fun orderListRecyclerView(){
        sellerOrderListAdapter = SellerOrderListAdapter(this)
        binding.recyclerOrderList.adapter = sellerOrderListAdapter

        for(i in 0 until 10){
            sellerOrderListDatas.apply {
                add(SellerOrderListData(storeImage = "", storeName = "가게 이름", priceProduct = "가격 | 제품이름", orderDate = "2023/12/31"))
            }
        }

        sellerOrderListAdapter.sellerOrderListDatas = sellerOrderListDatas
        sellerOrderListAdapter.notifyDataSetChanged()
    }

    private fun changeOrderNumberTitleColor(){
        // 해당 항목에 맞추어 글씨 색상 변경
        binding.tvOrderListAllHistory.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))
        binding.tvOrderListNumberAllHistory.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))
    }

    private fun navigateToStoreMain(){
        // 주문 내역 -> 마이페이지
        binding.ivOrderListLeftChevron.setOnClickListener {
            finish()
        }
    }
}