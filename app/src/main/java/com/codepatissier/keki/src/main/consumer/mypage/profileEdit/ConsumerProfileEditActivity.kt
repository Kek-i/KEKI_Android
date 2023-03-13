package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerProfileEditBinding
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.SignupService
import com.codepatissier.keki.src.main.auth.profilesetting.SignupView
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickRequest
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageService
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageView
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ConsumerProfileEditActivity :BaseActivity<ActivityConsumerProfileEditBinding>(ActivityConsumerProfileEditBinding::inflate), ConsumerProfileEditView,
    ConsumerMyPageView, SignupView {
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    var fbStorage : FirebaseStorage ?= null
    private lateinit var editImg:String
    private lateinit var editNickname : String
    private var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        editNickname = null.toString()
        editImg = null.toString()

        showLoadingDialog(this)
        // 이전 프로필 가져오기
        ConsumerMyPageService(this).tryGetMyPage()

        setTextUserEmail()
        backClicked()
        profileClicked()
        clickConfirm()
        clickDoubleCheck()
        keyboardEnterClicked()
    }

    private fun backClicked(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    //유저 이메일 표시하기
    private fun setTextUserEmail(){
        binding.tvUserEmail.text = ApplicationClass.sSharedPreferences.getString(ApplicationClass.UserEmail, null)
    }

    private fun profileClicked(){
        // 갤러리에서 이미지 가져오기
        binding.ivProfile.setOnClickListener{
            // 키패드 내리기
            keyboardDown()

            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // 갤러리에서 가져온 프로필 이미지 부분에 삽입
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.ic_customer
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
            editImg = "profiles/$profileImgName"

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
    private fun clickConfirm(){
        binding.tvEdit.setOnClickListener{
            // 키패드 내리기
            keyboardDown()
            //null값이 아니고, 중복 확인한 값일 경우(중복확인 누르고 값 바꾸는것 방지), 닉네임 조건에 맞을 경우
            if (nickname != null && nickname == binding.etNickname.text.toString() ) {
                profileEdit()
            // 이전 프로필과 아이디가 같을 경우
            }else if(nickname == null && editNickname == binding.etNickname.text.toString()){
                profileEdit()
            } else if (nickname == null) {
                binding.tvNamingResult.setText(R.string.edit_rule_null)
                binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            }
        }
    }

    // 수정된 프로필 서버에 보내기
    private fun profileEdit(){
        firebaseUpload()

        editNickname = binding.etNickname.text.toString()

        var consumerProfileEditBody = ConsumerProfileEditBody(editNickname, editImg)
        showLoadingDialog(this)
        ConsumerProfileEditService(this).tryPatchProfile(consumerProfileEditBody)
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
        // 프로필 이미지 또는 닉네임 중 한가지만 수정 시 null 값 뜨지 않도록 변수로 이전 프로필 또는 닉네임 가져오기
        if(response.result.profileImg != null){
            editImg = response.result.profileImg
        }

        if(response.result.nickname != null){
            editNickname = response.result.nickname
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
                    dismissLoadingDialog()
                }else{
                    dismissLoadingDialog()
                }
            }
        }else{
            dismissLoadingDialog()
        }
    }

    // 이전 프로필 서버에서 가져오기 실패
    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    //중복확인 버튼 클릭
    private fun clickDoubleCheck() {
        binding.btnOverlap.setOnClickListener {
            // 키패드 내리기
            keyboardDown()

            nickname = null     //새로 중복 확인 누르면 기존 시도 닉네임 초기화
            val tryNick = binding.etNickname.text.toString()
            if(isValidNickname(tryNick)){
                val postNickRequest = PostNickRequest(nickname = tryNick)  //새로운 닉네임
                SignupService(this).tryPostCheckNick(postNickRequest)
            }
            else{
                binding.tvNamingResult.setText(R.string.edit_rule_wrong)
                binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            }
        }
    }

    //닉네임 조건
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9]{2,10}\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }

    // 닉네임 중복확인
    override fun onPostNickSuccess(response: PostNickname) {
        if (response.isSuccess || editNickname == binding.etNickname.text.toString()) {
            binding.tvNamingResult.setText(R.string.edit_rule_pass)
            nickname = binding.etNickname.text.toString()   //중복이 아닐 경우 닉네임 변수에 넣기
        } else {
            binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            binding.tvNamingResult.setText(R.string.edit_rule_false)
        }
    }

    // 로그인 성공시라 사용 x
    override fun onPostSignupSuccess(response: SocialTokenResponse) {
        TODO("Not yet implemented")
    }

    // 엔터 클릭 시 키패드 내리기
    private fun keyboardEnterClicked(){
        binding.etNickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                keyboardDown()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    // 키패드 내리는 함수
    private fun keyboardDown(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etNickname.windowToken, 0)
        binding.etNickname.clearFocus()
    }
}