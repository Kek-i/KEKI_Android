package com.codepatissier.keki.util.recycler.like

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemLikeRecyclerBinding
import java.text.DecimalFormat

class LikeFeedAdapter(private val dataList: List<LikeFeedData>, private val context: Context) : RecyclerView.Adapter<LikeFeedAdapter.LikeFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeFeedViewHolder {
        val itemBinding = ItemLikeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeFeedViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LikeFeedViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int = dataList.size

    class LikeFeedViewHolder(private val itemBinding: ItemLikeRecyclerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: LikeFeedData, context: Context) {
            Glide.with(context)
                .load(item.postImgUrl)
                .centerCrop()
                .into(itemBinding.iv)
            itemBinding.tvProductName.text = item.productName.replace(" ", "\u00A0")
            itemBinding.tvProductPrice.text = DecimalFormat("###,###").format(item.productPrice.toLong())
        }
    }
}