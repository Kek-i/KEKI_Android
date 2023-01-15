package com.umc.keki.util.viewpager.storemain


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.keki.databinding.ItemHomeStoreRecyclerBinding
import com.umc.keki.databinding.ItemStoreMainRecyclerBinding
import com.umc.keki.util.recycler.home.HomeStoreAdapter

class StoreMainAdapter(private val dataList: ArrayList<StoreMainData>): RecyclerView.Adapter<StoreMainAdapter.DataViewHolder>() {

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