package com.codepatissier.keki.src.main.consumer.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerHomeBinding
import com.codepatissier.keki.src.main.consumer.home.model.ConsumerHomeResponse
import com.codepatissier.keki.src.main.consumer.home.model.HomeTagRes
import com.codepatissier.keki.src.main.consumer.search.searchresult.ConsumerSearchActivity
import com.codepatissier.keki.util.recycler.home.HomeStoreAdapter
import com.codepatissier.keki.util.recycler.home.HomeStoreData
import com.google.firebase.auth.UserInfo

class ConsumerHomeFragment : BaseFragment<FragmentConsumerHomeBinding>
    (FragmentConsumerHomeBinding::bind, R.layout.fragment_consumer_home), ConsumerHomeView{
    private val userRole = ApplicationClass.sSharedPreferences.getString(ApplicationClass.UserRole, "비회원")

    lateinit var homeStoreFirstAdapter : HomeStoreAdapter
    val homeStoreFirstDatas = mutableListOf<HomeStoreData>()

    lateinit var homeStoreSecondAdapter : HomeStoreAdapter
    val homeStoreSecondDatas = mutableListOf<HomeStoreData>()

    lateinit var homeStoreThirdAdapter : HomeStoreAdapter
    val homeStoreThirdDatas = mutableListOf<HomeStoreData>()

    private var num = 0

    private var constraintLayouts = arrayOfNulls<ConstraintLayout>(3)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        ConsumerHomeService(this).tryGetConsumerHome()
        navigateToSearchFirstTag()
        navigateToSearchSecondTag()
        navigateToSearchThirdTag()
    }

    override fun onGetConsumerHomeSuccess(response: ConsumerHomeResponse) {
        dismissLoadingDialog()
        initUser(response.result)
        checkHomePostRes(response)
    }

    override fun onGetConsumerHomeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    @SuppressLint("SetTextI18n")
    private fun initUser(response: com.codepatissier.keki.src.main.consumer.home.model.Result){ // 유저 정보 띄우기
        num = 0
        constraintLayouts = arrayOf(binding.constraintFirstHome, binding.constraintSecondHome, binding.constraintThirdHome)

        // token 없는 경우
        val jwtToken: String? = ApplicationClass.sSharedPreferences.getString(ApplicationClass.Authorization, null)
        if (jwtToken != null && userRole == "구매자") {
            // token 있는 경우 + 구매자일 경우
            if(response.calendarTitle.isNullOrBlank()){
                binding.tvHomeComment.text = response.nickname + "님!\n" + "당신의 특별한 기념일을\n" + "케키와 함께 준비해요!"
            }else{
                if(response.calendarDate == 0){ // d-day인 경우
                    binding.tvHomeComment.text = response.nickname + "님!\n" + response.calendarTitle + "이(가) 오늘이에요!\n케키와 함께 준비해요!"
                }else{
                    binding.tvHomeComment.text = response.nickname + "님!\n" + response.calendarTitle + "이(가) " + response.calendarDate.toString() + "일 남았어요\n특별한 하루를 준비해요!"
                }
            }
        }else{
            //회원가입이 안됐을 경우
            binding.tvHomeComment.text = "어서오세요!\n" +
                    "당신의 특별한 기념일을\n" +
                    "케키와 함께 준비해요!"
        }

    }

    private fun checkHomePostRes(response: ConsumerHomeResponse){ // tag의 내용이 있는 것만 고르기
        for(i in response.result.homeTagResList.indices){
            if(response.result.homeTagResList[i].homePostRes.isNotEmpty()){
                constraintLayouts[num]?.isVisible = true
                homeStoreRecyclerView(response.result.homeTagResList[i], num++)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun homeStoreRecyclerView(response: HomeTagRes, number: Int){
        when(number){
            0 -> {
                binding.tvFirstHomeTag.text = "# " + response.tagName
                homeStoreFirstRecyclerView(response)
            }
            1 -> {
                binding.tvSecondHomeTag.text = "# " + response.tagName
                homeStoreSecondRecyclerView(response)
            }
            2 -> {
                binding.tvThirdHomeTag.text = "# " + response.tagName
                homeStoreThirdRecyclerView(response)
            }
        }
    }

    private fun homeStoreFirstRecyclerView(response: HomeTagRes){
        homeStoreFirstAdapter = HomeStoreAdapter(requireActivity())
        binding.recyclerFirstHome.adapter = homeStoreFirstAdapter

        for(i in response.homePostRes.indices){
            homeStoreFirstDatas.apply { add(HomeStoreData(img = response.homePostRes[i].postImgUrl,
                postIdx = response.homePostRes[i].postIdx,
                name = response.homePostRes[i].storeTitle, tagIdx = response.tagIdx, tagName = response.tagName)) }
        }

        homeStoreFirstAdapter.homeStoreDatas = homeStoreFirstDatas
        homeStoreFirstAdapter.notifyDataSetChanged()
    }

    private fun homeStoreSecondRecyclerView(response: HomeTagRes){
        homeStoreSecondAdapter = HomeStoreAdapter(requireActivity())
        binding.recyclerSecondHome.adapter = homeStoreSecondAdapter

        for(i in response.homePostRes.indices){
            homeStoreSecondDatas.apply { add(HomeStoreData(img = response.homePostRes[i].postImgUrl,
                postIdx = response.homePostRes[i].postIdx,
                name = response.homePostRes[i].storeTitle, tagIdx = response.tagIdx, tagName = response.tagName)) }
        }

        homeStoreSecondAdapter.homeStoreDatas = homeStoreSecondDatas
        homeStoreSecondAdapter.notifyDataSetChanged()
    }

    private fun homeStoreThirdRecyclerView(response: HomeTagRes){
        homeStoreThirdAdapter = HomeStoreAdapter(requireActivity())
        binding.recyclerThirdHome.adapter = homeStoreThirdAdapter

        for(i in response.homePostRes.indices){
            homeStoreThirdDatas.apply { add(HomeStoreData(img = response.homePostRes[i].postImgUrl,
                postIdx = response.homePostRes[i].postIdx,
                name = response.homePostRes[i].storeTitle, tagIdx = response.tagIdx, tagName = response.tagName)) }
        }

        homeStoreThirdAdapter.homeStoreDatas = homeStoreThirdDatas
        homeStoreThirdAdapter.notifyDataSetChanged()
    }

    // 첫번째 태그 서치 화면으로 이동
    private fun navigateToSearchFirstTag(){
        binding.ivFirstHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag 넘기기
            intent.putExtra("searchTag", binding.tvFirstHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

    // 두번째 태그 서치 화면으로 이동
    private fun navigateToSearchSecondTag(){
        binding.ivSecondHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag 넘기기
            intent.putExtra("searchTag", binding.tvSecondHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

    // 세번째 태그 서치 화면으로 이동
    private fun navigateToSearchThirdTag(){
        binding.ivThirdHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            intent.putExtra("searchTag", binding.tvThirdHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

}