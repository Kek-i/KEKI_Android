package com.codepatissier.keki.src.main.auth.profilesetting

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProfileSettingBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.SellerMainActivity
import com.codepatissier.keki.src.main.auth.IntroActivity
import com.codepatissier.keki.src.main.auth.model.PostStoreSignupRequest
import com.codepatissier.keki.src.main.auth.model.PostUserSignupRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SellerProfileSettingActivity : BaseActivity<ActivitySellerProfileSettingBinding>(
    ActivitySellerProfileSettingBinding::inflate), StoreSignupView {

    private var profileImg: String? = null
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    var fbStorage : FirebaseStorage?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()

        clickConfirm()
        setTextUserEmail()
        clickBack()
        getProfileImg()
        keyboardEnterClicked()

    }

    //완료 버튼 클릭
    private fun clickConfirm() {
        binding.tvCheck.setOnClickListener {
            // 키패드 내리기

            val nickname = binding.et1ProfileSetting.text.toString()
            val address = binding.et2ProfileSetting.text.toString()
            val introduction = binding.et3ProfileSetting.text.toString()
            val orderUrl = binding.et4ProfileSetting.text.toString()
            val businessName = binding.et5ProfileSetting.text.toString()
            val brandName = binding.et6ProfileSetting.text.toString()
            val businessAddress = binding.et7ProfileSetting.text.toString()
            val businessNumber = binding.et8ProfileSetting.text.toString()


            //null값이 아니고, 중복 확인한 값일 경우(중복확인 누르고 값 바꾸는것 방지), 닉네임 조건에 맞을 경우
            if (nickname != null && address != null) {
                firebaseUpload()
                val postStoreSignupRequest =
                    PostStoreSignupRequest(profileImg,  nickname, address,introduction , orderUrl, businessName, brandName, businessAddress,businessNumber)
                StoreSignupService(this).tryPostStoreSignup(postStoreSignupRequest)
            } else {

            }
        }
    }

    //돌아가기 버튼 클릭
    private fun clickBack(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }

    //유저 이메일 표시하기
    private fun setTextUserEmail(){
        binding.tvUserEmail.text = ApplicationClass.sSharedPreferences.getString(ApplicationClass.UserEmail, null)
    }


    //프로필 사진 설정하기
    private fun getProfileImg(){
        binding.cvPhoto.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // 갤러리에서 가져온 프로필 이미지 부분에 삽입
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                ProfileUri = result.data?.data

                val defaultImg = R.drawable.ic_customer
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

            // 파이어베이스에 사진 업로드
            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var profileImgName = "PROFILE_IMAGE_"+timeStamp+"_.png"
            var storageRef = fbStorage?.reference?.child("profiles/$profileImgName")

            profileImg = "profiles/$profileImgName"

            storageRef?.putFile(ProfileUri!!)
        }
    }

    //사진 저장하기 전에 보여주는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            binding.cvPhoto.setImageURI(data?.data)

            val imagePath = data?.data!!

            val file = File(absolutelyPath(imagePath, this))
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("proFile", file.name, requestFile)
            Log.d(ContentValues.TAG,file.name)
        }
    }

    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    // 엔터 클릭 시 키패드 내리기
    private fun keyboardEnterClicked(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.et1ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et1ProfileSetting.windowToken, 0)
                binding.et1ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et2ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et2ProfileSetting.windowToken, 0)
                binding.et2ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et3ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et3ProfileSetting.windowToken, 0)
                binding.et3ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et4ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et4ProfileSetting.windowToken, 0)
                binding.et4ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et5ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et5ProfileSetting.windowToken, 0)
                binding.et5ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et6ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et6ProfileSetting.windowToken, 0)
                binding.et6ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et7ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et7ProfileSetting.windowToken, 0)
                binding.et7ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.et8ProfileSetting.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(binding.et8ProfileSetting.windowToken, 0)
                binding.et8ProfileSetting.clearFocus()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onPostStoreSignupSuccess(response: SocialTokenResponse) {
        ApplicationClass.userInfo.putString(ApplicationClass.UserRole, "판매자")
        ApplicationClass.userInfo.commit()
        startActivity(Intent(this, SellerMainActivity::class.java))
        finish()
    }

}