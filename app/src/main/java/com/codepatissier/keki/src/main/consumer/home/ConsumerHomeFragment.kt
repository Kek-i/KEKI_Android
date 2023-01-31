package com.codepatissier.keki.src.main.consumer.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerHomeBinding
import com.codepatissier.keki.src.main.consumer.home.model.ConsumerHomeResponse
import com.codepatissier.keki.src.main.consumer.search.searchresult.ConsumerSearchActivity
import com.codepatissier.keki.util.recycler.home.HomeStoreAdapter
import com.codepatissier.keki.util.recycler.home.HomeStoreData

class ConsumerHomeFragment : BaseFragment<FragmentConsumerHomeBinding>
    (FragmentConsumerHomeBinding::bind, R.layout.fragment_consumer_home), ConsumerHomeView{

    lateinit var homeStoreFirstAdapter : HomeStoreAdapter
    val homeStoreFirstDatas = mutableListOf<HomeStoreData>()

    lateinit var homeStoreSecondAdapter : HomeStoreAdapter
    val homeStoreSecondDatas = mutableListOf<HomeStoreData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerHomeService(this).tryGetConsumerHome()
        //homeStoreFirstRecyclerView()
        //homeStoreSecondRecyclerView()
        //navigateToSearchFirstTag()
        //navigateToSearchSecondTag()
    }

    override fun onGetConsumerHomeSuccess(response: ConsumerHomeResponse) {
        dismissLoadingDialog()
        binding.tvHomeComment.text = response.result.nickname + "님!\n" + response.result.calendarTitle + "이 " + response.result.calendarDate.toString() + "일 남았어요\n 특별한 하루를 준비해요!"
        //initUser(response.result)
    }

    override fun onGetConsumerHomeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    @SuppressLint("SetTextI18n")
    private fun initUser(response: com.codepatissier.keki.src.main.consumer.home.model.Result){
        binding.tvHomeComment.text = response.nickname + "님!\n" + response.calendarTitle + "이 " + response.calendarDate.toString() + "일 남았어요\n 특별한 하루를 준비해요!"
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