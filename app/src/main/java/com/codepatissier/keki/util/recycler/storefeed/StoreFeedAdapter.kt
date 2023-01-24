package com.codepatissier.keki.util.recycler.storefeed

import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codepatissier.keki.R
import com.codepatissier.keki.databinding.ItemStoreFeedRecyclerBinding
import com.codepatissier.keki.src.main.consumer.store.ConsumerStoreMainActivity
import com.codepatissier.keki.src.main.consumer.store.storefeed.report.ConsumerStoreDetailFeedDialog
import com.codepatissier.keki.src.main.consumer.store.storefeed.DetailImageAdapter

class StoreFeedAdapter(val context: FragmentActivity?): RecyclerView.Adapter<ViewHolder>() {

    var storeFeedDatas = mutableListOf<StoreFeedData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemStoreFeedRecyclerBinding.inflate(layoutInflater, parent, false)
        return StoreFeedViewHolder(context, itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as StoreFeedViewHolder).bind(storeFeedDatas[position])
    }

    override fun getItemCount(): Int = storeFeedDatas.size

    class StoreFeedViewHolder(val context: FragmentActivity?, val binding: ItemStoreFeedRecyclerBinding): ViewHolder(binding.root){
        private val sellerImg: ImageView = binding.ivStoreFeedSeller
        private val nickname: TextView = binding.tvStoreFeedSellerNickname
        private val cakeName: TextView = binding.tvStoreFeedCakeName
        private val cakeDescription: TextView = binding.tvStoreFeedCakeDescription
        private val firstTag: TextView = binding.tvStoreFeedFirstTag
        private val secondTag: TextView = binding.tvStoreFeedSecondTag
        private val thirdTag: TextView = binding.tvStoreFeedThirdTag
        private var heart = false

        fun bind(item: StoreFeedData){
            nickname.text = item.nickname


            // 나중에 데이터 구조 보고 변경 - ConsumerStoreFeedActivity
            var img = arrayOfNulls<Drawable>(2)

            img[0] = context?.getDrawable(R.drawable.ex_cake)
            img[1] = context?.getDrawable(R.drawable.ex_cake)


            val pagerAdapter = DetailImageAdapter(context!!, img)
            binding.vpStoreFeedImg.adapter = pagerAdapter
            binding.wormDotsIndicator.setViewPager2(binding.vpStoreFeedImg)

            checkCakeDescription("이 제품은 어쩌구\n케이크어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구")
            seeMoreDescription("이 제품은 어쩌구\n케이크어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구어쩌구저쩌구")
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
                            Log.d("click", "report")
                            // 이후에 해당 게시물 신고되는거 맞는지 확인하기
                            ConsumerStoreDetailFeedDialog(context!!).show()

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
}