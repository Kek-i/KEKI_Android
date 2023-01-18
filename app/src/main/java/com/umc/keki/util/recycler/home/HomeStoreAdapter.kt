package com.umc.keki.util.recycler.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.keki.databinding.ItemHomeStoreRecyclerBinding
import com.umc.keki.src.storeMain.StoreMainActivity


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

        private val img: ImageView = binding.ivHomeStore
        private val name: TextView = binding.tvHomeStore
        private val cardView: CardView = binding.cv

        fun bind(item: HomeStoreData){
            name.text = item.name

            /*
            Glide.with(context!!)
                .load(item.img)
                .centerCrop()
                .into(img)

             */

            cardView.bringToFront()

            img.clipToOutline = true

            itemView.setOnClickListener{
                val intent = Intent(context, StoreMainActivity::class.java)
                intent.run{context?.startActivity(intent)}
            }
        }
    }


}