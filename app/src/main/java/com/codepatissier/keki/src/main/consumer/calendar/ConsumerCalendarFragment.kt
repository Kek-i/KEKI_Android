package com.codepatissier.keki.src.main.consumer.calendar

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerCalendarBinding
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.ConsumerCalendarAddActivity
import com.codepatissier.keki.src.main.consumer.calendar.model.ConsumerCalendarListResponse
import com.codepatissier.keki.util.recycler.calendar.CalendarAnniversaryAdapter
import com.codepatissier.keki.util.recycler.calendar.CalendarAnniversaryData
import com.daimajia.swipe.util.Attributes


class ConsumerCalendarFragment : BaseFragment<FragmentConsumerCalendarBinding>
    (FragmentConsumerCalendarBinding::bind, R.layout.fragment_consumer_calendar), ConsumerCalendarView {
    private lateinit var calendarAnniversaryAdapter: CalendarAnniversaryAdapter
    private val calendarAnniversaryDataList = mutableListOf<CalendarAnniversaryData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListenerToFab()
        showLoadingDialog(requireContext())
        ConsumerCalendarService(this).tryGetCalendarList()
    }

    override fun onGetCalendarListSuccess(response: ConsumerCalendarListResponse) {
        dismissLoadingDialog()
        setCalendarAnniversaryRecyclerView(response)
    }

    override fun onGetCalendarListFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류: $message")
    }

    private fun setCalendarAnniversaryRecyclerView(response: ConsumerCalendarListResponse) {
        for(i in response.result.indices) {
            calendarAnniversaryDataList.apply {
                add(CalendarAnniversaryData(
                    calendarIdx = response.result[i].calendarIdx,
                    title = response.result[i].title,
                    date = response.result[i].date,
                    dday = response.result[i].calDate
                ))
            }
        }

        binding.rvCalendarAnniversary.addItemDecoration(RecyclerViewDecoration(20))
        binding.rvCalendarAnniversary.setEmptyView(binding.layoutEmptyCalendar)
        binding.rvCalendarAnniversary.setFullView(binding.ivCalendarCherry)
        calendarAnniversaryAdapter = CalendarAnniversaryAdapter(calendarAnniversaryDataList, binding)
        binding.rvCalendarAnniversary.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    calendarAnniversaryAdapter.closeAllItems()
            }
        })
        calendarAnniversaryAdapter.mode = Attributes.Mode.Single
        binding.rvCalendarAnniversary.adapter = calendarAnniversaryAdapter
        calendarAnniversaryAdapter.notifyDataSetChanged()
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
}
