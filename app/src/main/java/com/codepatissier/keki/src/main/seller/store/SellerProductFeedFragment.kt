package com.codepatissier.keki.src.main.seller.store

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerProductFeedBinding
import com.codepatissier.keki.src.main.seller.store.product.SellerProductAddActivity
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductData
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainProductAdapter

class SellerProductFeedFragment(storeIdx : Long) : BaseFragment<FragmentSellerProductFeedBinding>
    (FragmentSellerProductFeedBinding::bind, R.layout.fragment_seller_product_feed){

    lateinit var sellerStoreMainProductAdapter: SellerStoreMainProductAdapter
    var cursorIdx : Long = 0
    val storeMainProductDatas = mutableListOf<StoreMainProductData>()
    val storeIdx = storeIdx
    var hasNext = false
    val size = 21


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productFeedRecyclerView()
        floatAddClicked()
    }


    private fun productFeedRecyclerView(){
        sellerStoreMainProductAdapter = SellerStoreMainProductAdapter(requireActivity())
        binding.recyclerProductFeed.adapter = sellerStoreMainProductAdapter

       for(i in 1..30){
           storeMainProductDatas.apply {
               add(StoreMainProductData(dessertIdx =1, dessertImgUrl = "케키", storeIdx = storeIdx))
           }
       }

        sellerStoreMainProductAdapter.storeMainProductDatas = storeMainProductDatas

    }

    private fun floatAddClicked(){
        binding.floatBtn.setOnClickListener{
            val intent = Intent(context, SellerProductAddActivity::class.java)
            startActivity(intent)
        }
    }

}