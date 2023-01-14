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
    //클래스 안에 다른 클래스를 만든다해서 inner class
    //ViewHolder 객체
    inner class DataViewHolder(private val viewBinding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(viewBinding.root){
        // 뷰 홀더가 실제로 어떤 것을 표시할 때 호출하는 함수
        fun bind(data:StoreMainData){
            viewBinding.ivStoreMain.setImageResource(data.img)
        }
    }

    // ViewHolder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemStoreMainRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(viewBinding)
    }

    // ViewHolder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    // 표현할 Item의 총 갯수
    override fun getItemCount(): Int = dataList.size
}