package com.codepatissier.keki.src.main.consumer.home.onefeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOneFeedDetailBinding

class ConsumerOneFeedDetailActivity : BaseActivity<ActivityConsumerOneFeedDetailBinding>(ActivityConsumerOneFeedDetailBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("postIdx", intent.getIntExtra("postIdx", 0).toString())
    }
}