package com.codepatissier.keki.src.main.consumer.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerSearchBinding
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.ConsumerSearchActivity
import com.codepatissier.keki.util.recycler.search.*

class ConsumerSearchFragment : BaseFragment<FragmentConsumerSearchBinding>(FragmentConsumerSearchBinding::bind, R.layout.fragment_consumer_search) , SearchMainView{

    private lateinit var searchRecentAdapter : SearchRecentAdapter
    private lateinit var searchPopularAdapter : SearchPopularAdapter
    private lateinit var searchRecentPostAdapter: SearchRecentPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerToEditText()
        showLoadingDialog(requireContext())
        SearchMainService(this).tryGetMainSearches()
        clickDeleteSearchHistory()
    }

    override fun onGetMainSearchesSuccess(response: MainSearchesResponse) {
        dismissLoadingDialog()
        searchMainRecycler(response)
    }

    override fun onGetMainSearchesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchSearchesSuccess(response: PatchSearchResponse) {
        binding.llEmptyHistory.visibility = View.GONE
    }

    override fun onPatchSearchesFailure(message: String) {
        showCustomToast("삭제 오류 : $message")
    }

    override fun onResume() {
        super.onResume()
        SearchMainService(this).tryGetMainSearches()
    }

    //검색어 전체 지우기 -> 최근 검색어 뷰 없어짐
    private fun clickDeleteSearchHistory(){
        binding.tvRecentSearchDelete.setOnClickListener {
            SearchMainService(this).tryPatchSearchHistories()
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

    //데이터 - 어뎁터 연결
    @SuppressLint("NotifyDataSetChanged")
    private fun searchMainRecycler(response: MainSearchesResponse) {
        searchRecentAdapter = SearchRecentAdapter(response.result, this)
        binding.rvRecentSearch.adapter = searchRecentAdapter
        searchRecentAdapter.notifyDataSetChanged()
        if (response.result.recentSearches.isEmpty()){
            binding.llEmptyHistory.visibility = View.GONE
        }else{
            binding.llEmptyHistory.visibility = View.VISIBLE
        }

        searchPopularAdapter = SearchPopularAdapter(response.result, this)
        binding.rvPopularSearch.adapter = searchPopularAdapter
        searchPopularAdapter.notifyDataSetChanged()

        searchRecentPostAdapter = SearchRecentPostAdapter(response.result, this)
        binding.rvRecentSeen.adapter = searchRecentPostAdapter
        searchRecentPostAdapter.notifyDataSetChanged()
    }

}