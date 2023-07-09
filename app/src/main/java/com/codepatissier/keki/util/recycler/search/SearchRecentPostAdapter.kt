package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemRecentSeenCakeRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment
import com.codepatissier.keki.src.main.consumer.search.model.RecentPostSearch
import com.codepatissier.keki.src.main.consumer.search.model.Result
import com.google.firebase.storage.FirebaseStorage

class SearchRecentPostAdapter(private var searchMainData: Result, val context: ConsumerSearchFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemRecentSeenCakeRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchCakeImgHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchMainData.recentPostSearches.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchCakeImgHolder).bind(searchMainData.recentPostSearches[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    class SearchCakeImgHolder(val context: ConsumerSearchFragment, binding: ItemRecentSeenCakeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var fbStorage : FirebaseStorage?= null
        private val seenCakeImg: ImageView = binding.ivCake

        fun bind(item:RecentPostSearch) {
            if (item.postImgUrl.startsWith("http")){
                Glide.with(context!!)
                    .load(item.postImgUrl)
                    .centerCrop()
                    .into(seenCakeImg)
            }else {
                fbStorage = FirebaseStorage.getInstance()
                var storageRef = fbStorage?.reference?.child(item.postImgUrl)

                storageRef?.downloadUrl?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Glide.with(context!!)
                            .load(it.result)
                            .centerCrop()
                            .into(seenCakeImg)
                    }
                }
            }
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