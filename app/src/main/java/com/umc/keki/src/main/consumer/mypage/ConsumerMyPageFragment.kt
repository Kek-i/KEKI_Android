package com.umc.keki.src.main.consumer.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerMyPageBinding

class ConsumerMyPageFragment : BaseFragment<FragmentConsumerMyPageBinding>
    (FragmentConsumerMyPageBinding::bind, R.layout.fragment_consumer_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileEditClicked()
        ConditionClicked()

    }

    private fun profileEditClicked(){
        binding.cslProfileEdit.setOnClickListener{
            val intent = Intent(context, ConsumerProfileEditActivity::class.java)
            startActivity(intent)
        }
    }
    private fun ConditionClicked(){
        binding.tvCondition.setOnClickListener{
            val intent = Intent(context, ConsumerConditionActivity::class.java)
            startActivity(intent)
        }
    }

}