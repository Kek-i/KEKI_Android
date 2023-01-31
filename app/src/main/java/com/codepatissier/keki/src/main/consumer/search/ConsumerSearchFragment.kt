package com.codepatissier.keki.src.main.consumer.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerSearchBinding
import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse
import com.codepatissier.keki.src.main.consumer.search.model.PatchSearchResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.ConsumerSearchActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.util.recycler.search.*

class ConsumerSearchFragment : BaseFragment<FragmentConsumerSearchBinding>(FragmentConsumerSearchBinding::bind, R.layout.fragment_consumer_search) , SearchMainView{

    private lateinit var searchRecentAdapter : SearchRecentAdapter
    private lateinit var searchPopularAdapter : SearchPopularAdapter
    private lateinit var searchRecentPostAdapter: SearchRecentPostAdapter
    private val AccessToken = sSharedPreferences.getString(Authorization, null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenerToEditText()
        showLoadingDialog(requireContext())
        clickDeleteSearchHistory()
        callMainSearches()
    }

    override fun onResume() {
        super.onResume()
        callMainSearches()
    }

    private fun callMainSearches(){
        SearchMainService(this).tryGetPopularSearches()
        if(AccessToken != null){
            SearchMainService(this).tryGetMainSearches()
        }
        else{
            binding.llEmptyHistory.visibility = View.GONE
            binding.llEmptySeen.visibility = View.GONE
        }
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

    //개별 검색어 데이터 뷰 연결하기
    @SuppressLint("NotifyDataSetChanged")
    private fun searchUserRecycler(response: MainSearchesResponse) {
        //최근 검색어 연결
        searchRecentAdapter = SearchRecentAdapter(response.result, this)
        binding.rvRecentSearch.adapter = searchRecentAdapter
        searchRecentAdapter.notifyDataSetChanged()

        if (response.result.recentSearches.isEmpty() ){
            binding.llEmptyHistory.visibility = View.GONE
        }else{
            binding.llEmptyHistory.visibility = View.VISIBLE
        }
        //최근 검색어 태그 클릭 시 이벤트
        searchRecentAdapter.setItemClickListener(object: SearchRecentAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, ConsumerSearchActivity::class.java)
                intent.putExtra("search_key", response.result.recentSearches[position].searchWord)
                startActivity(intent)
            }
        })
        //최근 본 케이크 연결
        searchRecentPostAdapter = SearchRecentPostAdapter(response.result, this)
        binding.rvRecentSeen.adapter = searchRecentPostAdapter
        searchRecentPostAdapter.notifyDataSetChanged()

        //최근 본 케이크 클릭 시 이벤트 -> 상품 전체 조회로 넘어가기기(수정 필요)
       searchRecentPostAdapter.setItemClickListener(object:SearchRecentPostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, ConsumerStoreDetailFeedActivity::class.java)
                intent.putExtra("postIdx", response.result.recentPostSearches[position].postIdx)
                startActivity(intent)
            }
        })

    }


    //인기 검색어 뷰 연결하기
    @SuppressLint("NotifyDataSetChanged")
    private fun searchPopularRecycler(response: MainSearchesResponse) {

        searchPopularAdapter = SearchPopularAdapter(response.result, this)
        binding.rvPopularSearch.adapter = searchPopularAdapter
        searchPopularAdapter.notifyDataSetChanged()

        //인기 검색어 클릭 시 이벤트
        searchPopularAdapter.setItemClickListener(object: SearchPopularAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, ConsumerSearchActivity::class.java)
                intent.putExtra("search_key", response.result.popularSearches[position].searchWord)
                startActivity(intent)
            }
        })

    }

    override fun onGetMainSearchesSuccess(response: MainSearchesResponse) {
        dismissLoadingDialog()
        searchUserRecycler(response)
    }

    override fun onGetMainSearchesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetPopularSearchesSuccess(response: MainSearchesResponse) {
        dismissLoadingDialog()
        searchPopularRecycler(response)
    }

    override fun onGetPopularSearchesFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchSearchesSuccess(response: PatchSearchResponse) {
        binding.llEmptyHistory.visibility = View.GONE
    }

    override fun onPatchSearchesFailure(message: String) {
        showCustomToast("삭제 오류 : $message")
    }
}