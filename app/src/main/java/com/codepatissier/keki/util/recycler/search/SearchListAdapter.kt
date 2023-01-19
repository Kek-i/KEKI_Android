package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemSearchListRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchActivity

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
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
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