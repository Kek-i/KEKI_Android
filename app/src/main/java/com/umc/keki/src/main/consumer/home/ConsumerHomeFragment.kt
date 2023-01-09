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

    lateinit var homeStoreAapter : HomeStoreAdapter
    val homeStoreDatas = mutableListOf<HomeStoreData>()

    lateinit var homeStoreSecondAdapter : HomeStoreAdapter
    val homeStoreSecondDatas = mutableListOf<HomeStoreData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeStoreFirstRecyclerView()
        homeStoreSecondRecyclerView()
    }

    private fun homeStoreFirstRecyclerView(){
        homeStoreAapter = HomeStoreAdapter(requireActivity())
        binding.recyclerFirstHome.adapter = homeStoreAapter

        // 임시
        for(i in 1 until 10){
            homeStoreDatas.apply { add(HomeStoreData(name = "가게 이름입니다")) }
        }

        homeStoreAapter.homeStoreDatas = homeStoreDatas
        homeStoreAapter.notifyDataSetChanged()
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