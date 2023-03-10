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
    private val userRole = ApplicationClass.sSharedPreferences.getString(ApplicationClass.UserRole, "λΉνμ")

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
        showCustomToast("μ€λ₯ : $message")
    }

    @SuppressLint("SetTextI18n")
    private fun initUser(response: com.codepatissier.keki.src.main.consumer.home.model.Result){ // μ μ  μ λ³΄ λμ°κΈ°
        num = 0
        constraintLayouts = arrayOf(binding.constraintFirstHome, binding.constraintSecondHome, binding.constraintThirdHome)

        // token μλ κ²½μ°
        val jwtToken: String? = ApplicationClass.sSharedPreferences.getString(ApplicationClass.Authorization, null)
        if (jwtToken != null && userRole == "κ΅¬λ§€μ") {
            // token μλ κ²½μ° + κ΅¬λ§€μμΌ κ²½μ°
            if(response.calendarTitle.isNullOrBlank()){
                binding.tvHomeComment.text = response.nickname + "λ!\n" + "λΉμ μ νΉλ³ν κΈ°λμΌμ\n" + "μΌν€μ ν¨κ» μ€λΉν΄μ!"
            }else{
                if(response.calendarDate == 0){ // d-dayμΈ κ²½μ°
                    binding.tvHomeComment.text = response.nickname + "λ!\n" + response.calendarTitle + "μ΄(κ°) μ€λμ΄μμ!\nμΌν€μ ν¨κ» μ€λΉν΄μ!"
                }else{
                    binding.tvHomeComment.text = response.nickname + "λ!\n" + response.calendarTitle + "μ΄(κ°) " + response.calendarDate.toString() + "μΌ λ¨μμ΄μ\nνΉλ³ν νλ£¨λ₯Ό μ€λΉν΄μ!"
                }
            }
        }else{
            //νμκ°μμ΄ μλμ κ²½μ°
            binding.tvHomeComment.text = "μ΄μμ€μΈμ!\n" +
                    "λΉμ μ νΉλ³ν κΈ°λμΌμ\n" +
                    "μΌν€μ ν¨κ» μ€λΉν΄μ!"
        }

    }

    private fun checkHomePostRes(response: ConsumerHomeResponse){ // tagμ λ΄μ©μ΄ μλ κ²λ§ κ³ λ₯΄κΈ°
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

    // μ²«λ²μ§Έ νκ·Έ μμΉ νλ©΄μΌλ‘ μ΄λ
    private fun navigateToSearchFirstTag(){
        binding.ivFirstHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag λκΈ°κΈ°
            intent.putExtra("searchTag", binding.tvFirstHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

    // λλ²μ§Έ νκ·Έ μμΉ νλ©΄μΌλ‘ μ΄λ
    private fun navigateToSearchSecondTag(){
        binding.ivSecondHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            // tag λκΈ°κΈ°
            intent.putExtra("searchTag", binding.tvSecondHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

    // μΈλ²μ§Έ νκ·Έ μμΉ νλ©΄μΌλ‘ μ΄λ
    private fun navigateToSearchThirdTag(){
        binding.ivThirdHomeChevronRight.setOnClickListener {
            val intent = Intent(context, ConsumerSearchActivity::class.java)
            intent.putExtra("searchTag", binding.tvThirdHomeTag.text.replace("# ".toRegex(), ""))
            startActivity(intent)
        }
    }

}