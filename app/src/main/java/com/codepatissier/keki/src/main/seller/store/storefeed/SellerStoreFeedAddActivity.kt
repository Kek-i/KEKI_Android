package com.codepatissier.keki.src.main.seller.store.storefeed

import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedAddBinding
import com.codepatissier.keki.util.recycler.storefeedadd.FeedImageAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameData
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType

class SellerStoreFeedAddActivity : BaseActivity<ActivitySellerStoreFeedAddBinding>(
    ActivitySellerStoreFeedAddBinding::inflate) {
    // 첨부 가능한 피드 이미지 최대 개수
    private val maxImage = 5
    // 상품 선택 메뉴 open/close 여부
    private var isOpenProductSelectionLayout: Boolean = false
    // recyclerview adapter & datalist
    private lateinit var feedImageAdapter: FeedImageAdapter
    private var feedImageUriList = mutableListOf<Uri>()
    private lateinit var productNameAdapter: ProductNameAdapter
    private val productNameDataList = mutableListOf<ProductNameData>()
    // 서버 연동한 후에는 mutableList 말고 그냥 list 쓰기 (수정불가능하게)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setListenerToBackBtn()
        initFeedImage()
        setProductNameRecyclerView()
        setListenerToProductSelectionLayout()
    }

    private fun initFeedImage() {
        // recyclerview item 삭제 시 깜빡임 현상 방지
        binding.rvFeedImage.itemAnimator = null

        binding.ibAddImage.setOnClickListener {
            if(maxImage <= feedImageUriList.size) {
                showCustomToast(getString(R.string.seller_feed_add_max_image))
            } else {
                val max = maxImage - feedImageUriList.size

                TedImagePicker.with(this)
                    .mediaType(MediaType.IMAGE)
                    .showCameraTile(false)
                    .title(getString(R.string.seller_feed_add_image_picker_title))
                    .buttonTextColor(R.color.black)
                    .buttonBackground(R.color.white)
                    .max(max, "최대 ${max}개 선택할 수 있습니다.")
                    .startMultiImage { uriList ->
                        if(uriList.isNotEmpty()) {
                            for (uri in uriList) {
                                feedImageUriList.add(uri)
                            }
                            feedImageAdapter = FeedImageAdapter(feedImageUriList, this)
                            feedImageAdapter.setItemClickListener(object : FeedImageAdapter.ImgDeleteBtnClickListener {
                                // 사진 삭제 버튼 클릭
                                override fun onClickDeleteBtn(position: Int) {
                                    feedImageAdapter.dataList.removeAt(position)
                                    feedImageAdapter.notifyItemRemoved(position)
                                    feedImageAdapter.notifyItemRangeRemoved(position, feedImageUriList.size)
                                    feedImageUriList = feedImageAdapter.dataList
                                }
                            })
                            binding.rvFeedImage.adapter = feedImageAdapter
                            val mLinearLayoutManager = LinearLayoutManager(this)
                            mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                            // 가장 최근에 추가한 이미지가 맨왼쪽에 위치
                            mLinearLayoutManager.reverseLayout = true
                            mLinearLayoutManager.stackFromEnd = true
                            binding.rvFeedImage.layoutManager = mLinearLayoutManager
                        }
                    }
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
        binding.ibOpen.setOnClickListener {
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

    private fun setListenerToBackBtn() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }
}
