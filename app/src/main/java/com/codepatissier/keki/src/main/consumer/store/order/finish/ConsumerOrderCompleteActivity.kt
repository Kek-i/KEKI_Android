package com.codepatissier.keki.src.main.consumer.store.order.finish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderCompleteBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.consumer.store.order.list.ConsumerOrderListActivity

class ConsumerOrderCompleteActivity : BaseActivity<ActivityConsumerOrderCompleteBinding>(ActivityConsumerOrderCompleteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confirmOrder()
    }

    private fun confirmOrder(){
        // 주문 확인 화면 -> 주문 내역 화면
        binding.tvOrderCompleteOk.setOnClickListener {
            var intent = Intent(this, ConsumerOrderListActivity::class.java)
            startActivity(intent)
        }
    }
}