package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerStoreFeedBinding
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedService
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedView
import com.codepatissier.keki.util.viewpager.storemain.StoreMainStoreAdapter
import com.codepatissier.keki.util.viewpager.storemain.StoreMainStoreData

class ConsumerStoreFeedFragment(storeIdx : Long) : BaseFragment<FragmentConsumerStoreFeedBinding>
    (FragmentConsumerStoreFeedBinding::bind, R.layout.fragment_consumer_store_feed), ConsumerStoreFeedView{

    lateinit var storeMainStoreAdapter : StoreMainStoreAdapter
    var cursorIdx : Long = 0
    val storeMainStoreDatas = mutableListOf<StoreMainStoreData>()
    val storeIdx = storeIdx
    var hasNext = false
    val size = 21


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerStoreFeedService(this).tryGetConsumerStoreFeed(storeIdx, size)
    }

    override fun onGetStoreFeedSuccess(response: SearchResultResponse) {
        dismissLoadingDialog()
        storeFeedRecyclerView(response)
    }

    override fun onGetStoreFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun storeFeedRecyclerView(response: SearchResultResponse){
        storeMainStoreAdapter = StoreMainStoreAdapter(requireActivity())
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext
        binding.recyclerSellerFeed.adapter = storeMainStoreAdapter

        for(i in response.result.feeds.indices) {
            storeMainStoreDatas.apply {
                add(StoreMainStoreData(img = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx, postIdx = response.result.feeds[i].postIdx))
            }
        }
        storeMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

        // 스크롤이 바닥에 닿았을 때
        binding.recyclerSellerFeed.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.recyclerSellerFeed.canScrollVertically(1) && hasNext) {
                    showLoadingDialog(requireContext())
                    ConsumerStoreFeedService(this@ConsumerStoreFeedFragment).tryGetConsumerStoreNextFeed(storeIdx, cursorIdx,size)
                }
            }
        })
    }

    // 스크롤이 바닥에 닿았을 때 api 호출 성공했을 시
    override fun onGetStoreNextFeedSuccess(response: SearchResultResponse) {
        dismissLoadingDialog()
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        for(i in response.result.feeds.indices) {
            storeMainStoreDatas.apply {
                add(StoreMainStoreData(img = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx, postIdx = response.result.feeds[i].postIdx))
            }
        }
        storeMainStoreAdapter.notifyDataSetChanged()

    }

    override fun onGetStoreNextFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }


}