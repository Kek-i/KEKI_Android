package com.codepatissier.keki.src.main.consumer.search.searchresult

import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerStoreDetailFeedBinding
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import com.codepatissier.keki.util.recycler.search.SearchResultFeedAdapter


class SearchResultFeedActivity : BaseActivity<ActivityConsumerStoreDetailFeedBinding>(ActivityConsumerStoreDetailFeedBinding::inflate)
    , SearchResultView {

    private lateinit var searchResultFeedAdapter : SearchResultFeedAdapter

    private var position : Int = 1
    private var sortType : String = "인기순"
    private var keyword : String = ""
    private var keytag : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = intent.getIntExtra("position", 0)!!
        sortType = intent.getStringExtra("sortType")!!
        keyword = intent.getStringExtra("keyword")!!
        keytag = intent.getStringExtra("keytag")!!
        init()

        navigateToStoreMain()
        showLoadingDialog(this)

    }
    private fun init(){
        if (keyword != ""){
            SearchResultService(this).tryGetSearchResults(keyword = keyword,sortType = sortType)
        }
        else{
            SearchResultService(this).tryGetTagResults(tag = keytag, sortType = sortType)
        }
    }


    override fun onGetSearchResultsSuccess(response: SearchResultResponse) {
        dismissLoadingDialog()
        searchResultFeedRecycler(response)
    }

    override fun onGetSearchResultsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun searchResultFeedRecycler(response: SearchResultResponse){

        searchResultFeedAdapter = SearchResultFeedAdapter(response.result,this)
        binding.recyclerStoreFeed.adapter = searchResultFeedAdapter
        searchResultFeedAdapter.notifyDataSetChanged()

        binding.recyclerStoreFeed.scrollToPosition(position)
    }

    private fun navigateToStoreMain(){
        binding.ivStoreFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

}


