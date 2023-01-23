package com.codepatissier.keki.src.main.consumer.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerSearchBinding
import com.codepatissier.keki.src.main.consumer.search.model.PopularSearchesResponse
import com.codepatissier.keki.util.recycler.search.*

class ConsumerSearchFragment : BaseFragment<FragmentConsumerSearchBinding>(FragmentConsumerSearchBinding::bind, R.layout.fragment_consumer_search) , SearchView{

    lateinit var searchRecentAdapter : SearchRecentAdapter
    val searchRecentData = mutableListOf<SearchRecentData>()

    lateinit var searchPopularAdapter : SearchPopularAdapter
    val searchPopularData = mutableListOf<SearchPopularData>()

    lateinit var searchRecentCakeImgAdapter: SearchCakeImgAdapter
    val searchCakeImgData = mutableListOf<SearchCakeImgData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        deleteSearchHistory()
        searchRecentRecycler()
        searchRecentSeenRecycler()
        setListenerToEditText()

        showLoadingDialog(requireContext())
        SearchService(this).tryGetPopularSearches()
    }

    override fun onGetPopularSearchesSuccess(response: PopularSearchesResponse) {
        dismissLoadingDialog()
        searchPopularRecycler(response)
    }

    override fun onGetPopularSearchesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    //검색어 전체 지우기 클릭 이벤트
    private fun deleteSearchHistory(){
        binding.tvRecentSearchDelete.setOnClickListener {
            binding.llEmptyHistory.visibility = View.GONE
        }
    }

    //검색창에서 엔터키로 동작 넘기기
    private fun setListenerToEditText() {
        binding.etSearch.setOnKeyListener { view, keyCode, event ->
            // Enter Key Action
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER)
            {
                val searchKey = binding.etSearch.text
                val intent = Intent(context, ConsumerSearchActivity::class.java)
                intent.putExtra("search_key","$searchKey")
                startActivity(intent)
            }
            false
        }
    }

    private fun searchRecentRecycler() {
        searchRecentAdapter = SearchRecentAdapter(this)
        binding.rvRecentSearch.adapter = searchRecentAdapter

        //최근 검색어 예시 데이터
        searchRecentData.apply {
            add(SearchRecentData(bearer = "생일 케이크"))
            add(SearchRecentData(bearer = "합격축하"))
            add(SearchRecentData(bearer = "크리스마스"))
            add(SearchRecentData(bearer = "당근 케이크 맛집"))
            add(SearchRecentData(bearer = "레터링 케이크"))

            searchRecentAdapter.recentSearchData = searchRecentData
            searchRecentAdapter.notifyDataSetChanged()
        }

        if (searchRecentData.isEmpty()) {
            binding.llEmptyHistory.visibility = View.GONE
        }
        else {
            binding.llEmptyHistory.visibility = View.VISIBLE
        }
    }

    private fun searchPopularRecycler(response: PopularSearchesResponse) {
        searchPopularAdapter = SearchPopularAdapter(this)
        binding.rvPopularSearch.adapter = searchPopularAdapter

        for(i in response.result.indices){
            searchPopularData.apply {
                add(SearchPopularData(popular = "# " + response.result[i].searchWord))
            }
        }

        searchPopularAdapter.searchPopularData = searchPopularData
        searchPopularAdapter.notifyDataSetChanged()
    }

    private fun searchRecentSeenRecycler() {
        searchRecentCakeImgAdapter = SearchCakeImgAdapter(this)
        binding.rvRecentSeen.adapter = searchRecentCakeImgAdapter

        //최근 본 가게 예시 데이터
        searchCakeImgData.apply {
            add(SearchCakeImgData(img= R.drawable.img_cake))
            add(SearchCakeImgData(img= R.drawable.img_cake))
            add(SearchCakeImgData(img= R.drawable.img_cake))
            add(SearchCakeImgData(img= R.drawable.img_cake))
            add(SearchCakeImgData(img= R.drawable.img_cake))


            searchRecentCakeImgAdapter.searchCakeImgData = searchCakeImgData
            searchRecentCakeImgAdapter.notifyDataSetChanged()

        }
    }


}