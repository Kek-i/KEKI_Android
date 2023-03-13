package com.codepatissier.keki.util.recycler.search

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemSearchPopularRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment
import com.codepatissier.keki.src.main.consumer.search.model.PopularSearch
import com.codepatissier.keki.src.main.consumer.search.model.Result
import java.util.*
import kotlin.collections.ArrayList

class SearchPopularAdapter(private var searchMainData: Result, val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchPopularRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchPopularViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchMainData.popularSearches.size

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchPopularViewHolder).bind(searchMainData.popularSearches[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        //태그별 색상 변경
        holder.popularTagName.backgroundTintList = when (position) {
            0 -> ColorStateList.valueOf(getColor(holder.itemView.context,R.color.off_white))
            1 -> ColorStateList.valueOf(getColor(holder.itemView.context, R.color.very_light_pink))
            2 -> ColorStateList.valueOf(getColor(holder.itemView.context, R.color.light_peach))
            3 -> ColorStateList.valueOf(getColor(holder.popularTagName.context, R.color.light_peach_1))
            else -> {ColorStateList.valueOf(getColor(holder.popularTagName.context, R.color.pale_lavender))}
        }
    }

    class SearchPopularViewHolder(val context: ConsumerSearchFragment, binding: ItemSearchPopularRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        val popularTagName: TextView = itemView.findViewById(R.id.tv_popular_search_tag)

        fun bind(item: PopularSearch) {
            popularTagName.text = item.searchWord
        }
    }

    fun getRandomColor(): Int {
        val colorList: ArrayList<Int> = ArrayList()
        colorList.add(R.color.off_white)
        colorList.add(R.color.pale_lavender)
        colorList.add(R.color.light_peach)
        colorList.add(R.color.light_peach_1)
        colorList.add(R.color.very_light_pink)

        val random = Random()
        val randomIndex: Int = random.nextInt(colorList.size)

        return colorList[randomIndex]
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