package com.codepatissier.keki.util.recycler.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemSearchListRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.searchresult.ConsumerSearchActivity
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.Feeds
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResult
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat

class SearchListAdapter(var searchListData: SearchResult, val context: ConsumerSearchActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemSearchListRecyclerBinding.inflate(layoutInflater, parent, false)
        return SearchListHolder(context, itemBinding)
    }

    override fun getItemCount(): Int = searchListData.feeds.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SearchListHolder).bind(searchListData.feeds[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    class SearchListHolder(val context: ConsumerSearchActivity, binding: ItemSearchListRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var fbStorage : FirebaseStorage?= null
        private val cakeImg: ImageView = binding.ivGridImg
        private val cakeName: TextView = binding.tvGridName
        private val cakePrice: TextView = binding.tvGridPrice

        fun bind(item: Feeds) {
            val width = getItemWidth()/3

//            fbStorage = FirebaseStorage.getInstance()
//            var storageRef = fbStorage?.reference?.child(item.postImgUrls[0])
//
//            storageRef?.downloadUrl?.addOnCompleteListener {
                Glide.with(context!!)
                    .load(item.postImgUrls[0])
                    .centerCrop()
                    .override(width,width)
                    .into(cakeImg)
//            }
            cakeName.text = item.dessertName
            cakePrice.text = DecimalFormat("###,###").format(item.dessertPrice.toLong())
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
    // (2) ????????? ???????????????
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) ???????????? ?????? ??? ????????? ??????
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener??? ????????? ?????? ??????
    private lateinit var itemClickListener : OnItemClickListener


}