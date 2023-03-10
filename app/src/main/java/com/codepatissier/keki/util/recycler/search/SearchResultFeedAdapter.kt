package com.codepatissier.keki.util.recycler.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemProgressbarLoadingBinding
import com.codepatissier.keki.databinding.ItemStoreFeedRecyclerBinding
import com.codepatissier.keki.src.main.consumer.search.searchresult.SearchResultFeedActivity
import com.codepatissier.keki.src.main.consumer.search.searchresult.SearchResultService
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.Feeds
import com.codepatissier.keki.src.main.consumer.search.searchresult.model.SearchResult
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.ConsumerStoreDetailFeedActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.report.ConsumerStoreDetailFeedDialog
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter
import com.google.firebase.storage.FirebaseStorage


class SearchResultFeedAdapter(var searchResult: SearchResult, val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType){
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemStoreFeedRecyclerBinding.inflate(layoutInflater, parent, false)
                StoreFeedViewHolder(context, itemBinding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ItemProgressbarLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(itemBinding)
            }
        };
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is StoreFeedViewHolder){
            (holder).bind(searchResult.feeds[position])
        }
    }

    override fun getItemCount(): Int = searchResult.feeds.size



    class StoreFeedViewHolder(val context: FragmentActivity?, val binding: ItemStoreFeedRecyclerBinding): ViewHolder(binding.root){
        var fbStorage : FirebaseStorage?= null
        private val sellerImg: ImageView = binding.ivStoreFeedSeller
        private val nickname: TextView = binding.tvStoreFeedSellerNickname
        private val cakeName: TextView = binding.tvStoreFeedCakeName
        private val cakeDescription: TextView = binding.tvStoreFeedCakeDescription
        private val firstTag: TextView = binding.tvStoreFeedFirstTag
        private val secondTag: TextView = binding.tvStoreFeedSecondTag
        private val thirdTag: TextView = binding.tvStoreFeedThirdTag
        private val tagArray = arrayOf(firstTag, secondTag, thirdTag)
        private var heart = false
        private var postIdx : Long? = null

        fun bind(item: Feeds){
            nickname.text = item.storeName
            cakeName.text = item.dessertName
            postIdx = item.postIdx

            for(i in item.tags.indices){
                tagArray[i].isVisible = true
                tagArray[i].text = "# " + item.tags[i]
            }

            Glide.with(context!!)
                .load(item.storeProfileImg)
                .centerCrop()
                .into(sellerImg)

            
            var img = arrayOfNulls<String>(item.postImgUrls.size)

            for(i in item.postImgUrls.indices){
                img[i] = item.postImgUrls[i]
            }


            val pagerAdapter = DetailImageAdapter(context!!, img)
            binding.vpStoreFeedImg.adapter = pagerAdapter
            binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)

            if(item.like){
                binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_on)
                heart = true
            }

            checkCakeDescription(item.description)
            seeMoreDescription(item.description)
            likeProduct()
            report()
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

        // 찜하기
        private fun likeProduct(){
            binding.ivStoreFeedHeartOff.setOnClickListener {
                if(!heart){ // 찜
                    binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_on)
                    heart = true
                }else{ // 해제
                    binding.ivStoreFeedHeartOff.setImageResource(R.drawable.ic_bottom_heart_off)
                    heart = false
                }
                SearchResultFeedActivity().postLike(postIdx!!)
            }

        }


        // 신고하기
        private fun report(){
            binding.ivStoreFeedReport.setOnClickListener {
                var popupMenu = PopupMenu(context, it)
                popupMenu.menuInflater?.inflate(R.menu.popup_menu_report_consumer_store_detail_feed, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.popup_report -> {
                            val reportDialog = ConsumerStoreDetailFeedDialog(context!!)
                            reportDialog.postIdx = postIdx // postIdx 값 전달
                            reportDialog.show()
                            return@setOnMenuItemClickListener true
                        }else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }


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

    class LoadingViewHolder(private val binding: ItemProgressbarLoadingBinding): RecyclerView.ViewHolder(binding.root){

    }

    // 뷰 타입 정하기
    override fun getItemViewType(position: Int): Int {
        return when (searchResult.feeds[position].dessertName){
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }
}