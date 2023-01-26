package com.codepatissier.keki.util.recycler.calendar

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.FragmentConsumerCalendarBinding
import com.codepatissier.keki.databinding.ItemCalendarAnniversaryRecyclerBinding
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.ConsumerCalendarDetailActivity
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
    // 아이템이 스와이프됐는지 여부 List
    private var isSwipedItemList: MutableList<Boolean> = MutableList(dataList.size) { false }
    // SwipeListener에서 스와이프 시 플로팅 버튼 안 보이게 하기 위한 변수
    private var swipedLayout: SwipeLayout? = null // 스와이프된 가장 최근 레이아웃
    private var isOpenLayout: Boolean = false // var swipedLayout이 open인지 여부


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAnniversaryViewHolder {
        val itemBinding = ItemCalendarAnniversaryRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.itemBinding = itemBinding
        return CalendarAnniversaryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CalendarAnniversaryViewHolder, position: Int) {
        var position = position
        mItemManger.bindView(holder.swipeLayout, position)

        holder.bind(dataList[position])
        holder.setClickListenerToDeleteItem(dataList, isSwipedItemList, position, this)
        holder.setClickListenerToViewDetail(dataList[position], isSwipedItemList, this)
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

    class CalendarAnniversaryViewHolder(private val itemBinding: ItemCalendarAnniversaryRecyclerBinding): RecyclerView.ViewHolder(itemBinding.root) {
        var swipeLayout = itemBinding.swipeLayout

        fun bind(item: CalendarAnniversaryData) {
            itemBinding.tvAnniversaryTitle.text = item.title
            itemBinding.tvAnniversaryDate.text = item.date
            itemBinding.tvAnniversaryDday.text = item.dday
        }

        fun setClickListenerToDeleteItem(
            dataList: MutableList<CalendarAnniversaryData>,
            isSwipedItemList: MutableList<Boolean>,
            position: Int,
            adapter: CalendarAnniversaryAdapter
        ) {
            itemBinding.layoutDelFrame.setOnClickListener {
                isSwipedItemList.removeLast()
                adapter.removeShownLayouts(itemBinding.swipeLayout)
                adapter.closeAllItems()
                adapter.fragmentBinding.fabCalendarAdd.visibility = View.VISIBLE
                dataList.removeAt(position)
                adapter.notifyDataSetChanged()
                // 아래의 removed가 붙은 함수를 사용하면 empty view가 뜨지 않음
//                adapter.notifyItemRemoved(position)
//                adapter.notifyItemRangeRemoved(position, dataList.size)
            }
        }

        fun setClickListenerToViewDetail(
            data: CalendarAnniversaryData,
            isSwipedItemList: MutableList<Boolean>,
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