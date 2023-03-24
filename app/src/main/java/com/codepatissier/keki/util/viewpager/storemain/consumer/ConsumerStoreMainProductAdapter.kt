package com.codepatissier.keki.util.viewpager.storemain.consumer


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreMainRecyclerBinding
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductData
import com.google.firebase.storage.FirebaseStorage

class ConsumerStoreMainProductAdapter(val context: FragmentActivity?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var storeMainProductDatas = mutableListOf<StoreMainProductData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStoreMainRecyclerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(storeMainProductDatas[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int = storeMainProductDatas.size

    class ViewHolder(val context: FragmentActivity?, val binding: ItemStoreMainRecyclerBinding): RecyclerView.ViewHolder(binding.root){
        var fbStorage : FirebaseStorage?= null
        private val FeedImg : ImageView = binding.ivStoreMain
        val defaultImg = R.drawable.bg_rectangle_radius_10_off_white

        val width = getItemWidth()/3

        fun bind(item: StoreMainProductData){
            if (item.dessertImgUrl.startsWith("http")) {
                Glide.with(context!!)
                    .load(item.dessertImgUrl)
                    .placeholder(defaultImg)
                    .override(width, width)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .centerCrop()
                    .into(FeedImg)
            } else {

                fbStorage = FirebaseStorage.getInstance()
                var storageRef = fbStorage?.reference?.child(item.dessertImgUrl)

                storageRef?.downloadUrl?.addOnCompleteListener {
                    Glide.with(context!!)
                        .load(item.dessertImgUrl)
                        .placeholder(defaultImg)
                        .override(width, width)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .centerCrop()
                        .into(FeedImg)
                }
            }
        }

        // display 별 화면에 맞는 그리드 크기 구하기
        fun getItemWidth():Int{
            val display = this.context?.resources?.displayMetrics
            val displaywidth = display?.widthPixels
            // 마진 값 dp를 px로 변경하기
            val density = display?.density
            val margin = (10 * density!! +0.5)*4
            // 마진값을 뺀 길이 구하기
            val width = displaywidth?.minus(margin.toInt())
            return width!!
        }
    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

}
