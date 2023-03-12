package com.codepatissier.keki.util.recycler.storefeed

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codepatissier.keki.databinding.ItemProgressbarLoadingBinding
import com.codepatissier.keki.databinding.ItemSellerStoreFeedRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.seller.store.storefeed.SellerStoreFeedDetailImageAdapter
import com.google.firebase.storage.FirebaseStorage

class SellerStoreFeedAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {
    var storeFeedDatas = mutableListOf<StoreFeedData>()
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType){
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemSellerStoreFeedRecyclerBinding.inflate(layoutInflater, parent, false)
                SellerStoreFeedViewHolder(context, itemBinding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemProgressbarLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is SellerStoreFeedViewHolder){
            (holder as SellerStoreFeedViewHolder).bind(storeFeedDatas[position])
        }else{

        }
    }

    override fun getItemCount(): Int = storeFeedDatas.size

    class LoadingViewHolder(private val binding: ItemProgressbarLoadingBinding): RecyclerView.ViewHolder(binding.root){

    }

    // 뷰 타입 정하기
    override fun getItemViewType(position: Int): Int {
        return if (storeFeedDatas[position] != null){
            VIEW_TYPE_ITEM
        }else{
            VIEW_TYPE_LOADING
        }
    }

    fun setList(storeFeed: MutableList<StoreFeedData>, hasNext: Boolean){
        storeFeedDatas.addAll(storeFeed)

    }

    class SellerStoreFeedViewHolder(val context: FragmentActivity?, val binding: ItemSellerStoreFeedRecyclerBinding): ViewHolder(binding.root){
        private val sellerImg: ImageView = binding.ivStoreFeedSeller
        private val nickname: TextView = binding.tvStoreFeedSellerNickname
        private val cakeName: TextView = binding.tvStoreFeedCakeName
        private val cakeDescription: TextView = binding.tvStoreFeedCakeDescription
        private val firstTag: TextView = binding.tvStoreFeedFirstTag
        private val secondTag: TextView = binding.tvStoreFeedSecondTag
        private val thirdTag: TextView = binding.tvStoreFeedThirdTag
        private val tagArray = arrayOf(firstTag, secondTag, thirdTag)
        private var postIdx : Long? = null
        var fbStorage : FirebaseStorage?= null

        // display 별 화면에 맞는 그리드 크기 구하기
        private fun getItemWidth():Int{
            val display = this.context?.resources?.displayMetrics
            val displaywidth = display?.widthPixels

            return displaywidth!!
        }

        fun bind(item: StoreFeedData){
            nickname.text = item.storeName
            cakeName.text = item.dessertName
            postIdx = item.postIdx

            val width = getItemWidth();

            for(i in item.tags.indices){
                tagArray[i].isVisible = true
                tagArray[i].text = "# " + item.tags[i]
            }

            if(item.storeProfileImg?.isNotEmpty() == true){
                Glide.with(context!!)
                    .load(item.storeProfileImg)
                    .override(width,width)
                    .centerCrop()
                    .into(sellerImg)
                sellerImg.clipToOutline = true
            }

            var img = arrayOfNulls<String>(item.postImgUrls.size)

            for(i in item.postImgUrls.indices){
                img[i] = item.postImgUrls[i]
            }


            val pagerAdapter = SellerStoreFeedDetailImageAdapter(context!!, img)
            binding.vpStoreFeedImg.adapter = pagerAdapter
            binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)


            checkCakeDescription(item.description)
            seeMoreDescription(item.description)
            navigateToStoreMain()
        }

        // 제품 내용 길이 확인
        private fun checkCakeDescription(description: String){
            if(description.length > 20){
                cakeDescription.text = description.substring(0, 20) + " ∙∙∙ 더보기"
            }else
                cakeDescription.text = description
        }

        // 더보기
        private fun seeMoreDescription(description: String){
            binding.tvStoreFeedCakeDescription.setOnClickListener {
                cakeDescription.text = description
            }
        }

        private fun navigateToStoreMain(){
            binding.tvStoreFeedSellerNickname.setOnClickListener {
                val intent = Intent(itemView.context, ConsumerStoreMainActivity::class.java)
                intent.putExtra("nickname", binding.tvStoreFeedSellerNickname.text)
                itemView.context.startActivity(intent)
            }
        }

    }
}