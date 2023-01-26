package com.codepatissier.keki.src.main.consumer.store.storefeed

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerStoreDetailFeedBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedData

class ConsumerStoreDetailFeedActivity : BaseActivity<ActivityConsumerStoreDetailFeedBinding>(ActivityConsumerStoreDetailFeedBinding::inflate)
    , ConsumerStoreFeedDetailView {

    lateinit var storeFeedAdapter : StoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToStoreMain()
        checkScrollEvent()
        showLoadingDialog(this)
        ConsumerStoreFeedDetailService(this).tryGetConsumerStoreFeedDetailRetrofitInterface("친구", null,null)
    }

    override fun onGetConsumerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse) {
        dismissLoadingDialog()
        storeFeedRecyclerView(response)
    }

    override fun onGetConsumerStoreFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun storeFeedRecyclerView(response: ConsumerStoreDetailFeedResponse){
        storeFeedAdapter = StoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = storeFeedAdapter

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

        storeFeedAdapter.storeFeedDatas = storeFeedDatas
        storeFeedAdapter.notifyDataSetChanged()
    }

    private fun checkScrollEvent(){
        binding.recyclerStoreFeed.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!binding.recyclerStoreFeed.canScrollVertically(1)){
                    Log.d("vertical_scroll", "success")
                }
            }
        })
    }

    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

}


