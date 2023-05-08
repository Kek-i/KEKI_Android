package com.codepatissier.keki.util.recycler.order

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemOrderListBinding
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
        private val priceProduct: TextView = binding.tvOrderPriceProduct
        private val orderDate: TextView = binding.tvOrderListDate

        fun bind(item: SellerOrderListData){
            itemView.setOnClickListener {
                /*
                val intent = Intent(itemView.context, )
                intent.putExtra("orderIdx", item.orderIdx)
                intent.run{itemView.context?.startActivity(intent)}
                 */
            }
        }
    }

}