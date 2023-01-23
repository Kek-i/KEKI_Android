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
import com.codepatissier.keki.util.recycler.search.*

class ConsumerSearchFragment : BaseFragment<FragmentConsumerSearchBinding>(FragmentConsumerSearchBinding::bind, R.layout.fragment_consumer_search) , SearchView{

    private lateinit var searchRecentAdapter : SearchRecentAdapter
    private lateinit var searchPopularAdapter : SearchPopularAdapter
    private lateinit var searchRecentPostAdapter: SearchRecentPostAdapter
    private lateinit var searchData : com.codepatissier.keki.src.main.consumer.search.model.Result


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteSearchHistory()
        setListenerToEditText()

        showLoadingDialog(requireContext())
        SearchService(this).tryGetMainSearches()
    }

    override fun onGetMainSearchesSuccess(response: MainSearchesResponse) {
        dismissLoadingDialog()
        searchMainRecycler(response)
    }

    override fun onGetMainSearchesFailure(message: String) {
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


    @SuppressLint("NotifyDataSetChanged")
    private fun searchMainRecycler(response: MainSearchesResponse) {
        searchRecentAdapter = SearchRecentAdapter(response.result, this)
        searchPopularAdapter = SearchPopularAdapter(response.result, this)
        searchRecentPostAdapter = SearchRecentPostAdapter(response.result, this)
        binding.rvRecentSearch.adapter = searchRecentAdapter
        binding.rvPopularSearch.adapter = searchPopularAdapter
        binding.rvRecentSeen.adapter = searchRecentPostAdapter
        searchRecentAdapter.notifyDataSetChanged()
        searchPopularAdapter.notifyDataSetChanged()
        searchRecentPostAdapter.notifyDataSetChanged()
    }

}