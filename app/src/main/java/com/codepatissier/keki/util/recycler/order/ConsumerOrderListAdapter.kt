package com.codepatissier.keki.util.recycler.order

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.databinding.ItemOrderListBinding
import com.google.firebase.storage.FirebaseStorage

class ConsumerOrderListAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var consumerOrderListDatas = mutableListOf<ConsumerOrderListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemOrderListBinding.inflate(layoutInflater, parent, false)
        return ConsumerOrderListViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ConsumerOrderListViewHolder).bind(consumerOrderListDatas[position])
    }

    override fun getItemCount(): Int = consumerOrderListDatas.size

    class ConsumerOrderListViewHolder(val context: FragmentActivity?, val binding: ItemOrderListBinding): ViewHolder(binding.root){
        var fbStoreage: FirebaseStorage?= null
        private val storeImage: ImageView = binding.ivOrderListSeller
        private val storeName: TextView = binding.tvOrderStoreName
        private val totalPriceDessertName: TextView = binding.tvOrderPriceProduct
        private val pickUpDate: TextView = binding.tvOrderListDate
        private var orderIdx: Int = 0

        fun bind(item: ConsumerOrderListData){
            orderIdx = item.orderIdx
            storeName.text = item.userName
            totalPriceDessertName.text = item.totalPrice.toString() + " | " + item.dessertName
            pickUpDate.text = item.pickUpDate.substring(0, 10)


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