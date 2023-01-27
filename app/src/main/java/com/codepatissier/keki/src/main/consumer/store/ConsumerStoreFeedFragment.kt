package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
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
    val storeMainStoreDatas = mutableListOf<StoreMainStoreData>()
    val storeIdx = storeIdx

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerStoreFeedService(this).tryGetConsumerStoreFeed(storeIdx, 30)
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
        binding.recyclerSellerFeed.adapter = storeMainStoreAdapter

        for(i in response.result.feeds.indices) {
            storeMainStoreDatas.apply {
                add(StoreMainStoreData(img = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx))
            }
        }
        storeMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

    }


}