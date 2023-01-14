package com.umc.keki.src.main.consumer.home

import android.os.Bundle
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerHomeBinding
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

}