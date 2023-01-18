package com.umc.keki.src.main.consumer.mypage

import android.os.Bundle
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerMyPageBinding
import com.umc.keki.src.MainActivity

class ConsumerMyPageFragment : BaseFragment<FragmentConsumerMyPageBinding>
    (FragmentConsumerMyPageBinding::bind, R.layout.fragment_consumer_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileEditClicked()
        ConditionClicked()

    }

    private fun profileEditClicked(){
        binding.cslProfileEdit.setOnClickListener{
            (activity as MainActivity).replaceFragment(ConsumerProfileEditFragment())
        }
    }
    private fun ConditionClicked(){
        binding.tvCondition.setOnClickListener{
            (activity as MainActivity).replaceFragment(ConsumerConditionFragment())
        }
    }

}