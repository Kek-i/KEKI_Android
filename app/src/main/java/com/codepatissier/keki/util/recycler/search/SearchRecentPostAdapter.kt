package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemRecentSeenCakeRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment
import com.codepatissier.keki.src.main.consumer.search.model.RecentPostSearch
import com.codepatissier.keki.src.main.consumer.search.model.Result

class SearchRecentPostAdapter(private var searchMainData: Result, val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemRecentSeenCakeRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchCakeImgHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchMainData.recentPostSearches.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchCakeImgHolder).bind(searchMainData.recentPostSearches[position])
    }

    class SearchCakeImgHolder(val context: ConsumerSearchFragment, binding: ItemRecentSeenCakeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val seenCakeImg: ImageView = binding.ivCake

        fun bind(item:RecentPostSearch) {
            Glide.with(context!!)
                .load(item.postImgUrl)
                .centerCrop()
                .into(seenCakeImg)
        }

    }


}