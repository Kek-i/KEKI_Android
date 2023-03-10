package com.codepatissier.keki.util.viewpager.storemain.seller


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.util.viewpager.storemain.StoreMainStoreData
import com.google.firebase.storage.FirebaseStorage

class SellerStoreMainStoreAdapter(val context: FragmentActivity?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        var fbStorage : FirebaseStorage?= null
        private val FeedImg : ImageView = binding.ivStoreMain
        val defaultImg = R.drawable.bg_rectangle_radius_10_off_white

        val width = getItemWidth()/3

        fun bind(item: StoreMainStoreData){
            if(item != null) {
                Log.e("merong", item.toString())
//                fbStorage = FirebaseStorage.getInstance()
//                var storageRef = fbStorage?.reference?.child(item.postImgUrl)
//
//                storageRef?.downloadUrl?.addOnCompleteListener {
                    Glide.with(context!!)
                        .load(item.postImgUrl)
                        .placeholder(defaultImg)
                        .override(width, width)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .centerCrop()
                        .into(FeedImg)
//                }
            }

            itemView.setOnClickListener {
                var intent = Intent(itemView.context, ConsumerStoreDetailFeedActivity::class.java)
                // ???????????????, ?????? ?????? StoreMainStoreData ????????? detailFeed??? ?????????
                intent.putExtra("StoreMainStoreData", item)
                itemView.context.startActivity(intent)
            }
        }

        // display ??? ????????? ?????? ????????? ?????? ?????????
        fun getItemWidth():Int{
            val display = this.context?.resources?.displayMetrics
            val displaywidth = display?.widthPixels
            // ?????? ??? dp??? px??? ????????????
            val density = display?.density
            val margin = (10 * density!! +0.5)*4
            // ???????????? ??? ?????? ?????????
            val width = displaywidth?.minus(margin.toInt())
            return width!!
        }
    }

}
