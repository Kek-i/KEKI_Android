package com.codepatissier.keki.src.main.auth.profilesetting

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.ApplicationClass.Companion.UserEmail
import com.codepatissier.keki.config.ApplicationClass.Companion.userInfo
import com.codepatissier.keki.databinding.ActivityConsumerProfileSettingBinding
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.auth.IntroActivity
import com.codepatissier.keki.src.main.auth.model.PostSignupRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickRequest
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname
import com.google.firebase.storage.FirebaseStorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CustomerProfileSettingActivity : BaseActivity<ActivityConsumerProfileSettingBinding>(
    ActivityConsumerProfileSettingBinding::inflate), SignupView {
    private var nickname: String? = null
    private var profileImg: String? = null
    private lateinit var launcher : ActivityResultLauncher<Intent>
    var ProfileUri : Uri ?= null
    var fbStorage : FirebaseStorage?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()

        clickConfirm()
        clickBack()
        clickDoubleCheck()
        setTextUserEmail()
        getProfileImg()
        keyboardEnterClicked()

    }

    //완료 버튼 클릭
    private fun clickConfirm() {
        binding.tvCheck.setOnClickListener {
            // 키패드 내리기
            keyboardDown()

            //null값이 아니고, 중복 확인한 값일 경우(중복확인 누르고 값 바꾸는것 방지), 닉네임 조건에 맞을 경우
            if (nickname != null && nickname == binding.etNickname.text.toString()
            ) {
                firebaseUpload()
                val postSignupRequest =
                    PostSignupRequest(nickname = nickname!!, profileImg = profileImg)
                SignupService(this).tryPostUserSignup(postSignupRequest)
            } else if (nickname == null) {
                binding.tvNamingResult.setText(R.string.edit_rule_null)
                binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
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

    //중복확인 버튼 클릭
    private fun clickDoubleCheck() {
        binding.btnDoubleCheck.setOnClickListener {
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
            // 키패드 내리기
            keyboardDown()
        }
    }

    //유저 이메일 표시하기
    private fun setTextUserEmail(){
        binding.tvUserEmail.text = ApplicationClass.sSharedPreferences.getString(UserEmail, null)
    }

    //닉네임 조건
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[가-힣ㄱ-ㅎa-zA-Z0-9]{2,10}\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }

    //중복확인 결과에 따라 문구 넣어주기
    @SuppressLint("SetTextI18n")
    override fun onPostNickSuccess(response: PostNickname) {
        if (response.isSuccess) {
            binding.tvNamingResult.setText(R.string.edit_rule_pass)
            nickname = binding.etNickname.text.toString()   //중복이 아닐 경우 닉네임 변수에 넣기
        } else {
            binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            binding.tvNamingResult.setText(R.string.edit_rule_false)
        }
    }

    //회원가입 성공하면 role 값 다시 설정,  메인 엑티비티로 이동
    override fun onPostSignupSuccess(response: SocialTokenResponse) {
        userInfo.putString(ApplicationClass.UserRole, "구매자")
        userInfo.commit()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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
        binding.etNickname.setOnKeyListener{view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                keyboardDown()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    // 키패드 내리는 함수
    private fun keyboardDown(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etNickname.windowToken, 0)
    }

}