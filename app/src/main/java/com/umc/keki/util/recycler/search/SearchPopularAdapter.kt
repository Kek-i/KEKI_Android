package com.umc.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.umc.keki.R
import com.umc.keki.databinding.ItemSearchPopularRecyclerBinding
import com.umc.keki.src.main.consumer.search.ConsumerSearchFragment
//
//검색 - 인기 케이크 Adapter
//
class SearchPopularAdapter(val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var searchPopularData = mutableListOf<SearchPopularData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchPopularRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchPopularViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchPopularData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchPopularViewHolder).bind(searchPopularData[position])
    }

    class SearchPopularViewHolder(val context: ConsumerSearchFragment, binding: ItemSearchPopularRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val popularTagName: TextView = itemView.findViewById(R.id.tv_popular_search_tag)

        fun bind(item: SearchPopularData) {
            popularTagName.text = item.popular
        }
    }


}