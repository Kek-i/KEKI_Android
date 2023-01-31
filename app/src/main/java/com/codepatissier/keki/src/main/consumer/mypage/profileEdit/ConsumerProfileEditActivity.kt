package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerProfileEditBinding
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageService
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageView
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ConsumerProfileEditActivity :BaseActivity<ActivityConsumerProfileEditBinding>(ActivityConsumerProfileEditBinding::inflate), ConsumerProfileEditView,
    ConsumerMyPageView {
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    var fbStorage : FirebaseStorage ?= null
    private lateinit var editImg:String
    private lateinit var editNickname : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        showLoadingDialog(this)
        // 이전 프로필 가져오기
        ConsumerMyPageService(this).tryGetMyPage()

        backClicked()
        profileClicked()
        profileEditClick()

    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun profileClicked(){
        // 갤러리에서 이미지 가져오기
        binding.ivProfile.setOnClickListener{
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // 갤러리에서 가져온 프로필 이미지 부분에 삽입
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.bg_oval_off_white
                val imageView = binding.ivProfile

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
                // 파이어베이스 이전 사진 삭제
                fbStorage?.reference?.child(editImg)?.delete()
            }
            // 파이어베이스에 사진 업로드
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
                    Toast.makeText(this, "프로필이 수정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

        }
    }

    // 완료 버튼 클릭시 서버에 patch
    private fun profileEditClick(){
        binding.tvEdit.setOnClickListener{
            firebaseUpload()

            editNickname = binding.etNickname.text.toString()

            // 서버에 편집한 닉네임, 사진 보내기.
            var consumerProfileEditBody = ConsumerProfileEditBody(editNickname, editImg)
            showLoadingDialog(this)
            ConsumerProfileEditService(this).tryPatchProfile(consumerProfileEditBody)
        }
    }

    // 프로필 편집 서버 patch 성공
    override fun onPatchProfileSuccess(response: ConsumerProfileEditResponse) {
        dismissLoadingDialog()
        if(ProfileUri == null){
            finish()
        }
    }

    // 프로필 편집 서버 patch 실패
    override fun onPatchProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    // 이전 프로필 서버에서 가져오기 성공
    override fun onGetMyPageSuccess(response: ConsumerMyPageResponse) {
        dismissLoadingDialog()

        // 프로필 이미지 또는 닉네임 중 한가지만 수정 시 null 값 뜨지 않도록 변수로 이전 프로필 또는 닉네임 가져오기
        if(response.result.profileImg != null){
            editImg = response.result.profileImg
        }

        // 이전 닉네임 삽입
        binding.etNickname.setText(response.result.nickname)

        val defaultImg = R.drawable.bg_oval_off_white
        val imageView = binding.ivProfile

        if(response.result.profileImg != null) {
            // 이전 프로필 이미지 삽입
            var storageRef = fbStorage?.reference?.child(response.result.profileImg)
            storageRef?.downloadUrl?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(this)
                        .load(it.result)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .circleCrop()
                        .into(imageView)
                }
            }
        }
    }

    // 이전 프로필 서버에서 가져오기 실패
    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}