package com.umc.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.keki.databinding.ItemRecentSeenCakeRecyclerBinding
import com.umc.keki.src.main.consumer.search.ConsumerSearchFragment
//
//최근 본 케이크용 Adapter
//
class SearchCakeImgAdapter(val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var searchCakeImgData = mutableListOf<SearchCakeImgData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemRecentSeenCakeRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchCakeImgHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchCakeImgData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchCakeImgHolder).bind(searchCakeImgData[position])
    }

    class SearchCakeImgHolder(val context: ConsumerSearchFragment, binding: ItemRecentSeenCakeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val seenCakeImg: ImageView = binding.ivCake

        fun bind(item: SearchCakeImgData) {
            Glide.with(context!!)
                .load(item.img)
                .centerCrop()
                .into(seenCakeImg)
        }

    }


}