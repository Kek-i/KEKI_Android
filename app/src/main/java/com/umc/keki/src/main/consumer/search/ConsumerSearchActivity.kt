package com.umc.keki.src.main.consumer.search

import android.annotation.SuppressLint
import android.app.admin.DelegatedAdminReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.keki.R
import com.umc.keki.databinding.ActivityConsumerSearchBinding
import com.umc.keki.util.recycler.search.*
import android.view.MotionEvent

import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


class ConsumerSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsumerSearchBinding
    private lateinit var searchListAdapter : SearchListAdapter
    val searchListData = mutableListOf<SearchListData>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumerSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNavigateFromHome()
        deleteSearch()
        setCategory()
        searchListRecycler()
        setListenerToEditText()
    }

    private fun checkNavigateFromHome(){
        if(intent.getStringExtra("searchTag") != null){
            var searchTag = intent.getStringExtra("searchTag")
            binding.etSearch.setText(searchTag)
            Log.d("searchTag", binding.etSearch.text.toString())
        }
    }


    //검색창 x 누르면 삭제
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
        // 엔터치면 키보드 내리기
        binding.etSearch.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
            {
                val imm = this@ConsumerSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
            }
            false
        }
    }

    //검색 결과 목록 보여주기
    private fun searchListRecycler() {
        searchListAdapter = SearchListAdapter(this@ConsumerSearchActivity)
        binding.rvSearchGrid.adapter = searchListAdapter
        // 개별 아이템 클릭 시 이벤트
        searchListAdapter.setItemClickListener(object: SearchListAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                Toast.makeText(binding.root.context,"${searchListData[position].cakeName} 선택됨",Toast.LENGTH_SHORT).show()
            }
        })

        //그리드뷰 컬럼 수 설정
        val myLayoutManager = GridLayoutManager(this, 3)
        binding.rvSearchGrid.layoutManager = myLayoutManager

        //검색결과 목록 예시 데이터
        searchListData.apply {
            add(SearchListData(img= R.drawable.img_cake, "딸기 컵케이크", 26000))
            add(SearchListData(img= R.drawable.img_cake, "바나나 컵케이크", 27000))
            add(SearchListData(img= R.drawable.img_cake, "수박 컵케이크", 28000))

            searchListAdapter.searchListData = searchListData
            searchListAdapter.notifyDataSetChanged()
        }

        //EmptyView 설정
        if (searchListData.isEmpty()) {
            binding.rvSearchGrid.visibility = View.GONE
            binding.layoutEmpty.visibility = View.VISIBLE
        }
        else {
            binding.rvSearchGrid.visibility = View.VISIBLE
            binding.layoutEmpty.visibility = View.GONE
        }

    }

    //최신순,인기순,가격순 스피너 설정, 정렬
    private fun setCategory() {

        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(this@ConsumerSearchActivity, R.array.spinner_array, R.layout.search_custom_spinner)
        spinnerAdapter.setDropDownViewResource(com.umc.keki.R.layout.search_custom_spinner_list)


        binding.spinnerSearch.adapter = spinnerAdapter

        binding.spinnerSearch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}