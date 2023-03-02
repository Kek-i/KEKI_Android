package com.codepatissier.keki.src.main.seller.store.productfeed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivitySellerProductDetailFeedBinding
import com.codepatissier.keki.src.main.auth.profilesetting.SellerProfileSettingActivity
import com.codepatissier.keki.src.main.seller.store.product.SellerProductAddActivity
import com.codepatissier.keki.src.main.seller.store.product.SellerProductEditActivity
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerProductDetailFeedDeleteDialog
import com.google.firebase.storage.FirebaseStorage


class SellerProductDetailFeedActivity : BaseActivity<ActivitySellerProductDetailFeedBinding> (ActivitySellerProductDetailFeedBinding::inflate),
    SellerProductFeedDetailView {
    var fbStorage : FirebaseStorage?= null
    private var dessertIdx : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStoreMain()
        selectMenu()
        getDessertIdx()
    }

    //스토어 메인 피드에서 넘어올때 dessertIdx 받아와서 디테일 피드 api 호출
    private fun getDessertIdx(){
        dessertIdx = intent.getStringExtra("dessertIdx").toString()
        if (dessertIdx != null) {
            SellerProductFeedDetailService(this).tryGetProductFeedDetail(dessertIdx = dessertIdx!!.toLong())
            ApplicationClass.userInfo.putString(ApplicationClass.DessertIdx, dessertIdx)
            ApplicationClass.userInfo.commit()
        }
        Log.d("디저트 id체크", "디저트idx: $dessertIdx")
    }

    private fun navigateToStoreMain(){
        binding.ivProductFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

    private fun selectMenu(){
        binding.ivProductFeedSetting.setOnClickListener{
            showMenu()
        }
    }

    private fun showMenu() {
        var popupMenu = PopupMenu(applicationContext, binding.ivProductFeedSetting)
        menuInflater?.inflate(R.menu.seller_product_detail_feed_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.seller_product_detail_feed_menu1 -> {
                    val intent = Intent(this, SellerProductEditActivity::class.java)
                    intent.putExtra("dessertIdx", dessertIdx)
                    startActivity(intent)
                    finish()
                    return@setOnMenuItemClickListener true
                }
                R.id.seller_product_detail_feed_menu2 -> {
                    SellerProductDetailFeedDeleteDialog(this).show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }

    override fun onGetProductFeedSuccess(response: SellerProductFeedDetailResponse) {
        getFeedView(response)
    }

    override fun onGetProductFeedFailure(message: String) {
        Log.d("오류", message)
    }

    override fun onPatchProductFeedSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPatchProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDelProductFeedSuccess(response: BaseResponse) {
        this@SellerProductDetailFeedActivity.finish()
    }

    override fun onDelProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    private fun getFeedView(response: SellerProductFeedDetailResponse){
        binding.tvProductFeedId.text = response.result.nickname
        binding.tvProductFeedCakeName.text = response.result.dessertName
        binding.tvProductFeedCakePrice.text = response.result.dessertPrice.toString() + "원"
        binding.tvProductFeedCakeDetail.text = response.result.dessertDescription

        Log.d("item체크", "아이템 값:${response.result}")

        //이미지개수 크기의 리스트 생성
//        var img = arrayOfNulls<String>(response.result.images.size)
//
//        for(i in response.result.images.indices) {
//            img[i] = response.result.images[i].postImgUrl
//
//        }
//
//        val pagerAdapter = DetailImageAdapter(this@SellerProductDetailFeedActivity!!, img)
//        binding.vpProductFeedImg.adapter = pagerAdapter
//        binding.wormDotsIndicator.setViewPager2(binding.vpProductFeedImg)


    }


}