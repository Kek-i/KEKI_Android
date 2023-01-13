package com.umc.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umc.keki.databinding.ItemSearchListRecyclerBinding
import com.umc.keki.src.main.consumer.search.ConsumerSearchActivity

class SearchListAdapter(val context: ConsumerSearchActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var searchListData = mutableListOf<SearchListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchListRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchListHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchListData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchListHolder).bind(searchListData[position])
    }

    class SearchListHolder(val context: ConsumerSearchActivity, binding: ItemSearchListRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        private val cakeImg: ImageView = binding.ivGridImg
        private val cakeName: TextView = binding.tvGridName
        private val cakePrice: TextView = binding.tvGridPrice

        fun bind(item: SearchListData) {
            Glide.with(context!!)
                .load(item.img)
                .centerCrop()
                .into(cakeImg)
            cakeName.text = item.cakeName
            cakePrice.text = item.price.toString()
        }

    }


}