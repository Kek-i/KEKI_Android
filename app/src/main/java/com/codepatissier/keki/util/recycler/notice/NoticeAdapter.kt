package com.codepatissier.keki.util.recycler.notice

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemNoticeRecyclerBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.NoticeDetailActivity

class NoticeAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var noticeDatas = mutableListOf<NoticeData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemNoticeRecyclerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(noticeDatas[position])
    }

    override fun getItemCount(): Int = noticeDatas.size

    class ViewHolder(val context: FragmentActivity?, val binding: ItemNoticeRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        private val noticeTitle : TextView = binding.tvNotice

        fun bind(item: NoticeData){
            noticeTitle.text = item.noticeTitle

            itemView.setOnClickListener {
                var intent = Intent(itemView.context, NoticeDetailActivity::class.java)
                intent.putExtra("noticeIdx", item.noticeIdx)
                itemView.context.startActivity(intent)
            }
        }
    }

}