package com.codepatissier.keki.src.main.seller.store.storefeed.storedetail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedDetailBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import com.codepatissier.keki.util.recycler.storefeed.SellerStoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedData


class SellerStoreFeedDetailActivity : BaseActivity<ActivitySellerStoreFeedDetailBinding>(com.codepatissier.keki.databinding.ActivitySellerStoreFeedDetailBinding::inflate)
    , SellerStoreFeedDetailView {

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


        firstLoading = true

        initRecyclerView()
        navigateToStoreMain()
        showLoadingDialog(this)
        SellerStoreFeedDetailService(this).tryGetSellerStoreFeedDetailInterface(storeIdx!!, cursorIdx, feedSize)
        checkScrollEvent()
    }

    private fun initRecyclerView(){
        storeIdx = intent.getLongExtra("storeIdx", -1)
        position = intent.getIntExtra("position", 0)
        feedSize = position!! + 5
        sellerStoreFeedAdapter = SellerStoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = sellerStoreFeedAdapter
    }

    override fun onGetSellerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse) {
        dismissLoadingDialog()
        storeFeedRecyclerView(response)
    }

    override fun onGetSellerStoreFeedDetailFailure(message: String) {
        dismissLoadingDialog()
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
                storeName = result[i].storeName,
                storeProfileImg = result[i].storeProfileImg,
                like = result[i].like,
                cursorIdx = response.result.cursorIdx,
                hasNext = response.result.hasNext)) }
        }

        itemSize = storeFeedDatas.size - positionStart

        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        sellerStoreFeedAdapter.setList(storeFeedDatas, hasNext!!)
        sellerStoreFeedAdapter.notifyItemRangeInserted(positionStart, response.result.feeds.size)

        if(firstLoading!!){
            binding.recyclerStoreFeed.scrollToPosition(position!!)
            firstLoading = false
        }
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
                        SellerStoreFeedDetailService(this@SellerStoreFeedDetailActivity)
                            .tryGetSellerStoreFeedDetailInterface(storeIdx!!, cursorIdx, feedSize)

                    }

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