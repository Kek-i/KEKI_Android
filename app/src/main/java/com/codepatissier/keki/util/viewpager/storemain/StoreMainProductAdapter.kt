package com.codepatissier.keki.util.viewpager.storemain


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductDetailFeedActivity

class StoreMainProductAdapter(private val dataList: ArrayList<StoreMainData>): RecyclerView.Adapter<StoreMainProductAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:StoreMainData){
            viewBinding.ivStoreMain.setImageResource(data.img)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, ConsumerStoreProductDetailFeedActivity::class.java)
                intent.run { itemView.context?.startActivity(intent) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemStoreMainRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}