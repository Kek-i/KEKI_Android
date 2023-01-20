package com.codepatissier.keki.src.main.consumer.calendar

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerCalendarBinding
import com.codepatissier.keki.util.recycler.calendar.CalendarAnniversaryAdapter
import com.codepatissier.keki.util.recycler.calendar.CalendarAnniversaryData
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.daimajia.swipe.util.Attributes


class ConsumerCalendarFragment : BaseFragment<FragmentConsumerCalendarBinding>
    (FragmentConsumerCalendarBinding::bind, R.layout.fragment_consumer_calendar) {
    private lateinit var calendarAnniversaryAdapter: CalendarAnniversaryAdapter
    private val calendarAnniversaryDataList = mutableListOf<CalendarAnniversaryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendarAnniversaryRecyclerView()
        setClickListenerToFab()
    }

    private fun setCalendarAnniversaryRecyclerView() {
        // 임시 데이터
        for(i in 0 until 5) {
            calendarAnniversaryDataList.apply {
                add(CalendarAnniversaryData("투리 생일", "2023.09.04", "D-270", "매년 반복", "친구", "생일", null))
                add(CalendarAnniversaryData("케키 데모데이", "2023.02.16", "D-31", "디데이", "기념일", null, null))
                add(CalendarAnniversaryData("나랑 안드로이드랑 만난 날♥", "2022.09.01", "D+1234", "날짜수", null, null, null))
                add(CalendarAnniversaryData("누가 술을 마셔 박소정이 술을 마셔 박소정 원샷", "2023.02.10", "D+99999",
                                            "날짜수", "친구", "생일", "파티"))
            }
        }

        binding.rvCalendarAnniversary.addItemDecoration(RecyclerViewDecoration(18))
        binding.rvCalendarAnniversary.setEmptyView(binding.layoutEmptyCalendar)
        binding.rvCalendarAnniversary.setFullView(binding.ivCalendarCherry)
        calendarAnniversaryAdapter = CalendarAnniversaryAdapter(calendarAnniversaryDataList, SwipeListener(binding))
        (calendarAnniversaryAdapter as RecyclerSwipeAdapter<*>).mode = Attributes.Mode.Single
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

    private fun setClickListenerToFab() {
        binding.fabCalendarAdd.setOnClickListener {
            val intent = Intent(this.context, ConsumerCalendarAddActivity::class.java)
            this.startActivity(intent)
        }
    }

    class SwipeListener(binding: FragmentConsumerCalendarBinding) : SimpleSwipeListener() {
        val binding = binding

        override fun onStartOpen(layout: SwipeLayout) {
            // fab 사라지기
            binding.fabCalendarAdd.visibility = GONE
        }
        override fun onClose(layout: SwipeLayout) {
            // fab 생기기
            binding.fabCalendarAdd.visibility = VISIBLE
        }
    }

}
