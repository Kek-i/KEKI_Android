package com.codepatissier.keki.src.main.seller.store.storefeed

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedDetailBinding
import com.codepatissier.keki.util.recycler.storefeed.SellerStoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedData


class SellerStoreFeedDetailActivity : BaseActivity<ActivitySellerStoreFeedDetailBinding>(com.codepatissier.keki.databinding.ActivitySellerStoreFeedDetailBinding::inflate) {

    lateinit var sellerStoreFeedAdapter: SellerStoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()
    var feedTag : String ?= null //
    var storeIdx: Long? = null
    var feedSize = 21
    var cursorIdx : Int? = null
    var hasNext : Boolean? = null
    var positionStart = 0
    var position : Int? = null
    var itemSize = 0
    var firstLoading : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        firstLoading = true
    }

    private fun initRecyclerView(){
        storeIdx = intent.getLongExtra("storeIdx", -1)
        position = intent.getIntExtra("position", 0)
        feedSize = position!! + 5
        sellerStoreFeedAdapter = SellerStoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = sellerStoreFeedAdapter
    }
}