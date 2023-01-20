package com.codepatissier.keki.util.recycler.calendar

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemCalendarAnniversaryRecyclerBinding
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarDetailActivity
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarFragment
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter


class CalendarAnniversaryAdapter(
    private val dataList: MutableList<CalendarAnniversaryData>,
    swipeListener: ConsumerCalendarFragment.SwipeListener
):
    RecyclerSwipeAdapter<ViewHolder>() {
    private lateinit var itemBinding: ItemCalendarAnniversaryRecyclerBinding
    private val swipeListener = swipeListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemCalendarAnniversaryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.itemBinding = itemBinding
        itemBinding.swipeLayout.addSwipeListener(swipeListener)
        return CalendarAnniversaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as CalendarAnniversaryViewHolder).bind(dataList[position])
        holder.setClickListenerToDeleteItem(dataList, position, this)
        holder.setClickListenerToViewDetail(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return com.codepatissier.keki.R.id.swipe_layout
    }

    class CalendarAnniversaryViewHolder(private val itemBinding: ItemCalendarAnniversaryRecyclerBinding): RecyclerView.ViewHolder(itemBinding.root) {
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