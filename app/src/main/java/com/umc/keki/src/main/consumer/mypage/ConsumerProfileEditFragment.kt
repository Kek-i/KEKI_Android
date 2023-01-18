package com.umc.keki.src.main.consumer.mypage

import android.os.Bundle
import android.view.View
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerProfileEditBinding
import com.umc.keki.src.MainActivity

class ConsumerProfileEditFragment :BaseFragment<FragmentConsumerProfileEditBinding>
    (FragmentConsumerProfileEditBinding::bind, R.layout.fragment_consumer_profile_edit) {

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