package com.codepatissier.keki.src.main.consumer.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerMyPageBinding
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.consumer.mypage.notice.NoticeActivity

class ConsumerMyPageFragment : BaseFragment<FragmentConsumerMyPageBinding>
    (FragmentConsumerMyPageBinding::bind, R.layout.fragment_consumer_my_page),ConsumerMyPageView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileEditClicked()
        conditionClicked()
        noticeClicked()

        showLoadingDialog(requireContext())
        ConsumerMyPageService(this).tryGetMyPage()
    }

    private fun profileEditClicked(){
        binding.cslProfileEdit.setOnClickListener{
            val intent = Intent(context, ConsumerProfileEditActivity::class.java)
            startActivity(intent)
        }
    }
    private fun conditionClicked(){
        binding.tvCondition.setOnClickListener{
            val intent = Intent(context, ConsumerConditionActivity::class.java)
            startActivity(intent)
        }
    }
    private fun noticeClicked(){
        binding.cslNotice.setOnClickListener{
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onGetMyPageSuccess(response: ConsumerMyPageResponse) {
        dismissLoadingDialog()

        val defaultImg = R.drawable.bg_oval_off_white
        val imageView = binding.ivProfile

        binding.tvNickName.text = response.result.nickname + "님."
        Glide.with(this)
            .load(response.result.profileImg)
            .placeholder(defaultImg)
            .error(defaultImg)
            .fallback(defaultImg)
            .circleCrop()
            .into(imageView)
    }

    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}