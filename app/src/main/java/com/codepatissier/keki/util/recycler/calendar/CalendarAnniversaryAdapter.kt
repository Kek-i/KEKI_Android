package com.codepatissier.keki.util.recycler.calendar

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.FragmentConsumerCalendarBinding
import com.codepatissier.keki.databinding.ItemCalendarAnniversaryRecyclerBinding
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarDetailActivity
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter


class CalendarAnniversaryAdapter(
    private val dataList: MutableList<CalendarAnniversaryData>,
    fragmentBinding: FragmentConsumerCalendarBinding
):
    RecyclerSwipeAdapter<CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder>() {
    private lateinit var itemBinding: ItemCalendarAnniversaryRecyclerBinding
    private val fragmentBinding = fragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAnniversaryViewHolder {
        val itemBinding = ItemCalendarAnniversaryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.itemBinding = itemBinding
        return CalendarAnniversaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CalendarAnniversaryViewHolder, position: Int) {
        var position = position
        holder.bind(dataList[position])
        holder.setClickListenerToDeleteItem(dataList, position, this)
        holder.setClickListenerToViewDetail(dataList[position])
        holder.swipeLayout.addSwipeListener(object : SimpleSwipeListener() {
            override fun onOpen(layout: SwipeLayout?) {
                // fab 사라지기
                fragmentBinding.fabCalendarAdd.visibility = View.GONE
//                openItem(position)
            }
            override fun onClose(layout: SwipeLayout?) {
                // fab 생기기
                fragmentBinding.fabCalendarAdd.visibility = View.VISIBLE
//                closeItem(position)
            }
        })
    }

    override fun getItemCount(): Int = dataList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return com.codepatissier.keki.R.id.swipe_layout
    }

    class CalendarAnniversaryViewHolder(private val itemBinding: ItemCalendarAnniversaryRecyclerBinding): RecyclerView.ViewHolder(itemBinding.root) {
        var swipeLayout = itemBinding.swipeLayout

        fun bind(item: CalendarAnniversaryData) {
            itemBinding.tvAnniversaryTitle.text = item.title
            itemBinding.tvAnniversaryDate.text = item.date
            itemBinding.tvAnniversaryDday.text = item.dday
        }

        fun setClickListenerToDeleteItem(
            dataList: MutableList<CalendarAnniversaryData>,
            position: Int,
            adapter: CalendarAnniversaryAdapter
        ) {
            itemBinding.layoutDelFrame.setOnClickListener {
                dataList.removeAt(position)
                adapter.notifyDataSetChanged()
    //            this@CalendarAnniversaryAdapter.notifyItemRemoved(position)
    //            this@CalendarAnniversaryAdapter.notifyItemRangeRemoved(position, dataList.size - position)
            }
        }

        fun setClickListenerToViewDetail(data: CalendarAnniversaryData) {
            itemBinding.layoutItemFrame.setOnClickListener {
                val intent = Intent(it.context, ConsumerCalendarDetailActivity::class.java)
                intent.putExtra("title", data.title)
                intent.putExtra("date", data.date)
                intent.putExtra("dday", data.dday)
                intent.putExtra("type", data.type)
                intent.putExtra("firstTag", data.firstTag)
                intent.putExtra("secondTag", data.secondTag)
                intent.putExtra("thirdTag", data.thirdTag)
                (it.context).startActivity(intent)
            }
        }
    }

}