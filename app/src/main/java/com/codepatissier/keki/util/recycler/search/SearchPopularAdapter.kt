package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemSearchPopularRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment
import com.codepatissier.keki.src.main.consumer.search.model.PopularSearch
import com.codepatissier.keki.src.main.consumer.search.model.Result

class SearchPopularAdapter(private var searchMainData: Result, val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchPopularRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchPopularViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchMainData.popularSearches.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchPopularViewHolder).bind(searchMainData.popularSearches[position])
    }

    class SearchPopularViewHolder(val context: ConsumerSearchFragment, binding: ItemSearchPopularRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val popularTagName: TextView = itemView.findViewById(R.id.tv_popular_search_tag)

        fun bind(item: PopularSearch) {
            popularTagName.text = item.searchWord
        }
    }
}