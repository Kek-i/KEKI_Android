package com.codepatissier.keki.util

import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentNonConsumerBinding

class NonConsumerFragment: BaseFragment<FragmentNonConsumerBinding>
    (FragmentNonConsumerBinding::bind, R.layout.fragment_non_consumer) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}