package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerProductFeedBinding
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedService
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedView
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedService
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductAdapter
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductData

class ConsumerProductFeedFragment(storeIdx : Long) : BaseFragment<FragmentConsumerProductFeedBinding>
    (FragmentConsumerProductFeedBinding::bind, R.layout.fragment_consumer_product_feed), ConsumerStoreProductFeedView{

    lateinit var storeMainProductAdapter: StoreMainProductAdapter
    var cursorIdx : Long = 0
    val storeMainProductDatas = mutableListOf<StoreMainProductData>()
    val storeIdx = storeIdx
    var hasNext = false
    val size = 21

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerStoreProductFeedService(this).tryGetProductFeed(storeIdx, size)
    }

    override fun onGetProductFeedSuccess(response: ConsumerStoreProductFeedResponse) {
        dismissLoadingDialog()
        productFeedRecyclerView(response)
    }

    override fun onGetProductFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun productFeedRecyclerView(response:ConsumerStoreProductFeedResponse){
        storeMainProductAdapter = StoreMainProductAdapter(requireActivity())
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext
        binding.recyclerProductFeed.adapter = storeMainProductAdapter

       for(i in response.result.desserts.indices){
           storeMainProductDatas.apply {
               add(StoreMainProductData(dessertIdx = response.result.desserts[i].dessertIdx, dessertImgUrl = response.result.desserts[i].dessertImgUrl, storeIdx = storeIdx))
           }
           Log.e("merong",response.result.desserts[i].dessertImgUrl)
       }
        storeMainProductAdapter.storeMainProductDatas = storeMainProductDatas

        // 스크롤이 바닥에 닿았을 때
        binding.recyclerProductFeed.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.recyclerProductFeed.canScrollVertically(1) && hasNext) {
                    showLoadingDialog(requireContext())
                    ConsumerStoreProductFeedService(this@ConsumerProductFeedFragment).tryGetProductNextFeed(storeIdx, cursorIdx,size)
                }
            }
        })
    }

    // 스크롤이 바닥에 닿았을 때 api 호출 성공했을 시
    override fun onGetProductNextFeedSuccess(response: ConsumerStoreProductFeedResponse) {
        dismissLoadingDialog()
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        for(i in response.result.desserts.indices){
            storeMainProductDatas.apply {
                add(StoreMainProductData(dessertIdx = response.result.desserts[i].dessertIdx, dessertImgUrl = response.result.desserts[i].dessertImgUrl, storeIdx = storeIdx))
            }
        }
        storeMainProductAdapter.notifyDataSetChanged()

    }

    override fun onGetProductNextFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}