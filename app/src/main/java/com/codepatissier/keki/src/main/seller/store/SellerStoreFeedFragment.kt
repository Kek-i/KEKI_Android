package com.codepatissier.keki.src.main.seller.store

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerStoreFeedBinding
import com.codepatissier.keki.src.main.seller.store.storefeed.SellerStoreFeedAddActivity
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

        showLoadingDialog(requireContext())

        floatAddClicked()
    }

    private fun productFeedRecyclerView(){
        sellerStoreMainStoreAdapter = SellerStoreMainStoreAdapter(requireActivity())
        binding.recyclerSellerFeed.adapter = sellerStoreMainStoreAdapter

       for(i in response.result.feeds.indices){
           storeMainStoreDatas.apply {
               add(StoreMainStoreData(postImgUrl = response.result.feeds[i].postImgUrls[0], storeIdx = storeIdx, postIdx = response.result.feeds[i].postIdx))
           }
       }
        sellerStoreMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

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
                    ConsumerStoreFeedService(this@SellerStoreFeedFragment).tryGetConsumerStoreNextFeed(storeIdx = storeIdx, cursorIdx=cursorIdx,size =size)
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        storeMainStoreDatas.clear()
        ConsumerStoreFeedService(this).tryGetConsumerStoreFeed(storeIdx=storeIdx, size=size)
    }

    override fun onGetStoreFeedSuccess(response: SearchResultResponse) {
        dismissLoadingDialog()
        productFeedRecyclerView(response)
    }

    override fun onGetStoreFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetStoreNextFeedSuccess(response: SearchResultResponse) {
        binding.progress.visibility = View.GONE
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        for(i in response.result.feeds.indices) {
            storeMainStoreDatas.apply {
                add(StoreMainStoreData(postImgUrl = "케키", storeIdx = storeIdx, postIdx = 1))
            }
        }

        sellerStoreMainStoreAdapter.storeMainStoreDatas = storeMainStoreDatas

    }

    private fun floatAddClicked(){
        binding.floatBtn.setOnClickListener{
            val intent = Intent(context, SellerStoreFeedAddActivity::class.java)
            startActivity(intent)
        }
    }
}