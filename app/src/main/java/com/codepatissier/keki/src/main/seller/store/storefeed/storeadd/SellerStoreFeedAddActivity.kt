package com.codepatissier.keki.src.main.seller.store.storefeed.storeadd

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivitySellerStoreFeedAddBinding
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.DessertName
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.PostStoreFeedRequest
import com.codepatissier.keki.src.main.seller.store.storefeed.storeadd.model.SellerFeedAddViewResponse
import com.codepatissier.keki.util.recycler.storefeedadd.FeedImageAdapter
import com.codepatissier.keki.util.recycler.storefeedadd.ProductNameAdapter
import com.google.android.material.chip.Chip
import com.google.firebase.storage.FirebaseStorage
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import java.text.SimpleDateFormat
import java.util.*

class SellerStoreFeedAddActivity : BaseActivity<ActivitySellerStoreFeedAddBinding>(
    ActivitySellerStoreFeedAddBinding::inflate), SellerStoreFeedAddView {
    private val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private val uploadedImgList = mutableListOf<String>()
    // 첨부 가능한 피드 이미지 최대 개수
    private val maxImage = 5
    // recyclerview adapter & datalist
    private lateinit var feedImageAdapter: FeedImageAdapter
    private var feedImageUriList = mutableListOf<Uri>()
    private lateinit var productNameAdapter: ProductNameAdapter
    private var productNameDataList = listOf<DessertName>()
    // 무슨색 태그가 사용 중인지
    private var bOffWhiteIsUsed: Boolean = false
    private var bVeryLightPinkIsUsed: Boolean = false
    private var bLightPeach2IsUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListenerToBackBtn()
        setListenerToCompletionBtn()
        initFeedImage()
        // 상품 및 해시태그 목록 서버에서 가져오기
        showLoadingDialog(this)
        SellerStoreFeedAddService(this).tryGetStoreFeedAddView()
    }

    override fun onGetFeedAddViewSuccess(response: SellerFeedAddViewResponse) {
        dismissLoadingDialog()
        // 상품 목록 RecyclerView 생성
        setProductNameRecyclerView(response.result.desserts)
        // 상품 선택 레이아웃
        setListenerToProductSelectionLayout()
        // 해시태그 Chip 생성
        createTagChip(response.result.tags)
        // 정렬용 태그 Click Listener
        setListenerToSortedTag(binding.chipFirstSortedTag, 1)
        setListenerToSortedTag(binding.chipSecondSortedTag, 2)
        setListenerToSortedTag(binding.chipThirdSortedTag, 3)
    }

    override fun onGetFeedAddViewFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostStoreFeedSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        finish()
    }

    override fun onPostStoreFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
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

    private fun setProductNameRecyclerView(productList: List<DessertName>) {
        productNameDataList = productList
        productNameAdapter = ProductNameAdapter(productNameDataList)
        productNameAdapter.setItemClickListener(object : ProductNameAdapter.ItemClickListener {
            override fun onClick(position: Int) {
                binding.tvSelectProduct.text = productNameDataList[position].dessertName
                binding.layoutOpenProduct.visibility = GONE
            }
        })
        binding.rvProduct.adapter = productNameAdapter

        // 등록된 상품이 없을 경우(리스트가 비어있으면) 안 열리고 밑에 에러문구 띄우기(상품 등록 먼저)
        if (productNameDataList.isEmpty()) {
            binding.tvProductError.text = getString(R.string.seller_feed_add_tv_error_no_product)
            binding.tvProductError.visibility = VISIBLE
        }
    }

    private fun setListenerToProductSelectionLayout() {
        binding.layoutCloseProduct.setOnClickListener {
            if (productNameDataList.isNotEmpty()) {
                // 선택 메뉴 open 되어 있을 때
                if (binding.layoutOpenProduct.visibility == VISIBLE) {
                    binding.layoutOpenProduct.visibility = GONE
                }
                // close 되어 있을 때
                else if (binding.layoutOpenProduct.visibility == GONE) {
                    binding.layoutOpenProduct.visibility = VISIBLE
                }
            }
        }
        binding.ibOpen.setOnClickListener {
            if (productNameDataList.isNotEmpty()) {
                // 선택 메뉴 open 되어 있을 때
                if (binding.layoutOpenProduct.visibility == VISIBLE) {
                    binding.layoutOpenProduct.visibility = GONE
                }
                // close 되어 있을 때
                else if (binding.layoutOpenProduct.visibility == GONE) {
                    binding.layoutOpenProduct.visibility = VISIBLE
                }
            }
        }
    }

    private fun createTagChip(tagList: List<String>) {
        for (i in tagList.indices) {
            val chip = layoutInflater.inflate(
                R.layout.single_chip_layout,
                binding.chipGroupHashtag,
                false
            ) as Chip
            chip.id = View.generateViewId()
            chip.text = "# ${tagList[i]}"
            binding.chipGroupHashtag.addView(chip)

            val firstTag = binding.chipFirstSortedTag
            val secondTag = binding.chipSecondSortedTag
            val thirdTag = binding.chipThirdSortedTag

            // 선택용 태그 ClickListener 설정
            chip.setOnClickListener {
                // 첫번째 태그 자리가 비어있다면 == 아무것도 선택하지 않은 상태
                if(firstTag.visibility == GONE) {
                    firstTag.text = chip.text
                    setBackgroundColor(firstTag)
                    chip.visibility = GONE
                    firstTag.visibility = VISIBLE
                }
                // 두번째 태그 자리가 비어있다면 == 첫번째 태그 사용 중
                else if(secondTag.visibility == GONE) {
                    secondTag.text = chip.text
                    setBackgroundColor(secondTag)
                    chip.visibility = GONE
                    secondTag.visibility = VISIBLE
                }
                // 세번째 태그 자리가 비어있다면 == 두,세번째 태그 사용 중
                else if(thirdTag.visibility == GONE) {
                    thirdTag.text = chip.text
                    setBackgroundColor(thirdTag)
                    chip.visibility = GONE
                    thirdTag.visibility = VISIBLE
                }
                // 모든 태그가 사용 중이라면
                else {
                    chip.isChecked = false
                    this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
                }
            }
        }
    }

    private fun setListenerToSortedTag(sortedTag: Chip, tagNumber: Int) {
        // 이미 선택된 상태에서 클릭했다면
        sortedTag.setOnClickListener {
            // 원래 태그 보여주기
            for (id in binding.chipGroupHashtag.checkedChipIds) {
                val chip: Chip = binding.chipGroupHashtag.findViewById(id)
                if(chip.text.equals(sortedTag.text)) {
                    chip.visibility = VISIBLE
                    chip.isChecked = false
                }
            }
            // 색 반환
            returnBackgroundColor(sortedTag)
            // 자리 이동
            moveTagPlace(tagNumber)
        }
    }

    private fun moveTagPlace(tagNumber: Int) {
        val firstTag = binding.chipFirstSortedTag
        val secondTag = binding.chipSecondSortedTag
        val thirdTag = binding.chipThirdSortedTag

        when(tagNumber) {
            1 -> {
                firstTag.text = ""
                if (secondTag.visibility != GONE) {
                    firstTag.text = secondTag.text
                    firstTag.chipBackgroundColor = secondTag.chipBackgroundColor
                    secondTag.text = ""
                    if (thirdTag.visibility != GONE) {
                        thirdTag.visibility = GONE
                        secondTag.text = thirdTag.text
                        secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                        thirdTag.text = ""
                    } else secondTag.visibility = GONE
                } else firstTag.visibility = GONE
            }
            2 -> {
                secondTag.text = ""
                if (thirdTag.visibility != GONE) {
                    secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                    thirdTag.visibility = GONE
                    secondTag.text = thirdTag.text
                    thirdTag.text = ""
                } else secondTag.visibility = GONE
            }
            3 -> {
                thirdTag.visibility = GONE
                thirdTag.text = ""
            }
        }
    }

    private fun returnBackgroundColor(tag: Chip) {
        when (tag.chipBackgroundColor) {
            // off_white를 사용했다면
            resources.getColorStateList(R.color.off_white, null) -> {
                bOffWhiteIsUsed = false
            }
            // very_light_pink를 사용했다면
            resources.getColorStateList(R.color.very_light_pink, null) -> {
                bVeryLightPinkIsUsed = false
            }
            // light_peach_2를 사용했다면
            resources.getColorStateList(R.color.light_peach_2, null) -> {
                bLightPeach2IsUsed = false
            }
        }
    }

    private fun setBackgroundColor(chipSortedTag: Chip) {
        // off_white를 쓸 수 있다면
        if (!bOffWhiteIsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.off_white)
            bOffWhiteIsUsed = true
        }
        // very_light_pink를 쓸 수 있다면
        else if (!bVeryLightPinkIsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.very_light_pink)
            bVeryLightPinkIsUsed = true
        }
        // light_peach_2를 쓸 수 있다면
        else if (!bLightPeach2IsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.light_peach_2)
            bLightPeach2IsUsed = true
        }
    }

    private fun setListenerToCompletionBtn() {
        binding.tvCompletion.setOnClickListener {
            // 필수 입력 항목 다 채웠는지 확인
            if (checkAllRequiredInputIsEntered()) {
                // Firebase에 사진 업로드
                uploadImgToFirebase()
                // Firebase에 업로드 성공 시 POST api 호출
                var dessertIdx = 0L
                val description = binding.etFeedContent.text.toString()
                val tags = mutableListOf<String>()

                for(product in productNameDataList) {
                    if(binding.tvSelectProduct.text.equals(product.dessertName))
                        dessertIdx = product.dessertIdx
                }
                for(id in binding.chipGroupHashtag.checkedChipIds) {
                    val tag: Chip = binding.chipGroupHashtag.findViewById(id)
                    var tagText = tag.text.toString().replace(" ", "")
                    tagText = tagText.replace("#", "")
                    tags.add(tagText)
                }

                val postStoreFeedRequest = PostStoreFeedRequest(
                    dessertIdx = dessertIdx, description = description, postImgUrls = uploadedImgList, tags = tags
                )
                showLoadingDialog(this)
                SellerStoreFeedAddService(this).tryPostStoreFeed(postStoreFeedRequest)
            }
        }
    }

    private fun uploadImgToFirebase() {
        for(feedImgUri in feedImageUriList) {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            // 시간만 같으면 ImgName이 중복될 수 있으므로 더 추가할 게 있으면 좋을 듯
            val feedImgName = "FEED_IMAGE_$timeStamp.png"
            val uploadImgName = "feed/$feedImgName"
            val storageRef = firebaseStorage.reference.child(uploadImgName)
            storageRef
                .putFile(feedImgUri).addOnProgressListener {
                    showLoadingDialog(this)
                }
                .addOnSuccessListener {
                    dismissLoadingDialog()
                    uploadedImgList.add(uploadImgName)
                    finish()
                }
                .addOnFailureListener {
                    dismissLoadingDialog()
                    Toast.makeText(this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkAllRequiredInputIsEntered(): Boolean {
        var check = true

        // 이미지 선택 확인
        if(feedImageUriList.isEmpty()) {
            binding.tvErrorNoImg.visibility = VISIBLE
            check = false
        } else binding.tvErrorNoImg.visibility = GONE
        // 제품 선택 확인
        if(productNameDataList.isEmpty()) {
            binding.tvProductError.text = getString(R.string.seller_feed_add_tv_error_no_product)
            binding.tvProductError.visibility = VISIBLE
            check = false
        } else {
            if(binding.tvSelectProduct.text.equals(getString(R.string.seller_feed_add_tv_select_product))) {
                binding.tvProductError.text = getString(R.string.calendar_add_error_no_select)
                binding.tvProductError.visibility = VISIBLE
                check = false
            } else binding.tvProductError.visibility = GONE
        }
        // 피드 내용 입력 확인
        if(binding.etFeedContent.text.isNullOrBlank()) {
            binding.tvFeedContentErrorNoInput.visibility = VISIBLE
            check = false
        } else binding.tvFeedContentErrorNoInput.visibility = GONE
        // 태그 선택 확인
        if(binding.chipGroupHashtag.checkedChipIds.isEmpty()) {
            binding.tvErrorNoTag.visibility = VISIBLE
            check = false
        } else binding.tvErrorNoTag.visibility = GONE

        return check
    }

    private fun setListenerToBackBtn() {
        binding.ibBack.setOnClickListener {
            finish()
        }
    }
}
