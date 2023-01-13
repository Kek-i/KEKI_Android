package com.umc.keki.src.main.consumer.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.umc.keki.R
import com.umc.keki.databinding.ActivityConsumerSearchBinding
import com.umc.keki.util.recycler.search.*


class ConsumerSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsumerSearchBinding
    private lateinit var searchListAdapter : SearchListAdapter
    val searchListData = mutableListOf<SearchListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsumerSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //검색어 그대로 가져와서 보여주기
        val searchKey = intent.getStringExtra("search_key")
        binding.etSearch.setText("$searchKey")

        setCategory()
        searchListRecycler()
    }


    //검색 결과 목록 보여주기
    private fun searchListRecycler() {
        searchListAdapter = SearchListAdapter(this@ConsumerSearchActivity)
        binding.rvSearchGrid.adapter = searchListAdapter

        //그리드뷰 컬럼 수 설정
        val myLayoutManager = GridLayoutManager(this, 3)
        binding.rvSearchGrid.layoutManager = myLayoutManager

        //검색결과 목록 예시 데이터
        searchListData.apply {
            add(SearchListData(img= R.drawable.img_cake, "딸기 컵케이크", 26000))
            add(SearchListData(img= R.drawable.img_cake, "딸기 컵케이크", 27000))
            add(SearchListData(img= R.drawable.img_cake, "딸기 컵케이크", 28000))

            searchListAdapter.searchListData = searchListData
            searchListAdapter.notifyDataSetChanged()
        }

        //EmptyView 설정
        if (searchListData.isEmpty()) {
            binding.rvSearchGrid.setVisibility(View.GONE);
            binding.layoutEmpty.setVisibility(View.VISIBLE);
        }
        else {
            binding.rvSearchGrid.setVisibility(View.VISIBLE);
            binding.layoutEmpty.setVisibility(View.GONE);
        }

    }

    //최신순,인기순,가격순 스피너 설정, 정렬
    private fun setCategory() {

        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(this@ConsumerSearchActivity, R.array.spinner_array, R.layout.search_custom_spinner)
        spinnerAdapter.setDropDownViewResource(com.umc.keki.R.layout.search_custom_spinner_list)


        binding.spinnerSearch.adapter = spinnerAdapter

        binding.spinnerSearch.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }
}