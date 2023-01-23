package com.codepatissier.keki.util.recycler.notice

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.ItemNoticeRecyclerBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.NoticeDetailActivity

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
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, NoticeDetailActivity::class.java)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size
}