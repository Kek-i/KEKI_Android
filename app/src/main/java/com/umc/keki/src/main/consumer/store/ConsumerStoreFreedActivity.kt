package com.umc.keki.src.main.consumer.store

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.umc.keki.R
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerStoreFreedBinding
import com.umc.keki.databinding.ActivityMainBinding.inflate
import com.umc.keki.util.recycler.storefeed.StoreFeedAdapter
import com.umc.keki.util.recycler.storefeed.StoreFeedData

class ConsumerStoreFreedActivity : BaseActivity<ActivityConsumerStoreFreedBinding>(ActivityConsumerStoreFreedBinding::inflate) {

    lateinit var storeFeedAdapter : StoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeFeedRecyclerView()
    }

    private fun storeFeedRecyclerView(){
        storeFeedAdapter = StoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = storeFeedAdapter

        storeFeedDatas.apply { add(StoreFeedData(nickname = "닉네임")) }

        storeFeedAdapter.storeFeedDatas = storeFeedDatas
        storeFeedAdapter.notifyDataSetChanged()
    }

}

