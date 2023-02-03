package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemSearchRecentRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment
import com.codepatissier.keki.src.main.consumer.search.model.RecentSearch
import com.codepatissier.keki.src.main.consumer.search.model.Result

class SearchRecentAdapter(private var searchMainData: Result, val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchRecentRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchRecentViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchMainData.recentSearches.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchRecentViewHolder).bind(searchMainData.recentSearches[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    class SearchRecentViewHolder(val context: ConsumerSearchFragment, binding: ItemSearchRecentRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        private val recentTagName: TextView = itemView.findViewById(R.id.tv_recent_search_tag)

        fun bind(item: RecentSearch) {
            recentTagName.text = item.searchWord
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener
}