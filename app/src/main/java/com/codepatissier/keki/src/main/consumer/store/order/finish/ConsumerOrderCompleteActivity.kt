package com.codepatissier.keki.src.main.consumer.store.order.finish

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderCompleteBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity

class ConsumerOrderCompleteActivity : BaseActivity<ActivityConsumerOrderCompleteBinding>(ActivityConsumerOrderCompleteBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        confirmOrder()
    }

    private fun confirmOrder(){
        binding.tvOrderCompleteOk.setOnClickListener {
            var intent = Intent(this, ConsumerStoreMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}