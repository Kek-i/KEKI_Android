package com.codepatissier.keki.src.main.seller.store.productfeed.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProductAddAndEditBinding
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddBody
import com.codepatissier.keki.src.main.seller.store.productfeed.product.model.SellerProductAddResponse
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class SellerProductAddActivity:BaseActivity<ActivitySellerProductAddAndEditBinding>(ActivitySellerProductAddAndEditBinding::inflate),
    SellerProductAddView {
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri?= null
    var fbStorage : FirebaseStorage?= null
    private lateinit var uploadImg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        uploadImg = null.toString()

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
        binding.ivPlus.setOnClickListener{
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
            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var profileImgName = "PRODUCT_IMAGE_"+timeStamp+"_.png"
            var storageRef = fbStorage?.reference?.child("product/$profileImgName")
            uploadImg = "product/$profileImgName"

            storageRef
                ?.putFile(ProfileUri!!)?.addOnProgressListener {
                    showLoadingDialog(this)
                }
                ?.addOnSuccessListener {
                    dismissLoadingDialog()
                    finish()
                }
                ?.addOnFailureListener{
                    dismissLoadingDialog()
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
                SellerProductAddService(this).tryPostProduct(sellerProductAddBody)
            }
        }
    }

    override fun onPostProductSuccess(response: SellerProductAddResponse) {
        dismissLoadingDialog()
        if(ProfileUri == null){
            finish()
        }
    }

    override fun onPostProductFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}