package com.codepatissier.keki.util.recycler.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ActivityConsumerOneFeedDetailBinding
import com.codepatissier.keki.databinding.ItemHomeStoreRecyclerBinding
import com.codepatissier.keki.src.main.consumer.home.onefeed.ConsumerOneFeedDetailActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.google.firebase.storage.FirebaseStorage


class HomeStoreAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>(){

    var homeStoreDatas = mutableListOf<HomeStoreData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemHomeStoreRecyclerBinding.inflate(layoutInflater, parent, false)
        return HomeStoreViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as HomeStoreViewHolder).bind(homeStoreDatas[position])
    }

    override fun getItemCount(): Int = homeStoreDatas.size

    class HomeStoreViewHolder(val context: FragmentActivity?, val binding: ItemHomeStoreRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        var fbStorage : FirebaseStorage?= null
        private val img: ImageView = binding.ivHomeStore
        private val name: TextView = binding.tvHomeStore
        private val cardView: CardView = binding.cv

        fun bind(item: HomeStoreData){
            name.text = item.name

//            if(item.img != null) {
//                fbStorage = FirebaseStorage.getInstance()
//                var storageRef = fbStorage?.reference?.child(item.img)
//
//                storageRef?.downloadUrl?.addOnCompleteListener {
                    Glide.with(context!!)
                        .load(item.img)
                        .centerCrop()
                        .into(img)
//                }
//            }

            cardView.bringToFront()

            img.clipToOutline = true

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, ConsumerOneFeedDetailActivity::class.java)
                intent.putExtra("postIdx", item.postIdx)
                intent.run { itemView.context?.startActivity(intent) }
            }
        }
    }


}