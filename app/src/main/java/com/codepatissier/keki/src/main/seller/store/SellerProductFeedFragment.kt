package com.codepatissier.keki.src.main.seller.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerProductFeedBinding
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageService
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedService
import com.codepatissier.keki.src.main.consumer.store.productfeed.ConsumerStoreProductFeedView
import com.codepatissier.keki.src.main.consumer.store.productfeed.model.ConsumerStoreProductFeedResponse
import com.codepatissier.keki.src.main.consumer.store.productfeed.productdetail.ConsumerStoreProductDetailFeedActivity
import com.codepatissier.keki.src.main.seller.store.product.SellerProductAddActivity
import com.codepatissier.keki.util.viewpager.storemain.StoreMainProductData
import com.codepatissier.keki.util.viewpager.storemain.consumer.ConsumerStoreMainProductAdapter
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainProductAdapter

class SellerProductFeedFragment(storeIdx : Long) : BaseFragment<FragmentSellerProductFeedBinding>
    (FragmentSellerProductFeedBinding::bind, R.layout.fragment_seller_product_feed),
    ConsumerStoreProductFeedView {

    lateinit var sellerStoreMainProductAdapter: SellerStoreMainProductAdapter
    var cursorIdx : Long = 0
    val storeMainProductDatas = mutableListOf<StoreMainProductData>()
    val storeIdx = storeIdx
    var hasNext = false
    val size = 21
    var positionStart = 0
    var itemSize = 0
    var lastItemVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floatAddClicked()
        showLoadingDialog(requireContext())
    }


    private fun productFeedRecyclerView(response:ConsumerStoreProductFeedResponse){
        sellerStoreMainProductAdapter = SellerStoreMainProductAdapter(requireActivity())
        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext
        binding.recyclerProductFeed.adapter = sellerStoreMainProductAdapter

        for(i in response.result.desserts.indices){
            storeMainProductDatas.apply {
                add(StoreMainProductData(dessertIdx = response.result.desserts[i].dessertIdx, dessertImgUrl = response.result.desserts[i].dessertImgUrl, storeIdx = storeIdx))
            }
        }

        sellerStoreMainProductAdapter.storeMainProductDatas = storeMainProductDatas

        // 스크롤이 바닥에 닿았을 때
        binding.recyclerProductFeed.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // 현재 보이는 마지막 아이템의 position
                var lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                // 전제 아이템 갯수
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                // 마지막 아이템이면 true로 변경
                if( lastVisibleItemPosition != itemTotalCount) {
                    lastItemVisible = true
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // 스크롤이 멈춰있고, 다음 아이템이 있으며 마지막 아이템일 때 api 호출
                if(newState == RecyclerView.SCROLL_STATE_IDLE && hasNext && lastItemVisible){
                    binding.progress.visibility = View.VISIBLE
                    positionStart = storeMainProductDatas.size
                    ConsumerStoreProductFeedService(this@SellerProductFeedFragment).tryGetProductNextFeed(storeIdx, cursorIdx,size)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        storeMainProductDatas.clear()
        ConsumerStoreProductFeedService(this).tryGetProductFeed(storeIdx, size)
    }

    private fun floatAddClicked(){
        binding.floatBtn.setOnClickListener{
            val intent = Intent(context, SellerProductAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onGetProductFeedSuccess(response: ConsumerStoreProductFeedResponse) {
        dismissLoadingDialog()

        productFeedRecyclerView(response)
    }

    override fun onGetProductFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetProductNextFeedSuccess(response: ConsumerStoreProductFeedResponse) {
        binding.progress.visibility = View.GONE

        cursorIdx = response.result.cursorIdx
        hasNext = response.result.hasNext

        for(i in response.result.desserts.indices){
            storeMainProductDatas.apply {
                add(StoreMainProductData(dessertIdx = response.result.desserts[i].dessertIdx, dessertImgUrl = response.result.desserts[i].dessertImgUrl, storeIdx = storeIdx))
            }
        }

        itemSize = storeMainProductDatas.size - positionStart
        sellerStoreMainProductAdapter.notifyItemRangeChanged(positionStart, itemSize)
    }

    override fun onGetProductNextFeedFailure(message: String) {
        binding.progress.visibility = View.GONE
        showCustomToast("오류 : $message")
    }

}