package com.umc.keki.src.main.consumer.store

import android.os.Bundle
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerStoreFeedBinding
import com.umc.keki.util.recycler.storefeed.StoreFeedAdapter
import com.umc.keki.util.recycler.storefeed.StoreFeedData

class ConsumerStoreFeedActivity : BaseActivity<ActivityConsumerStoreFeedBinding>(ActivityConsumerStoreFeedBinding::inflate) {

    lateinit var storeFeedAdapter : StoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeFeedRecyclerView()
        navigateToStoreMain()
    }

    private fun storeFeedRecyclerView(){
        storeFeedAdapter = StoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = storeFeedAdapter

        storeFeedDatas.apply { add(StoreFeedData(nickname = "닉네임")) }
        storeFeedDatas.apply { add(StoreFeedData(nickname = "닉네임2")) }

        storeFeedAdapter.storeFeedDatas = storeFeedDatas
        storeFeedAdapter.notifyDataSetChanged()
    }

    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }
}

