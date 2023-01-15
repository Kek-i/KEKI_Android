package com.umc.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc.keki.R
import com.umc.keki.databinding.ItemSearchRecentRecyclerBinding
import com.umc.keki.src.main.consumer.search.ConsumerSearchFragment

class SearchRecentAdapter(val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var recentSearchData = mutableListOf<SearchRecentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchRecentRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchRecentViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = recentSearchData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchRecentViewHolder).bind(recentSearchData[position])
    }

    class SearchRecentViewHolder(val context: ConsumerSearchFragment, binding: ItemSearchRecentRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val recentTagName: TextView = itemView.findViewById(R.id.tv_recent_search_tag)

        fun bind(item: SearchRecentData) {
            recentTagName.text = item.bearer
        }
    }


}