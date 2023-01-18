package com.umc.keki.src.main.consumer.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerHomeBinding
import com.umc.keki.src.main.consumer.search.ConsumerSearchActivity
import com.umc.keki.util.recycler.home.HomeStoreAdapter
import com.umc.keki.util.recycler.home.HomeStoreData

class ConsumerHomeFragment : BaseFragment<FragmentConsumerHomeBinding>
    (FragmentConsumerHomeBinding::bind, R.layout.fragment_consumer_home) {

    lateinit var homeStoreFirstAdapter : HomeStoreAdapter
    val homeStoreFirstDatas = mutableListOf<HomeStoreData>()

    lateinit var homeStoreSecondAdapter : HomeStoreAdapter
    val homeStoreSecondDatas = mutableListOf<HomeStoreData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeStoreFirstRecyclerView()
        homeStoreSecondRecyclerView()
        navigateToSearchFirstTag()
        navigateToSearchSecondTag()
    }

    private fun homeStoreFirstRecyclerView(){
        homeStoreFirstAdapter = HomeStoreAdapter(requireActivity())
        binding.recyclerFirstHome.adapter = homeStoreFirstAdapter

        // 임시
        for(i in 1 until 10){
            homeStoreFirstDatas.apply { add(HomeStoreData(name = "가게 이름입니다")) }
        }

        homeStoreFirstAdapter.homeStoreDatas = homeStoreFirstDatas
        homeStoreFirstAdapter.notifyDataSetChanged()
    }

    private fun homeStoreSecondRecyclerView(){
        homeStoreSecondAdapter = HomeStoreAdapter(requireActivity())
        binding.recyclerSecondHome.adapter = homeStoreSecondAdapter

        // 임시
       for(i in 1 until 10){
            homeStoreSecondDatas.apply { add(HomeStoreData(name = "가게 이름")) }
        }

        homeStoreSecondAdapter.homeStoreDatas = homeStoreSecondDatas
        homeStoreSecondAdapter.notifyDataSetChanged()
    }

    // 첫번째 태그 서치 화면으로 이동
    private fun navigateToSearchFirstTag(){
        binding.ivFirstHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag 넘기기
            intent.putExtra("searchTag", binding.tvFirstHomeTag.text)
            startActivity(intent)
        }
    }

    // 두번째 태그 서치 화면으로 이동
    private fun navigateToSearchSecondTag(){
        binding.ivSecondHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag 넘기기
            intent.putExtra("tag", binding.tvSecondHomeTagSecond.text)
            startActivity(intent)
        }
    }

}