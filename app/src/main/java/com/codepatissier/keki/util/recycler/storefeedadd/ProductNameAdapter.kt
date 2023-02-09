package com.codepatissier.keki.util.recycler.storefeedadd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.ItemProductNameRecyclerBinding

class ProductNameAdapter(private val dataList: MutableList<ProductNameData>) : RecyclerView.Adapter<ProductNameAdapter.ProductNameViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductNameViewHolder {
        val itemBinding = ItemProductNameRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductNameViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductNameViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    class ProductNameViewHolder(private val itemBinding: ItemProductNameRecyclerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ProductNameData) {
            itemBinding.tvProductName.text = item.productName
        }
    }

    interface ItemClickListener {
        fun onClick(position: Int)
    }
}