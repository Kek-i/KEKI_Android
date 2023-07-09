package com.codepatissier.keki.util.recycler.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemOrderImgRecyclerBinding
import com.google.firebase.storage.FirebaseStorage

class OrderImgListAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var orderImgListDatas = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemOrderImgRecyclerBinding.inflate(layoutInflater, parent, false)
        return OrderImgListViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as OrderImgListViewHolder).bind(orderImgListDatas[position])
    }

    override fun getItemCount(): Int = orderImgListDatas.size

    class OrderImgListViewHolder(val context: FragmentActivity?, val binding: ItemOrderImgRecyclerBinding): ViewHolder(binding.root){
        var fbStorage: FirebaseStorage?= null

        fun bind(item: String){
            if (item.startsWith("http")){
                Glide.with(context!!)
                    .load(item)
                    .centerCrop()
                    .into(binding.ivRequestImg)
            }else {
                fbStorage = FirebaseStorage.getInstance()
                var storageRef = fbStorage?.reference?.child(item)

                storageRef?.downloadUrl?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Glide.with(context!!)
                            .load(it.result)
                            .centerCrop()
                            .into(binding.ivRequestImg)
                    }
                }
            }
        }
    }

}