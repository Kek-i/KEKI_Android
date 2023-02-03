package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivityConsumerStoreDetailFeedBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedData

class ConsumerStoreDetailFeedActivity : BaseActivity<ActivityConsumerStoreDetailFeedBinding>(ActivityConsumerStoreDetailFeedBinding::inflate)
    , ConsumerStoreFeedDetailView {

    lateinit var storeFeedAdapter : StoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()
    var feedTag : String ?= null
    var feedSize = 3
    var cursorIdx : Int? = null
    var hasNext : Boolean? = null
    var positionStart = 0
    var itemSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initRecyclerView()
        navigateToStoreMain()
        showLoadingDialog(this)
        ConsumerStoreFeedDetailService(this).tryGetConsumerStoreFeedDetailRetrofitInterface(feedTag!!, cursorIdx, feedSize)
        checkScrollEvent()
    }

    private fun initRecyclerView(){
        feedTag = intent.getStringExtra("tag")!!
        storeFeedAdapter = StoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = storeFeedAdapter
    }

    override fun onGetConsumerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse) {
        dismissLoadingDialog()
        storeFeedRecyclerView(response)
    }

    override fun onGetConsumerStoreFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 피드 좋아요
    override fun onPostConsumerStoreFeedDetailLikeSuccess(response: BaseResponse) {
    }

    // 피드 좋아요 취소
    override fun onPostConsumerStoreFeedDetailLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun storeFeedRecyclerView(response: ConsumerStoreDetailFeedResponse){
        storeFeedDatas.clear()

        val result = response.result.feeds

        for(i in response.result.feeds.indices){
            storeFeedDatas.apply { add(StoreFeedData(postIdx = response.result.feeds[i].postIdx,
            dessertName = result[i].dessertName,
            description = result[i].description,
            postImgUrls = result[i].postImgUrls,
            tags = result[i].tags,
            brandName = result[i].brandName,
            storeProfileImg = result[i].storeProfileImg,
            like = result[i].like,
            cursorIdx = response.result.cursorIdx,
            hasNext = response.result.hasNext)) }
        }

        itemSize = storeFeedDatas.size - positionStart

        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        storeFeedAdapter.setList(storeFeedDatas, hasNext!!)
        storeFeedAdapter.notifyItemRangeInserted(positionStart, response.result.feeds.size)

        //LinearLayoutManager(this).scrollToPositionWithOffset(positionStart,0)
    }

    private fun checkScrollEvent(){
        binding.recyclerStoreFeed.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1
                
                if(!binding.recyclerStoreFeed.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount){
                    if(hasNext!!){
                        positionStart = storeFeedDatas.size
                        ConsumerStoreFeedDetailService(this@ConsumerStoreDetailFeedActivity)
                            .tryGetConsumerStoreFeedDetailRetrofitInterface(feedTag!!, cursorIdx, feedSize)

                    }

                }
            }
        })
    }

    fun postLike(postIdx: Int){
        ConsumerStoreFeedDetailService(this).tryPostConsumerStoreFeedDetailLikeRetrofitInterface(postIdx)
    }

    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

}


