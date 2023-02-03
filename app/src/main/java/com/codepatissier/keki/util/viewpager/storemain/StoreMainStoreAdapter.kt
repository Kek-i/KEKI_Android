package com.codepatissier.keki.util.viewpager.storemain


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity

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


        val width = getItemWidth()/3

        fun bind(item:StoreMainStoreData){
            Glide.with(context!!)
                .load(item.postImgUrl)
                .placeholder(defaultImg)
                .override(width,width)
                .error(defaultImg)
                .fallback(defaultImg)
                .centerCrop()
                .into(FeedImg)


            itemView.setOnClickListener {
                var intent = Intent(itemView.context, ConsumerStoreDetailFeedActivity::class.java)
                // 스토어번호, 피드 번호 StoreMainStoreData 객체로 detailFeed에 넘기기
                intent.putExtra("StoreMainStoreData", item)
                itemView.context.startActivity(intent)
            }
        }

        // display 별 화면에 맞는 그리드 크기 구하기
        fun getItemWidth():Int{
            val display = this.context?.resources?.displayMetrics
            val displaywidth = display?.widthPixels
            // 마진 값 dp를 px로 변경하기
            val density = display?.density
            val margin = (10 * density!! +0.5)*4
            // 마진값을 뺀 길이 구하기
            val width = displaywidth?.minus(margin.toInt())
            return width!!
        }
    }

}
