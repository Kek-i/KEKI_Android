package com.codepatissier.keki.src.main.seller.store.storefeed

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedAddBinding
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameData

class SellerStoreFeedAddActivity : BaseActivity<ActivitySellerStoreFeedAddBinding>(
    ActivitySellerStoreFeedAddBinding::inflate) {
    // 상품 선택 메뉴 open/close 여부
    var isOpenProductSelectionLayout: Boolean = false
    // recyclerview adapter & datalist
    private lateinit var productNameAdapter: ProductNameAdapter
    private val productNameDataList = mutableListOf<ProductNameData>()
    // 서버 연동한 후에는 mutableList 말고 그냥 list 쓰기 (수정불가능하게)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setProductNameRecyclerView()
        setListenerToProductSelectionLayout()
    }

    private fun setListenerToProductSelectionLayout() {
        binding.layoutCloseProduct.setOnClickListener {
            // 등록된 상품이 없을 경우(리스트가 비어있으면) 안 열리고 밑에 에러문구 띄우기(상품 등록 먼저)
            if (productNameDataList.size == 0) {

            }
            // 선택 메뉴 open 되어 있을 때
            else if (isOpenProductSelectionLayout) {
                binding.layoutOpenProduct.visibility = GONE
                isOpenProductSelectionLayout = false
            }
            // close 되어 있을 때
            else {
                binding.layoutOpenProduct.visibility = VISIBLE
                isOpenProductSelectionLayout = true
            }
        }
    }

    private fun setProductNameRecyclerView() {
        // 임시 데이터
        productNameDataList.apply {
            add(ProductNameData(1, "케이크 1호"))
            add(ProductNameData(2, "케이크 2호"))
            add(ProductNameData(3, "미니"))
            add(ProductNameData(4, "도시락"))
        }
        productNameAdapter = ProductNameAdapter(productNameDataList)
        productNameAdapter.setItemClickListener(object : ProductNameAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                binding.tvSelectProduct.text = productNameDataList[position].productName
                binding.layoutOpenProduct.visibility = GONE
                isOpenProductSelectionLayout = false
            }
        })
        binding.rvProduct.adapter = productNameAdapter
    }
}