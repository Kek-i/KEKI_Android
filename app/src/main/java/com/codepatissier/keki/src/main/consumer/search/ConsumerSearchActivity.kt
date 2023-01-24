package com.codepatissier.keki.src.main.consumer.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ActivityConsumerSearchBinding
import com.codepatissier.keki.util.recycler.search.*
import android.view.MotionEvent

import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.src.main.consumer.search.model.SearchResultResponse


class ConsumerSearchActivity : BaseActivity<ActivityConsumerSearchBinding>(ActivityConsumerSearchBinding::inflate), SearchResultView {
    private lateinit var searchListAdapter : SearchListAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNavigateFromHome()
        deleteSearch()
        setCategory()
        setListenerToEditText()

    }
    override fun onGetSearchResultsSuccess(response: SearchResultResponse) {
        searchListRecycler(response)
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

    }


    //검색창 x 누르면 삭제
    @SuppressLint("ClickableViewAccessibility")
    private fun deleteSearch(){
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
                SearchResultService(this).tryGetSearchResults(keyword = "${binding.etSearch.text}")
            }
            false
        }
        //처음 검색하기
        SearchResultService(this).tryGetSearchResults(keyword = "$searchKey")
    }

    //최신순,인기순,가격순 스피너 설정, 정렬
    private fun setCategory() {
        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(this@ConsumerSearchActivity, R.array.spinner_array, R.layout.search_custom_spinner)
        spinnerAdapter.setDropDownViewResource(com.codepatissier.keki.R.layout.search_custom_spinner_list)

        binding.spinnerSearch.adapter = spinnerAdapter
        binding.spinnerSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,view: View,position: Int,id: Long) {}
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    //홈화면에서 넘어오는 해시태그
    private fun checkNavigateFromHome(){
        if(intent.getStringExtra("searchTag") != null){
            var searchTag = intent.getStringExtra("searchTag")
            binding.etSearch.setText(searchTag)
            Log.d("searchTag", binding.etSearch.text.toString())
        }
    }
}