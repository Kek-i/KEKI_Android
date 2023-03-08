package com.codepatissier.keki.src.main.seller.store

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerStoreFeedBinding
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.SellerStoreFeedAddActivity
import com.codepatissier.keki.util.viewpager.storemain.StoreMainStoreData
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainStoreAdapter

class SellerStoreFeedFragment(storeIdx : Long) : BaseFragment<FragmentSellerStoreFeedBinding>
    (FragmentSellerStoreFeedBinding::bind, R.layout.fragment_seller_store_feed){

    lateinit var sellerStoreMainStoreAdapter: SellerStoreMainStoreAdapter
    var cursorIdx : Long = 0
    val storeMainStoreDatas = mutableListOf<StoreMainStoreData>()
    val storeIdx = storeIdx
    var hasNext = false
    val size = 21

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productFeedRecyclerView()
        floatAddClicked()
    }

    private fun productFeedRecyclerView(){
        sellerStoreMainStoreAdapter = SellerStoreMainStoreAdapter(requireActivity())
        binding.recyclerSellerFeed.adapter = sellerStoreMainStoreAdapter

//        for(i in 1..30){
//            storeMainStoreDatas.apply {
//                add(StoreMainStoreData(postImgUrl = "케키", storeIdx = storeIdx, postIdx = 1))
//            }
//        }

        sellerStoreMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

    }

    private fun floatAddClicked(){
        binding.floatBtn.setOnClickListener{
            val intent = Intent(context, SellerStoreFeedAddActivity::class.java)
            startActivity(intent)
        }
    }
}