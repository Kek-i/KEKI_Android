package com.codepatissier.keki.util.recycler.order

import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemOrderListBinding
import com.codepatissier.keki.src.main.seller.mypage.orderInformation.SellerOrderInformationActivity
import com.google.firebase.storage.FirebaseStorage

class SellerOrderListAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var sellerOrderListDatas = mutableListOf<SellerOrderListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemOrderListBinding.inflate(layoutInflater, parent, false)
        return SellerOrderListViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SellerOrderListViewHolder).bind(sellerOrderListDatas[position])
    }

    override fun getItemCount(): Int = sellerOrderListDatas.size

    class SellerOrderListViewHolder(val context: FragmentActivity?, val binding: ItemOrderListBinding): ViewHolder(binding.root){
        var fbStoreage: FirebaseStorage?= null
        private val storeImage: ImageView = binding.ivOrderListSeller
        private val storeName: TextView = binding.tvOrderStoreName
        private val totalPriceDessertName: TextView = binding.tvOrderPriceProduct
        private val pickUpDate: TextView = binding.tvOrderListDate
        private var orderIdx: Int = 0

        fun bind(item: SellerOrderListData){
            orderIdx = item.orderIdx
            storeName.text = item.userName
            totalPriceDessertName.text = item.totalPrice.toString() + " | " + item.dessertName
            pickUpDate.text = item.pickUpDate.substring(0, 10)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SellerOrderInformationActivity::class.java)
                intent.putExtra("orderIdx", item.orderIdx)
                intent.run{itemView.context?.startActivity(intent)}

            }
        }
    }

}