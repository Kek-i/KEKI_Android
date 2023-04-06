package com.codepatissier.keki.src.main.consumer.store.order.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderListBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.util.recycler.order.ConsumerOrderListAdapter
import com.codepatissier.keki.util.recycler.order.ConsumerOrderListData

class ConsumerOrderListActivity : BaseActivity<ActivityConsumerOrderListBinding>(ActivityConsumerOrderListBinding::inflate) {

    lateinit var consumerOrderListAdapter: ConsumerOrderListAdapter
    private val consumerOrderListDatas = mutableListOf<ConsumerOrderListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        changeOrderNumberTitleColor()
        orderListRecyclerView()
        navigateToStoreMain()
    }

    private fun orderListRecyclerView(){
        consumerOrderListAdapter = ConsumerOrderListAdapter(this)
        binding.recyclerOrderList.adapter = consumerOrderListAdapter

        for(i in 0 until 10){
            consumerOrderListDatas.apply {
                add(ConsumerOrderListData(storeImage = "", storeName = "가게 이름", priceProduct = "가격 | 제품이름", orderDate = "2023/12/31"))
            }
        }

        consumerOrderListAdapter.consumerOrderListDatas = consumerOrderListDatas
        consumerOrderListAdapter.notifyDataSetChanged()
    }

    private fun changeOrderNumberTitleColor(){
        // 해당 항목에 맞추어 글씨 색상 변경
        binding.tvOrderListAllHistory.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))
        binding.tvOrderListNumberAllHistory.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.darkish_pink))
    }

    private fun navigateToStoreMain(){
        // 주문 내역 -> 메인 스토어
        binding.ivOrderListLeftChevron.setOnClickListener {
            var intent = Intent(this, ConsumerStoreMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}