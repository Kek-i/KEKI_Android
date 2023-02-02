package com.codepatissier.keki.src.main.consumer.calendar

import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentNonConsumerCalendarBinding

class NonConsumerCalendarFragment: BaseFragment<FragmentNonConsumerCalendarBinding>
    (FragmentNonConsumerCalendarBinding::bind, R.layout.fragment_non_consumer_calendar) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}