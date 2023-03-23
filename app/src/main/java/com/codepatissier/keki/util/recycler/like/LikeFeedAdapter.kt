package com.codepatissier.keki.util.recycler.like

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemLikeRecyclerBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.DecimalFormat

class LikeFeedAdapter(private val dataList: List<LikeFeedData>, private val context: Context) : RecyclerView.Adapter<LikeFeedAdapter.LikeFeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeFeedViewHolder {
        val itemBinding = ItemLikeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeFeedViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LikeFeedViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int = dataList.size

    class LikeFeedViewHolder(private val itemBinding: ItemLikeRecyclerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var fbStorage : FirebaseStorage?= null
        fun bind(item: LikeFeedData, context: Context) {
            val width = getItemWidth(context)/3
            if (item.postImgUrl.startsWith("http")){
                Glide.with(context!!)
                    .load(item.postImgUrl)
                    .override(width, width)
                    .centerCrop()
                    .into(itemBinding.iv)
            }else {
                fbStorage = FirebaseStorage.getInstance()
                var storageRef = fbStorage?.reference?.child(item.postImgUrl)

                storageRef?.downloadUrl?.addOnCompleteListener {
                    Glide.with(context!!)
                        .load(item.postImgUrl)
                        .centerCrop()
                        .override(width, width)
                        .into(itemBinding.iv)
                }
            }
            itemBinding.tvProductName.text = item.productName.replace(" ", "\u00A0")
            itemBinding.tvProductPrice.text = DecimalFormat("###,###").format(item.productPrice.toLong())
        }

        // display 별 화면에 맞는 그리드 크기 구하기
        fun getItemWidth(context: Context):Int{
            val display = context.resources?.displayMetrics
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