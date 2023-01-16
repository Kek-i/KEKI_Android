package com.umc.keki.util.recycler.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.keki.databinding.ItemNoticeRecyclerBinding

class NoticeAdapter(private val dataList:ArrayList<NoticeData>):RecyclerView.Adapter<NoticeAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: ItemNoticeRecyclerBinding):RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:NoticeData){
            viewBinding.tvNotice.setText(data.notice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemNoticeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}