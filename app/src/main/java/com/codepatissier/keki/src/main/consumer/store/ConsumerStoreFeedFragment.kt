package com.codepatissier.keki.src.main.consumer.store

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
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
    var positionStart = 0
    var itemSize = 0
    var lastItemVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerStoreFeedService(this).tryGetConsumerStoreFeed(storeIdx=storeIdx, size=size)
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
                add(StoreMainStoreData(postImgUrl = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx, postIdx = response.result.feeds[i].postIdx))
            }
        }
        storeMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

        // 스크롤이 바닥에 닿았을 때
        binding.recyclerSellerFeed.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 현재 보이는 마지막 아이템의 position
                var lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                // 전제 아이템 갯수
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)
                // 마지막 아이템이면 true로 변경
               if( lastVisibleItemPosition != itemTotalCount) {
                   lastItemVisible = true
               }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // 스크롤이 멈춰있고, 다음 아이템이 있으며 마지막 아이템일 때 api 호출
                if(newState == RecyclerView.SCROLL_STATE_IDLE && hasNext && lastItemVisible){
                    binding.progress.visibility = View.VISIBLE
                    positionStart = storeMainStoreDatas.size
                    ConsumerStoreFeedService(this@ConsumerStoreFeedFragment).tryGetConsumerStoreNextFeed(storeIdx = storeIdx, cursorIdx=cursorIdx,size =size)
                }
            }
        })
    }

    // 스크롤이 바닥에 닿았을 때 api 호출 성공했을 시
    override fun onGetStoreNextFeedSuccess(response: SearchResultResponse) {
        binding.progress.visibility = View.GONE
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        for(i in response.result.feeds.indices) {
            storeMainStoreDatas.apply {
                add(StoreMainStoreData(postImgUrl = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx, postIdx = response.result.feeds[i].postIdx))
            }
        }

        itemSize = storeMainStoreDatas.size - positionStart
        storeMainStoreAdapter.notifyItemRangeChanged(positionStart, itemSize)

    }

    override fun onGetStoreNextFeedFailure(message: String) {
        binding.progress.visibility = View.GONE
        showCustomToast("오류 : $message")
    }


}