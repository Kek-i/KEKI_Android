package com.codepatissier.keki.src.main.seller.mypage.profileEdit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProfileEditBinding
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.ConsumerProfileEditService
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageService
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageView
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditBody
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.model.SellerProfileEditResponse
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class SellerProfileEditActivity :BaseActivity<ActivitySellerProfileEditBinding>(ActivitySellerProfileEditBinding::inflate), SellerMyPageView, SellerProfileEditView {

    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri?= null
    var fbStorage : FirebaseStorage ?= null
    private lateinit var editImg:String
    private lateinit var editNickname : String
    private lateinit var editAddress:String
    private lateinit var editIntroduction : String
    private lateinit var editOrderUrl:String
    private var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        editNickname = null.toString()
        editImg = null.toString()
        editAddress = null.toString()
        editIntroduction = null.toString()
        editOrderUrl = null.toString()

        showLoadingDialog(this)
        // ?????? ????????? ????????????
        SellerMyPageService(this).tryGetSellerMyPage()

        //setTextUserEmail()
        backClicked()
        profileClicked()
        clickConfirm()
        keyboardEnterClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun profileClicked(){
        // ??????????????? ????????? ????????????
        binding.cvPhoto.setOnClickListener{

            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // ??????????????? ????????? ????????? ????????? ????????? ??????
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.ic_seller
                val imageView = binding.cvPhoto

                Glide.with(this)
                    .load(ProfileUri)
                    .placeholder(defaultImg)
                    .error(defaultImg)
                    .fallback(defaultImg)
                    .circleCrop()
                    .into(imageView)
            }
        }
    }

    private fun firebaseUpload(){
        if(ProfileUri != null){

            if(!editImg.equals(null)){
                // ?????????????????? ?????? ?????? ??????
                fbStorage?.reference?.child(editImg)?.delete()
            }
            // ????????????????????? ?????? ?????????
            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var profileImgName = "PROFILE_IMAGE_"+timeStamp+"_.png"
            var storageRef = fbStorage?.reference?.child("profiles/$profileImgName")
            editImg = "profiles/$profileImgName";

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
                    Toast.makeText(this, "???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
                }

        }
    }

    // ?????? ?????? ????????? ????????? patch
    private fun clickConfirm(){
        binding.tvCheck.setOnClickListener{
            // ????????? ?????????

//            val nickname = binding.et1ProfileEdit.text.toString()
//            val address = binding.et2ProfileEdit.text.toString()
//            val introduction = binding.et3ProfileEdit.text.toString()
//            val orderUrl = binding.et4ProfileEdit.text.toString()

            editNickname = binding.et1ProfileEdit.text.toString()
            editAddress = binding.et2ProfileEdit.text.toString()
            editIntroduction = binding.et3ProfileEdit.text.toString()
            editOrderUrl = binding.et4ProfileEdit.text.toString()

            profileEdit()

        }
    }

    // ????????? ????????? ????????? ?????????
    private fun profileEdit(){
        firebaseUpload()

        var sellerProfileEditBody = SellerProfileEditBody(editImg, editNickname, editAddress,editIntroduction, editOrderUrl)
        showLoadingDialog(this)
        SellerProfileEditService(this).tryPatchStoreProfile(sellerProfileEditBody)
    }

    // ????????? ?????? ?????? patch ??????
    override fun onPatchProfileSuccess(response: SellerProfileEditResponse) {
        dismissLoadingDialog()
        if(ProfileUri == null){
            finish()
        }
    }

    // ????????? ?????? ?????? patch ??????
    override fun onPatchProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("?????? : $message")
    }

    // ?????? ????????? ???????????? ???????????? ??????
    override fun onGetMyPageSuccess(response: SellerMyPageResponse) {
        //?????? ????????? ????????????
        binding.tvUserEmail.text = response.result.email

        // ????????? ????????? ?????? ????????? ??? ???????????? ?????? ??? null ??? ?????? ????????? ????????? ?????? ????????? ?????? ????????? ????????????
        if(response.result.storeImgUrl != null){
            editImg = response.result.storeImgUrl
        }
        if(response.result.nickname != null){
            editNickname = response.result.nickname
        }
        if(response.result.address != null){
            editAddress = response.result.address
        }

        if(response.result.introduction != null){
            editIntroduction = response.result.introduction
        }
        if(response.result.orderUrl != null){
            editOrderUrl = response.result.orderUrl
        }


        // ?????? ??? ??????
        binding.et1ProfileEdit.setText(editNickname)
        binding.et2ProfileEdit.setText(editAddress)
        binding.et3ProfileEdit.setText(editIntroduction)
        binding.et4ProfileEdit.setText(editOrderUrl)

        val defaultImg = R.drawable.bg_oval_off_white
        val imageView = binding.cvPhoto

        if(response.result.storeImgUrl != null) {
            // ?????? ????????? ????????? ??????
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
        }else{
            dismissLoadingDialog()
        }
    }

    // ?????? ????????? ???????????? ???????????? ??????
    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("?????? : $message")
    }

    // ?????? ?????? ??? ????????? ?????????
    private fun keyboardEnterClicked(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.et1ProfileEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et1ProfileEdit.windowToken, 0)
                binding.et1ProfileEdit.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et2ProfileEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et2ProfileEdit.windowToken, 0)
                binding.et2ProfileEdit.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et3ProfileEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et3ProfileEdit.windowToken, 0)
                binding.et3ProfileEdit.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et4ProfileEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et4ProfileEdit.windowToken, 0)
                binding.et4ProfileEdit.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

}