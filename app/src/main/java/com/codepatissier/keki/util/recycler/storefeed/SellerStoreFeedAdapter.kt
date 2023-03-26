package com.codepatissier.keki.util.recycler.storefeed

import android.content.Intent
import android.util.Log
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
import com.codepatissier.keki.databinding.ItemSellerStoreFeedRecyclerBinding
import com.codepatissier.keki.src.main.consumer.calendar.calendaradd.ConsumerCalendarAddActivity
import com.codepatissier.keki.src.main.consumer.calendar.calendardetail.ConsumerCalendarDetailActivity
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.seller.store.storefeed.storedetail.SellerStoreFeedDetailImageAdapter
import com.codepatissier.keki.src.main.seller.store.storefeed.storedetail.delete.SellerStoreDetailFeedDeleteDialog
import com.codepatissier.keki.src.main.seller.store.storefeed.storeedit.SellerStoreFeedEditActivity
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
        private var storeIdx: Long? = null
        var fbStorage : FirebaseStorage?= null
        val defaultImg = R.drawable.ic_seller

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
            storeIdx = item.storeIdx

            val width = getItemWidth()/3

            for(i in item.tags.indices){
                tagArray[i].isVisible = true
                tagArray[i].text = "# " + item.tags[i]
            }

            if(item.storeProfileImg != null){
                if (item.storeProfileImg.startsWith("http")){
                    Glide.with(context!!)
                        .load(item.storeProfileImg)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .override(width, width)
                        .centerCrop()
                        .into(sellerImg)
                }else {
                    fbStorage = FirebaseStorage.getInstance()
                    var storageRef = fbStorage?.reference?.child(item.storeProfileImg)

                    storageRef?.downloadUrl?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            Glide.with(context!!)
                                .load(it.result)
                                .placeholder(defaultImg)
                                .error(defaultImg)
                                .fallback(defaultImg)
                                .override(width, width)
                                .centerCrop()
                                .into(sellerImg)
                        }
                    }
                }

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
            setting()
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
                intent.putExtra("storeIdx", storeIdx)
                intent.putExtra("nickname", binding.tvStoreFeedSellerNickname.text)
                itemView.context.startActivity(intent)
            }
        }

        // 피드 수정, 삭제
        private fun setting(){
            binding.ivStoreFeedDelete.setOnClickListener {
                var popupMenu = PopupMenu(context, it)
                popupMenu.menuInflater?.inflate(R.menu.popup_menu_delete_seller_store_feed_detail, popupMenu.menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.popup_modify -> {
                            // 수정하기 activity로 이동
                            val intent = Intent(context, SellerStoreFeedEditActivity::class.java)
                            intent.putExtra("postIdx", postIdx)
                            context?.startActivity(intent)
                            return@setOnMenuItemClickListener false
                        }R.id.popup_delete -> {
                            val deleteDialog = SellerStoreDetailFeedDeleteDialog(context!!)
                            deleteDialog.postIdx = postIdx
                            deleteDialog.show()
                            return@setOnMenuItemClickListener true
                        }else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            }
        }

    }

}