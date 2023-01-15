package com.umc.keki.util.viewpager.storemain


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umc.keki.databinding.ItemStoreMainRecyclerBinding

class StoreMainProductAdapter(private val dataList: ArrayList<StoreMainData>): RecyclerView.Adapter<StoreMainProductAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:StoreMainData){
            viewBinding.ivStoreMain.setImageResource(data.img)
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