package com.codepatissier.keki.util.viewpager.storemain


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.noticedetail.NoticeDetailActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.util.recycler.notice.NoticeData

class StoreMainStoreAdapter(val context: FragmentActivity?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var storeMainStoreDatas = mutableListOf<StoreMainStoreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStoreMainRecyclerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(storeMainStoreDatas[position])
    }

    override fun getItemCount(): Int = storeMainStoreDatas.size

    class ViewHolder(val context: FragmentActivity?, val binding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        private val FeedImg : ImageView = binding.ivStoreMain
        val defaultImg = R.drawable.bg_rectangle_radius_10_off_white

        fun bind(item:StoreMainStoreData){
            Glide.with(context!!)
                .load(item.img)
                .placeholder(defaultImg)
                .error(defaultImg)
                .fallback(defaultImg)
                .into(FeedImg)

            itemView.setOnClickListener {
                var intent = Intent(itemView.context, ConsumerStoreDetailFeedActivity::class.java)
                // 스토어번호, 피드 번호 StoreMainStoreData 객체로 detailFeed로 넘기기
                intent.putExtra("StoreMainStoreData", item)
                itemView.context.startActivity(intent)
            }
        }
    }

}
