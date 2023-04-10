package com.codepatissier.keki.src.main.seller.store.productfeed

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedDetailBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedDetailService
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreFeedDetailView
import com.codepatissier.keki.src.main.consumer.store.storefeed.model.ConsumerStoreDetailFeedResponse
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedAdapter
import com.codepatissier.keki.util.recycler.storefeed.StoreFeedData

class SellerProductFeedDetailActivity : BaseActivity<ActivitySellerStoreFeedDetailBinding>(ActivitySellerStoreFeedDetailBinding::inflate),
    ConsumerStoreFeedDetailView{

    lateinit var storeFeedAdapter: StoreFeedAdapter
    val storeFeedDatas = mutableListOf<StoreFeedData>()
    var storeIdx: Long? = null
    var feedSize = 12
    var cursorIdx : Int? = null
    var hasNext : Boolean? = null
    var positionStart = 0
    var position : Int? = null
    var itemSize = 0
    var seller = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        navigateToStoreMain()
        showLoadingDialog(this)
        ConsumerStoreFeedDetailService(this).tryGetConsumerStoreFeedDetailRetrofitInterface(storeIdx!!, cursorIdx, feedSize)
        checkScrollEvent()
    }

    private fun initRecyclerView(){
        seller = true // 판매자임을 알림
        storeIdx = intent.getLongExtra("storeIdx", -1)
        position = intent.getIntExtra("position", 0)
        storeFeedAdapter = StoreFeedAdapter(this)
        binding.recyclerStoreFeed.adapter = storeFeedAdapter
    }

    private fun storeFeedRecyclerView(response: ConsumerStoreDetailFeedResponse){
        storeFeedDatas.clear()

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
                hasNext = response.result.hasNext,
                storeIdx = response.result.feeds[i].storeIdx)) }
        }

        itemSize = storeFeedDatas.size - positionStart

        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        storeFeedAdapter.setList(storeFeedDatas, hasNext!!)
        storeFeedAdapter.notifyItemRangeInserted(positionStart, response.result.feeds.size)

        binding.recyclerStoreFeed.scrollToPosition(position!!)
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
                        ConsumerStoreFeedDetailService(this@SellerProductFeedDetailActivity)
                            .tryGetConsumerStoreFeedDetailRetrofitInterface(storeIdx!!, cursorIdx, feedSize)

                    }

                }
            }
        })
    }


    override fun onGetConsumerStoreFeedDetailSuccess(response: ConsumerStoreDetailFeedResponse) {
        dismissLoadingDialog()
        storeFeedRecyclerView(response)
    }

    override fun onGetConsumerStoreFeedDetailFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostConsumerStoreFeedDetailLikeSuccess(response: BaseResponse) {

    }

    override fun onPostConsumerStoreFeedDetailLikeFailure(message: String) {

    }

    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }
}