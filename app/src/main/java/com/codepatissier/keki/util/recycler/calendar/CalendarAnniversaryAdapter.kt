package com.codepatissier.keki.util.recycler.calendar

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.FragmentConsumerCalendarBinding
import com.codepatissier.keki.databinding.ItemCalendarAnniversaryRecyclerBinding
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarService
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarView
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.ConsumerCalendarDetailActivity
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter


class CalendarAnniversaryAdapter(
    val dataList: MutableList<CalendarAnniversaryData>,
    private val fragmentBinding: FragmentConsumerCalendarBinding,
    private val consumerCalendarView: ConsumerCalendarView
):
    RecyclerSwipeAdapter<CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder>() {
    lateinit var itemBinding: ItemCalendarAnniversaryRecyclerBinding
    // 아이템이 스와이프됐는지 여부 List
    var isSwipedItemList: MutableList<Boolean> = MutableList(dataList.size) { false }
    // SwipeListener에서 스와이프 시 플로팅 버튼 안 보이게 하기 위한 변수
    private var swipedLayout: SwipeLayout? = null // 스와이프된 가장 최근 레이아웃
    private var isOpenLayout: Boolean = false // var swipedLayout이 open인지 여부
    // 삭제된 datalist position
    var deletedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAnniversaryViewHolder {
        val itemBinding = ItemCalendarAnniversaryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.itemBinding = itemBinding
        return CalendarAnniversaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CalendarAnniversaryViewHolder, position: Int) {
        var position = position
        mItemManger.bindView(holder.swipeLayout, position)

        holder.bind(dataList[position])
        holder.setClickListenerToDeleteItem(position)
        holder.setClickListenerToViewDetail(dataList[position], this)
        holder.swipeLayout.addSwipeListener(object : SimpleSwipeListener() {
            override fun onOpen(layout: SwipeLayout?) {
                fragmentBinding.fabCalendarAdd.visibility = View.GONE
                if(position < isSwipedItemList.size) {
                    isSwipedItemList[position] = true
                    swipedLayout = layout
                    isOpenLayout = true
                }
            }

            override fun onClose(layout: SwipeLayout?) {
                if(position < isSwipedItemList.size) {
                    isSwipedItemList[position] = false
                    if(swipedLayout == layout) {
                        if(isOpenLayout) {
                            isOpenLayout = false
                        }
                        fragmentBinding.fabCalendarAdd.visibility = View.VISIBLE
                    }
                    else {
                        fragmentBinding.fabCalendarAdd.visibility = View.GONE
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int = dataList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return com.codepatissier.keki.R.id.swipe_layout
    }

    override fun closeAllItems() {
        super.closeAllItems()
        for(i in 0 until isSwipedItemList.size) {
            isSwipedItemList[i] = false
        }
        isOpenLayout = false
        fragmentBinding.fabCalendarAdd.visibility = View.VISIBLE
    }

    inner class CalendarAnniversaryViewHolder(private val itemBinding: ItemCalendarAnniversaryRecyclerBinding): RecyclerView.ViewHolder(itemBinding.root) {
        var swipeLayout = itemBinding.swipeLayout

        fun bind(item: CalendarAnniversaryData) {
            itemBinding.tvAnniversaryTitle.text = item.title
            itemBinding.tvAnniversaryDate.text = item.date
            itemBinding.tvAnniversaryDday.text = item.dday
        }

        fun setClickListenerToDeleteItem(position: Int) {
            itemBinding.layoutDelFrame.setOnClickListener {
                deletedPosition = position
                ConsumerCalendarService(consumerCalendarView).tryDeleteCalendar(dataList[position].calendarIdx)
            }
        }

        fun setClickListenerToViewDetail(
            data: CalendarAnniversaryData,
            adapter: CalendarAnniversaryAdapter
        ) {
            itemBinding.layoutItemFrame.setOnClickListener {
                if(isSwipedItemList.contains(true)) {
                    adapter.closeAllItems()
                }
                else {
                    val intent = Intent(it.context, ConsumerCalendarDetailActivity::class.java)
                    intent.putExtra("calendarIdx", data.calendarIdx)
                    (it.context).startActivity(intent)

//                    if(!adapter.openLayouts.contains(swipeLayout))
//                        adapter.openLayouts.add(swipeLayout)
                    itemBinding.swipeLayout.close()
                    isSwipedItemList[adapterPosition] = false
                }
            }
        }
    }
}