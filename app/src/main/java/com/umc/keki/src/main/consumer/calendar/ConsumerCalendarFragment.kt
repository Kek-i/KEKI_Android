package com.umc.keki.src.main.consumer.calendar

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
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
                add(CalendarAnniversaryData("나랑 안드로이드랑 만난 날♥", "2022.09.01", "D+1234"))
                add(CalendarAnniversaryData("누가 술을 마셔 박소정이 술을 마셔 박소정 원샷", "2023.02.10", "D+99999"))
            }
        }

        binding.rvCalendarAnniversary.addItemDecoration(RecyclerViewDecoration(15))
        binding.rvCalendarAnniversary.setEmptyView(binding.layoutEmptyCalendar)
        binding.rvCalendarAnniversary.setFullView(binding.ivCalendarCherry)
        calendarAnniversaryAdapter = CalendarAnniversaryAdapter(calendarAnniversaryDataList)
        binding.rvCalendarAnniversary.adapter = calendarAnniversaryAdapter
    }

    class RecyclerViewDecoration(private val divHeight: Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = divHeight
        }
    }
}