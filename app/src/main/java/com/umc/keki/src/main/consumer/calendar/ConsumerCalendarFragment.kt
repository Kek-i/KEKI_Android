package com.umc.keki.src.main.consumer.calendar

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.umc.keki.R
import com.umc.keki.config.BaseFragment
import com.umc.keki.databinding.FragmentConsumerCalendarBinding
import com.umc.keki.util.recycler.calendar.CalendarAnniversaryAdapter
import com.umc.keki.util.recycler.calendar.CalendarAnniversaryData


class ConsumerCalendarFragment : BaseFragment<FragmentConsumerCalendarBinding>
    (FragmentConsumerCalendarBinding::bind, R.layout.fragment_consumer_calendar) {
    private lateinit var calendarAnniversaryAdapter: CalendarAnniversaryAdapter
    private val calendarAnniversaryDataList = mutableListOf<CalendarAnniversaryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendarAnniversaryRecyclerView()
    }

    private fun setCalendarAnniversaryRecyclerView() {
        // 임시 데이터
        for(i in 0 until 5) {
            calendarAnniversaryDataList.apply {
                add(CalendarAnniversaryData("투리 생일", "2023.09.04", "D-270"))
                add(CalendarAnniversaryData("케키 데모데이", "2023.02.16", "D-31"))
                add(CalendarAnniversaryData("나랑 안드로이드랑 만난 날♥", "2022.09.01", "D+150"))
            }
        }

        binding.rvCalendarAnniversary.setEmptyView(binding.layoutEmptyCalendar)
        binding.rvCalendarAnniversary.setFullView(binding.ivCalendarCherry)
        calendarAnniversaryAdapter = CalendarAnniversaryAdapter(calendarAnniversaryDataList)
        binding.rvCalendarAnniversary.adapter = calendarAnniversaryAdapter
    }

}