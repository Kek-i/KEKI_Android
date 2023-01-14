package com.umc.keki.util.recycler.storefeed

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.keki.R
import com.umc.keki.databinding.ItemStoreFeedRecyclerBinding
import com.umc.keki.src.main.consumer.store.DetailImageAdapter

class StoreFeedAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var storeFeedDatas = mutableListOf<StoreFeedData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStoreFeedRecyclerBinding.inflate(layoutInflater, parent, false)
        return StoreFeedViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as StoreFeedViewHolder).bind(storeFeedDatas[position])
    }

    override fun getItemCount(): Int = storeFeedDatas.size

    class StoreFeedViewHolder(val context: FragmentActivity?, val binding: ItemStoreFeedRecyclerBinding): ViewHolder(binding.root){
        private val nickname: TextView = binding.tvStoreFeedSellerNickname

        fun bind(item: StoreFeedData){
            nickname.text = item.nickname

            var img = arrayOfNulls<Drawable>(2)

            img[0] = context?.getDrawable(R.drawable.softsquared_logo)
            img[1] = context?.getDrawable(R.drawable.ex_cake)

            val pagerAdapter = DetailImageAdapter(context!!, img)
            binding.vpStoreFeedImg.adapter = pagerAdapter
            binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)

        }
    }
}