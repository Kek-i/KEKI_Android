package com.umc.keki.src.main.consumer.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerSearchBinding
import com.umc.keki.util.recycler.search.*

class ConsumerSearchFragment : BaseFragment<FragmentConsumerSearchBinding>(FragmentConsumerSearchBinding::bind, R.layout.fragment_consumer_search) {

    lateinit var searchRecentAdapter : SearchRecentAdapter
    val searchRecentData = mutableListOf<SearchRecentData>()

    lateinit var searchPopularAdapter : SearchPopularAdapter
    val searchPopularData = mutableListOf<SearchPopularData>()

    lateinit var searchRecentCakeImgAdapter: SearchCakeImgAdapter
    val searchCakeImgData = mutableListOf<SearchCakeImgData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRecentRecycler()
        searchPopularRecycler()
        searchRecentSeenRecycler()
        setListenerToEditText()

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
    }

    private fun searchPopularRecycler() {
        searchPopularAdapter = SearchPopularAdapter(this)
        binding.rvPopularSearch.adapter = searchPopularAdapter

        //인기 검색어 예시 데이터
        searchPopularData.apply {
            add(SearchPopularData(popular = "#친구"))
            add(SearchPopularData(popular = "#가족"))
            add(SearchPopularData(popular = "#기념일"))
            add(SearchPopularData(popular = "#1주년"))
            add(SearchPopularData(popular = "#생일파티"))

            searchPopularAdapter.searchPopularData = searchPopularData
            searchPopularAdapter.notifyDataSetChanged()

        }
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