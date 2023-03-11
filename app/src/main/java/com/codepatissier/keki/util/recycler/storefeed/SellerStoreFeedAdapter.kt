package com.codepatissier.keki.util.recycler.storefeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemProgressbarLoadingBinding
import com.codepatissier.keki.databinding.ItemSellerStoreFeedRecyclerBinding

class SellerStoreFeedAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {
    var storeFeedDatas = mutableListOf<StoreFeedData>()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType){
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemSellerStoreFeedRecyclerBinding.inflate(layoutInflater, parent, false)
                SellerStoreFeedViewHolder(context, itemBinding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemProgressbarLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is SellerStoreFeedViewHolder){
            (holder as SellerStoreFeedViewHolder).bind(storeFeedDatas[position])
        }else{

        }
    }

    override fun getItemCount(): Int = storeFeedDatas.size

    class LoadingViewHolder(private val binding: ItemProgressbarLoadingBinding): RecyclerView.ViewHolder(binding.root){

    }

    // 뷰 타입 정하기
    override fun getItemViewType(position: Int): Int {
        return if (storeFeedDatas[position] != null){
            VIEW_TYPE_ITEM
        }else{
            VIEW_TYPE_LOADING
        }
    }

    fun setList(storeFeed: MutableList<StoreFeedData>, hasNext: Boolean){
        storeFeedDatas.addAll(storeFeed)

    }

    class SellerStoreFeedViewHolder(val context: FragmentActivity?, val binding: ItemSellerStoreFeedRecyclerBinding): ViewHolder(binding.root){

        // display 별 화면에 맞는 그리드 크기 구하기
        private fun getItemWidth():Int{
            val display = this.context?.resources?.displayMetrics
            val displaywidth = display?.widthPixels

            return displaywidth!!
        }

        fun bind(item: StoreFeedData){

        }
    }
}