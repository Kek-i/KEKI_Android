package com.codepatissier.keki.src.main.consumer.search.searchresult

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ActivityConsumerSearchBinding
import com.codepatissier.keki.util.recycler.search.*
import android.view.MotionEvent

import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResultResponse
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedService
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductData


class ConsumerSearchActivity : BaseActivity<ActivityConsumerSearchBinding>(ActivityConsumerSearchBinding::inflate),
    SearchResultView {
    private lateinit var searchListAdapter : SearchListAdapter
    private var sortType : String = "최신순"
    private var keyword : String = ""
    private var keyTag : String = ""
    private var size : Int = 12
    private var cursorIdx: Long = 0
    private var cursorPopularNum: Int = 0
    private var cursorPrice: Int = 0
    private var hasNext = false
    private var lastItemVisible = false
    var positionStart = 0
    var itemSize = 0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchTag = intent.getStringExtra("searchTag")
        Log.d("서치", "$searchTag")
        if (searchTag == null){
            setListenerToEditText()
        }
        else{
            setTagResult()
        }
        clickDeleteSearch()
        setCategory()
        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener(){
            finish()
        }
    }

    override fun onGetSearchResultsSuccess(response: SearchResultResponse) {
        searchListRecycler(response)
        cursorIdx = response.result.cursorIdx
        cursorPopularNum = response.result.cursorPopulaNum
        cursorPrice = response.result.cursorPrice
        hasNext = response.result.hasNext
    }
    override fun onGetSearchResultsFailure(message: String) {
        showCustomToast("오류 : $message")    }


    //검색 결과 목록 보여주기
    @SuppressLint("NotifyDataSetChanged")
    private fun searchListRecycler(response: SearchResultResponse) {
        val myLayoutManager = GridLayoutManager(this, 3)
        binding.rvSearchGrid.layoutManager = myLayoutManager
        searchListAdapter = SearchListAdapter(response.result, this@ConsumerSearchActivity)
        binding.rvSearchGrid.adapter = searchListAdapter
        searchListAdapter.notifyDataSetChanged()

        // 개별 아이템 클릭 시 이벤트
        searchListAdapter.setItemClickListener(object: SearchListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                SearchResultService(this@ConsumerSearchActivity).tryPostHistory(postIdx = response.result.feeds[position].postIdx)
                val intent = Intent(this@ConsumerSearchActivity, SearchResultFeedActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("sortType", sortType)
                intent.putExtra("keyword", keyword)
                intent.putExtra("keytag", keyTag)
                startActivity(intent)
               }
        })


        //EmptyView 설정
        if (response.result.feeds.isEmpty()) {
            binding.rvSearchGrid.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        }
        else {
            binding.rvSearchGrid.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
        }

        // 스크롤이 바닥에 닿았을 때
        binding.rvSearchGrid.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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
                    positionStart = response.result.numOfRows
                    callLaterSearches()
                }
            }
        })
    }

    // 스크롤이 바닥에 닿았을 때 api 호출 성공했을 시
    override fun onGetNextResultSuccess(response: SearchResultResponse) {

        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext
        itemSize = response.result.numOfRows
        searchListAdapter.notifyItemRangeChanged(positionStart, itemSize)

    }

    override fun onGetNextResultFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostConsumerStoreFeedDetailLikeSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostConsumerStoreFeedDetailLikeFailure(message: String) {
        TODO("Not yet implemented")
    }


    //검색창 x 누르면 삭제
    @SuppressLint("ClickableViewAccessibility")
    private fun clickDeleteSearch(){
        binding.etSearch.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP)
                if (event.rawX  >= binding.etSearch.right - binding.etSearch.compoundDrawables.get(2).bounds.width() - 100 )
                {
                    binding.etSearch.text = null
                    return@OnTouchListener true
                }
            false
        })
    }

    //검색창 엔터키 이벤트
    private fun setListenerToEditText() {
        //검색어 그대로 가져와서 보여주기
        val searchKey = intent.getStringExtra("search_key")
        binding.etSearch.setText("$searchKey")
        // 엔터치면 키보드 내리기, 검색창에 있는 단어 검색하기
        binding.etSearch.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                val imm = this@ConsumerSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                //엑티비티 내에서 검색할 경우
                keyword = "${binding.etSearch.text}"
                keyTag = ""
                SearchResultService(this).tryGetSearchResults(keyword = "${binding.etSearch.text}",sortType = sortType)
            }
            false
        }
        //처음 검색하기
        keyword = "$searchKey"
        SearchResultService(this).tryGetSearchResults(keyword = "$searchKey", sortType = sortType)
    }


    //홈 화면에서 태그 받아오기
    private fun setTagResult() {
        //검색어 그대로 가져와서 보여주기
        val searchTag = intent.getStringExtra("searchTag")
        binding.etSearch.setText("$searchTag")

        binding.etSearch.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                val imm = this@ConsumerSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                //엑티비티 내에서 검색할 경우
                keyword = "${binding.etSearch.text}"
                keyTag = ""
                SearchResultService(this).tryGetSearchResults(keyword = "${binding.etSearch.text}", sortType = sortType)
            }
            false
        }
        keyTag = "$searchTag"
        SearchResultService(this).tryGetTagResults(tag = "$searchTag", sortType)
    }


    //최신순,인기순,가격순 스피너 설정, 정렬
    private fun setCategory() {
        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(this@ConsumerSearchActivity, R.array.spinner_array, R.layout.search_custom_spinner)
        spinnerAdapter.setDropDownViewResource(R.layout.search_custom_spinner_list)
        binding.spinnerSearch.adapter = spinnerAdapter
        binding.spinnerSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View,position: Int,id: Long) {
                if (binding.spinnerSearch.getItemAtPosition(position) != sortType){
                    sortType = "${binding.spinnerSearch.getItemAtPosition(position)}"
                    keyword = binding.etSearch.text.toString()
                    if (keyTag == ""){
                        SearchResultService(this@ConsumerSearchActivity).tryGetSearchResults(keyword = keyword,sortType = sortType)
                    }
                    else{
                        SearchResultService(this@ConsumerSearchActivity).tryGetTagResults(tag = keyTag, sortType = sortType)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }



    private fun callLaterSearches(){
        if(keyTag == ""){
            if(sortType == "최신순"){
                SearchResultService(this).tryGetSearchResultsLatest(keyword = keyword, sortType = sortType, cursorIdx= cursorIdx, size= size)
            }
            if(sortType == "인기순"){
                SearchResultService(this).tryGetSearchResultsPopular(keyword = keyword, sortType = sortType, cursorIdx= cursorIdx, cursorPopularNum = cursorPopularNum,size= size)
            }
            if(sortType == "가격낮은순"){
                SearchResultService(this).tryGetSearchResultsPrice(keyword = keyword, sortType = sortType, cursorIdx= cursorIdx, cursorPrice = cursorPrice, size= size)
            }
        }
        else{
            if(sortType == "최신순"){
                SearchResultService(this).tryGetTagResultsLatest(tag = keyTag, sortType = sortType, cursorIdx= cursorIdx, size= size)
            }
            if(sortType == "인기순"){
                SearchResultService(this).tryGetTagResultsPopular(tag = keyTag, sortType = sortType, cursorIdx= cursorIdx, cursorPopularNum = cursorPopularNum,size= size)
            }
            if(sortType == "가격낮은순"){
                SearchResultService(this).tryGetTagResultsPrice(tag = keyTag, sortType = sortType, cursorIdx= cursorIdx, cursorPrice = cursorPrice, size= size)
            }
        }
    }




}