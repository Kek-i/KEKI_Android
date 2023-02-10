package com.codepatissier.keki.src.main.seller.store.storefeed

import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedAddBinding
import com.codepatissier.keki.util.recycler.storefeedadd.FeedImageAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameData
import com.nguyenhoanglam.imagepicker.model.GridCount
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker

class SellerStoreFeedAddActivity : BaseActivity<ActivitySellerStoreFeedAddBinding>(
    ActivitySellerStoreFeedAddBinding::inflate) {
    private lateinit var pickMultiplePhotos: ActivityResultLauncher<PickVisualMediaRequest>
    // 상품 선택 메뉴 open/close 여부
    var isOpenProductSelectionLayout: Boolean = false
    // recyclerview adapter & datalist
    private lateinit var feedImageAdapter: FeedImageAdapter
    private val feedImageUriList = mutableListOf<Uri>()
    private lateinit var productNameAdapter: ProductNameAdapter
    private val productNameDataList = mutableListOf<ProductNameData>()
    // 서버 연동한 후에는 mutableList 말고 그냥 list 쓰기 (수정불가능하게)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListenerToBackBtn()
        setFeedImages()
        setProductNameRecyclerView()
        setListenerToProductSelectionLayout()
    }

    private fun setFeedImages() {
        val maxItems = 5
        val launcher = registerImagePicker { images ->
            // Selected images are ready to use
            if(images.isNotEmpty()){
                for(image in images) {
                    feedImageUriList.add(image.uri)
                }
                feedImageAdapter = FeedImageAdapter(feedImageUriList, this)
                binding.rvFeedImage.adapter = feedImageAdapter
                val mLinearLayoutManager = LinearLayoutManager(this)
                mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                // 가장 최근에 추가한 이미지가 맨왼쪽에 위치
                mLinearLayoutManager.reverseLayout = true
                mLinearLayoutManager.stackFromEnd = true
                binding.rvFeedImage.layoutManager = mLinearLayoutManager
            }
        }
        
        // string.xml 추가
        val config = ImagePickerConfig(
            statusBarColor = "#FFFFFF",
            isLightStatusBar = true,
            toolbarColor = "#FFFFFF",
            toolbarTextColor = "#000000",
            toolbarIconColor = "#000000",
            backgroundColor = "#FFFFFF",
            doneTitle = "완료",
            folderTitle = "앨범",
            isShowNumberIndicator = true,
            isAlwaysShowDoneButton = true,
            isFolderMode = true,
            isMultipleMode = true,
            subDirectory = "Photos",
            folderGridCount = GridCount(2, 4),
            imageGridCount = GridCount(3, 5),
        )

        binding.ibAddImage.setOnClickListener {
            if(maxItems == feedImageUriList.size) {
                showCustomToast("첨부 가능한 이미지 최대 개수는 5개입니다.")
            } else {
                config.maxSize = maxItems-feedImageUriList.size
                config.limitMessage = ""

                launcher.launch(config)
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
