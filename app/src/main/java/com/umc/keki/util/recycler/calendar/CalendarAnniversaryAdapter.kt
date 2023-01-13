package com.umc.keki.util.recycler.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.keki.databinding.ItemCalendarAnniversaryRecyclerBinding

class CalendarAnniversaryAdapter(private val dataList: List<CalendarAnniversaryData>):
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemCalendarAnniversaryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarAnniversaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as CalendarAnniversaryViewHolder).bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    class CalendarAnniversaryViewHolder(private val itemBinding: ItemCalendarAnniversaryRecyclerBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private var isClamped: Boolean = false

        fun bind(item: CalendarAnniversaryData) {
            itemBinding.tvAnniversaryTitle.text = item.title
            itemBinding.tvAnniversaryDate.text = item.date
            itemBinding.tvAnniversaryDday.text = item.dday
        }

        fun getClamped(): Boolean {
            return isClamped
        }

        fun setClamped(isClamped: Boolean) {
            this.isClamped = isClamped
        }
    }
}