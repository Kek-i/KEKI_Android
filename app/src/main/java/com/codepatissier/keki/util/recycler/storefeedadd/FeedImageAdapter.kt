package com.codepatissier.keki.util.recycler.storefeedadd

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemFeedImageRecyclerBinding

class FeedImageAdapter(val dataList: MutableList<Uri>, private val context: Context)
    : RecyclerView.Adapter<FeedImageAdapter.FeedImageViewHolder>() {
    private lateinit var imgDeleteBtnClickListener: ImgDeleteBtnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedImageViewHolder {
        val itemBinding = ItemFeedImageRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FeedImageViewHolder, position: Int) {
        holder.bind(dataList[position], context)
        holder.setListenerToDeleteImage(position, imgDeleteBtnClickListener)
    }

    override fun getItemCount(): Int = dataList.size

    fun setItemClickListener(imgDeleteBtnClickListener: ImgDeleteBtnClickListener) {
        this.imgDeleteBtnClickListener = imgDeleteBtnClickListener
    }

    class FeedImageViewHolder(private val itemBinding: ItemFeedImageRecyclerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(uri: Uri, context: Context) {
            Glide.with(context)
                .load(uri)
                .centerCrop()
                .into(itemBinding.ivFeedImg)
        }

        fun setListenerToDeleteImage(position: Int, imgDeleteBtnClickListener: ImgDeleteBtnClickListener
        ) {
            itemBinding.ivDelete.setOnClickListener {
                imgDeleteBtnClickListener.onClickDeleteBtn(position)
            }
        }
    }

    interface ImgDeleteBtnClickListener {
        fun onClickDeleteBtn(position: Int)
    }
}
