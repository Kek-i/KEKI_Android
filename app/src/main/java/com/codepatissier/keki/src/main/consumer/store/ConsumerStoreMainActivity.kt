package com.codepatissier.keki.src.main.consumer.store


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerStoreMainBinding
import com.codepatissier.keki.src.main.consumer.store.model.ConsumerStoreMainResponse
import com.codepatissier.keki.src.main.consumer.store.order.ConsumerOrderActivity
import com.codepatissier.keki.src.main.consumer.store.order.list.ConsumerOrderListActivity
import com.codepatissier.keki.util.viewpager.storemain.consumer.ConsumerStoreMainDialog
import com.codepatissier.keki.util.viewpager.storemain.consumer.ConsumerStoreMainTabAdapter
import com.google.firebase.storage.FirebaseStorage

class ConsumerStoreMainActivity : BaseActivity<ActivityConsumerStoreMainBinding>(ActivityConsumerStoreMainBinding::inflate),
    ConsumerStoreMainView{
    var storeIdx: Long = 1
    var fbStorage : FirebaseStorage?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()

        getStoreIdx()
        tabSetting()
        back()
        infoClick()
        showLoadingDialog(this)
        ConsumerStoreMainService(this).tryGetStoreMain(storeIdx)
    }

    private fun getStoreIdx(){
        storeIdx = intent.getLongExtra("storeIdx", 1)
        Log.d("storeIdx", storeIdx.toString())
    }

    private fun tabSetting(){
        val consumerStoreMainTabAdapter = ConsumerStoreMainTabAdapter(this, storeIdx)
        binding.vpStore.adapter = consumerStoreMainTabAdapter

        val tabIconArray = arrayOf(R.drawable.ic_store_main_grid, R.drawable.ic_store_main_cake)

        TabLayoutMediator(binding.tabStore, binding.vpStore){tab, position ->
            tab.setIcon(tabIconArray[position])
        }.attach()

        tabClickColor()
    }

    private fun tabClickColor(){
        binding.tabStore.getTabAt(0)?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))

        binding.tabStore.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.my_page_line,null))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))
            }
        })
    }

    private fun back(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun infoClick(){
        binding.ivInfo.setOnClickListener{
            ConsumerStoreMainDialog(this).setStoreIdx(storeIdx)
            ConsumerStoreMainDialog(this).show()
        }
    }

    override fun onGetStoreMainSuccess(response: ConsumerStoreMainResponse) {
        binding.tvStoreName.text = response.result.nickname
        binding.tvStoreDetail.text = response.result.introduction

        val defaultImg = R.drawable.ic_seller
        val imageView = binding.ivProfile
//        val uri = "http://"+response.result.orderUrl

        if(!response.result.storeImgUrl.isNullOrEmpty()) {
            if (response.result.storeImgUrl!!.startsWith("http")){
                Glide.with(this)
                    .load(response.result.storeImgUrl)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .circleCrop()
                    .into(imageView)
                dismissLoadingDialog()
            } else {
                // 프로필 이미지 띄우기
                var storageRef = fbStorage?.reference?.child(response.result.storeImgUrl)
                storageRef?.downloadUrl?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Glide.with(this)
                            .load(it.result)
                            .placeholder(defaultImg)
                            .error(defaultImg)
                            .fallback(defaultImg)
                            .circleCrop()
                            .into(imageView)
                        dismissLoadingDialog()
                    }else{
                        dismissLoadingDialog()
                    }
                }
            }
        }else{
            dismissLoadingDialog()
        }
        setViewMore(binding.tvStoreDetail, binding.tvViewMore)

        // 버튼 클릭시 주문링크로 이동
        binding.tvOrder.setOnClickListener{
            //var intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            // 주문하기
            var intent = Intent(this, ConsumerOrderActivity::class.java)
            intent.putExtra("storeIdx", storeIdx)
            startActivity(intent)
        }

    }

    override fun onGetStoreMainFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun setViewMore(contentTextView: TextView, viewMoreTextView:TextView){
        contentTextView.post{
            val lineCount = contentTextView.layout.lineCount
            if (lineCount > 0) {
                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
                    viewMoreTextView.visibility = View.VISIBLE

                    viewMoreTextView.setOnClickListener {
                        contentTextView.maxLines = Int.MAX_VALUE
                        viewMoreTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}



