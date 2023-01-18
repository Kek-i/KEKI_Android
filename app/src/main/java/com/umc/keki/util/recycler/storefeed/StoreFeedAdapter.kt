package com.umc.keki.util.recycler.storefeed

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.umc.keki.R
import com.umc.keki.databinding.ItemStoreFeedRecyclerBinding
import com.umc.keki.src.main.consumer.store.DetailImageAdapter

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
                 //var intent = Intent(itemView.context, StoreFeedReportDialogActivity::class.java)
                 //itemView.context.startActivity(intent)

                binding.tvStoreFeedReport.isVisible = true

                /*
                val builder = AlertDialog.Builder(context)
                builder.setTitle("신고하기")
                builder.show()

                 */
            }
        }

    }
}