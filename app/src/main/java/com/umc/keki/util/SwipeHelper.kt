package com.umc.keki.util

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.umc.keki.R
import com.umc.keki.util.recycler.calendar.CalendarAnniversaryAdapter
import java.lang.Float.max

class SwipeHelper: ItemTouchHelper.Callback() {
    // fix로 네이밍 수정
    private var clamp: Float = 0f
    private var currentDx: Float = 0f

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        clamp = (viewHolder as CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder)
            .itemView.findViewById<View>(R.id.tv_calendar_item_delete)
            .width
            .toFloat()
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    // 사용자 상호작용/애니메이션 끝나면
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getDefaultUIUtil().clearView(getSwipedView(viewHolder))
    }

    // 다른 item 스와이프/드래그하면 : actionState로 스와이프인지 확인
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            getDefaultUIUtil().onSelected(getSwipedView(it))
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            val view = getSwipedView(viewHolder)
            val isClamped = getClamped(viewHolder as CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder)
            val x = getX(view, dX, isClamped, isCurrentlyActive)
            currentDx = x

            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                view,
                x,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }

    private fun getX(view: View, dX: Float, isClamped: Boolean, isCurrentlyActive: Boolean): Float {
        val maxSwipe: Float = -clamp
        val x =
            if(isClamped) {
                if(isCurrentlyActive) dX - clamp
                else maxSwipe
            } else dX

        return max(maxSwipe, x)
    }

    private fun getSwipedView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder).itemView.findViewById(R.id.layout_item_frame)
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 1000
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        setClamped(viewHolder as CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder, currentDx <= -clamp/1.5)
        return 10f
    }

    private fun getClamped(viewHolder: CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder): Boolean {
        return viewHolder.getClamped()
    }
    private fun setClamped(viewHolder: CalendarAnniversaryAdapter.CalendarAnniversaryViewHolder, isClamped: Boolean) {
        viewHolder.setClamped(isClamped)
    }

}