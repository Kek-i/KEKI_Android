package com.umc.keki.src.main.consumer.mypage

import android.os.Bundle
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerConditionBinding
import com.umc.keki.src.MainActivity

class ConsumerConditionFragment : BaseFragment<FragmentConsumerConditionBinding>
    (FragmentConsumerConditionBinding::bind, R.layout.fragment_consumer_condition) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            (activity as MainActivity).replaceFragment(ConsumerMyPageFragment())
        }
    }
}