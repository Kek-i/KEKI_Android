package com.codepatissier.keki.util.viewpager.storemain


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.ConsumerStoreProductDetailFeedActivity

class StoreMainProductAdapter(val context: FragmentActivity?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var storeMainProductDatas = mutableListOf<StoreMainProductData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStoreMainRecyclerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(storeMainProductDatas[position])
    }

    override fun getItemCount(): Int = storeMainProductDatas.size

    class ViewHolder(val context: FragmentActivity?, val binding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        private val FeedImg : ImageView = binding.ivStoreMain
        val defaultImg = R.drawable.bg_rectangle_radius_10_off_white

        fun bind(item:StoreMainProductData){
            Glide.with(context!!)
                .load(item.dessertImgUrl)
                .placeholder(defaultImg)
                .error(defaultImg)
                .fallback(defaultImg)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                .into(FeedImg)

            itemView.setOnClickListener {
                var intent = Intent(itemView.context,  ConsumerStoreProductDetailFeedActivity::class.java)
                // 디저트번호, 스토어 번호 StoreMainProductData 객체로 detailFeed에 넘기기
                intent.putExtra("StoreMainProductData", item.dessertIdx)
                itemView.context.startActivity(intent)
            }
        }
    }

}
