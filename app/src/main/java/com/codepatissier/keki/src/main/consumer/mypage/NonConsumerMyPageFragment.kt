package com.codepatissier.keki.src.main.consumer.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentNonConsumerMyPageBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.NoticeActivity
import com.codepatissier.keki.src.main.auth.LoginActivity

class NonConsumerMyPageFragment : BaseFragment<FragmentNonConsumerMyPageBinding>
    (FragmentNonConsumerMyPageBinding::bind, R.layout.fragment_non_consumer_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryGetLogin()
        conditionClicked()
        noticeClicked()
    }

    private fun tryGetLogin(){
        binding.constraintLayoutUser.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            this@NonConsumerMyPageFragment.activity?.finish()
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

}