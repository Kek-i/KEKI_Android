package com.codepatissier.keki.src.main.seller.store.productfeed.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.UpdateLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivitySellerProductAddAndEditBinding
import com.codepatissier.keki.src.main.seller.store.SellerProductFeedFragment
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.SellerProductDetailFeedActivity
import com.codepatissier.keki.src.main.seller.store.productfeed.SellerProductFeedDetailService
import com.codepatissier.keki.src.main.seller.store.productfeed.SellerProductFeedDetailView
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class SellerProductEditActivity:BaseActivity<ActivitySellerProductAddAndEditBinding>(ActivitySellerProductAddAndEditBinding::inflate),
    SellerProductFeedDetailView {
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri?= null
    var fbStorage : FirebaseStorage?= null
    var dessertIdx: Long = 0
    private lateinit var uploadImg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()
        dessertIdx = intent.getStringExtra("dessertIdx")!!.toLong()

        uploadImg = null.toString()

        showLoadingDialog(this)
        //기존 정보 불러오기 -> (수정전)
        SellerProductFeedDetailService(this).tryGetProductFeedDetail(dessertIdx = dessertIdx)

        backClicked()
        plusClicked()
        clickConfirm()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun plusClicked(){
        binding.ivProduct.setOnClickListener{
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // 갤러리에서 가져온 프로필 이미지 부분에 삽입
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.bg_rectangle_radius_10_white_no_padding
                val imageView = binding.ivProduct

                Glide.with(this)
                    .load(ProfileUri)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .into(imageView)

                binding.ivPlus.visibility = View.GONE
            }
        }
    }

    private fun firebaseUpload(){
        if(ProfileUri != null){
            // 파이어베이스에 사진 업로드
            if(!uploadImg.equals(null)){
                // 파이어베이스 이전 사진 삭제
                fbStorage?.reference?.child(uploadImg)?.delete()
            }

            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var profileImgName = "PRODUCT_IMAGE_"+timeStamp+"_.png"
            var storageRef = fbStorage?.reference?.child("product/$profileImgName")
            uploadImg = "product/$profileImgName"

            storageRef
                ?.putFile(ProfileUri!!)?.addOnProgressListener {
                    //showLoadingDialog(this)
                }
                ?.addOnSuccessListener {
                    //dismissLoadingDialog()
                    finish()
                }
                ?.addOnFailureListener{
                    //dismissLoadingDialog()
                    Toast.makeText(this, "상품 사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // 완료 버튼 클릭시 서버에 patch
    private fun clickConfirm(){
        binding.tvCompletion.setOnClickListener{
            firebaseUpload()

            if(uploadImg != null){
                val productName = binding.etProductName.text.toString()
                val productPrice = Integer.parseInt(binding.etProductPrice.text.toString())
                val productIntro = binding.etProductIntro.text.toString()


                var sellerProductAddBody = SellerProductAddBody(dessertName = productName, dessertPrice = productPrice, dessertDescription = productIntro, dessertImg = uploadImg)

                showLoadingDialog(this)
                SellerProductFeedDetailService(this).tryPatchProductFeedDetail(dessertIdx, sellerProductAddBody)
            }
        }
    }

    //기존 작성 내용 그대로 불러오기
    private fun getFeedView(response: SellerProductFeedDetailResponse){
        binding.etProductName.setText(response.result.dessertName)
        binding.etProductPrice.setText(response.result.dessertPrice.toString())
        binding.etProductIntro.setText(response.result.dessertDescription)
        uploadImg = response.result.dessertImg
        val defaultImg = R.drawable.bg_oval_off_white
        val imageView = binding.ivProduct

        if(response.result.dessertImg != null) {
            // 이전 프로필 이미지 삽입
            var storageRef = fbStorage?.reference?.child(response.result.dessertImg)
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
                    binding.ivPlus.visibility = View.GONE
                }else{
                    dismissLoadingDialog()
                }
            }
        }else{
            dismissLoadingDialog()
        }

    }

    override fun onGetProductFeedSuccess(response: SellerProductFeedDetailResponse) {
        getFeedView(response)
    }

    override fun onGetProductFeedFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    //수정 성공하면 목록으로..?
    override fun onPatchProductFeedSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        val intent = Intent(this, SellerProductDetailFeedActivity::class.java)
        intent.putExtra("dessertIdx", "$dessertIdx")
        startActivity(intent)
        finish()
    }

    override fun onPatchProductFeedFailure(message: String) {
        Log.d("오류", message)
    }

    override fun onDelProductFeedSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }
    override fun onDelProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }



}