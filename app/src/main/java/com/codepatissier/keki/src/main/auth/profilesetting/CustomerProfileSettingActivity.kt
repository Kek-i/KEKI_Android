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
import android.view.inputmethod.EditorInfo
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
import com.codepatissier.keki.src.main.auth.model.PostUserSignupRequest
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

    //?????? ?????? ??????
    private fun clickConfirm() {
        binding.tvCheck.setOnClickListener {
            // ????????? ?????????
            keyboardDown()

            //null?????? ?????????, ?????? ????????? ?????? ??????(???????????? ????????? ??? ???????????? ??????), ????????? ????????? ?????? ??????
            if (nickname != null && nickname == binding.etNickname.text.toString()
            ) {
                firebaseUpload()
                val postUserSignupRequest =
                    PostUserSignupRequest(nickname = nickname!!, profileImg = profileImg)
                SignupService(this).tryPostUserSignup(postUserSignupRequest)
            } else if (nickname == null) {
                binding.tvNamingResult.setText(R.string.edit_rule_null)
                binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            }
        }
    }

    //???????????? ?????? ??????
    private fun clickBack(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }

    //???????????? ?????? ??????
    private fun clickDoubleCheck() {
        binding.btnDoubleCheck.setOnClickListener {
            // ????????? ?????????
            keyboardDown()

            nickname = null     //?????? ?????? ?????? ????????? ?????? ?????? ????????? ?????????
            val tryNick = binding.etNickname.text.toString()
            if(isValidNickname(tryNick)){
                val postNickRequest = PostNickRequest(nickname = tryNick)  //????????? ?????????
                SignupService(this).tryPostCheckNick(postNickRequest)
            }
            else{
                binding.tvNamingResult.setText(R.string.edit_rule_wrong)
                binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            }
        }
    }

    //?????? ????????? ????????????
    private fun setTextUserEmail(){
        binding.tvUserEmail.text = ApplicationClass.sSharedPreferences.getString(UserEmail, null)
    }

    //????????? ??????
    fun isValidNickname(nickname: String?): Boolean {
        val trimmedNickname = nickname?.trim().toString()
        val exp = Regex("^[???-??????-???a-zA-Z0-9]{2,10}\$")
        return !trimmedNickname.isNullOrEmpty() && exp.matches(trimmedNickname)
    }

    //???????????? ????????? ?????? ?????? ????????????
    @SuppressLint("SetTextI18n")
    override fun onPostNickSuccess(response: PostNickname) {
        if (response.isSuccess) {
            binding.tvNamingResult.setTextColor(resources.getColor(R.color.brown_grey))
            binding.tvNamingResult.setText(R.string.edit_rule_pass)
            nickname = binding.etNickname.text.toString()   //????????? ?????? ?????? ????????? ????????? ??????
        } else {
            binding.tvNamingResult.setTextColor(resources.getColor(R.color.darkish_pink))
            binding.tvNamingResult.setText(R.string.edit_rule_false)
        }
    }

    //???????????? ???????????? role ??? ?????? ??????,  ?????? ??????????????? ??????
    override fun onPostSignupSuccess(response: SocialTokenResponse) {
        userInfo.putString(ApplicationClass.UserRole, "?????????")
        userInfo.commit()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    //????????? ?????? ????????????
    private fun getProfileImg(){
        binding.cvPhoto.setOnClickListener {
            var intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            launcher.launch(intent)
        }

        // ??????????????? ????????? ????????? ????????? ????????? ??????
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

            // ????????????????????? ?????? ?????????
            var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var profileImgName = "PROFILE_IMAGE_"+timeStamp+"_.png"
            var storageRef = fbStorage?.reference?.child("profiles/$profileImgName")

            profileImg = "profiles/$profileImgName"

            storageRef?.putFile(ProfileUri!!)
        }
    }

    //?????? ???????????? ?????? ???????????? ??????
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

    // ?????? ?????? ??? ????????? ?????????
    private fun keyboardEnterClicked(){
        binding.etNickname.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                keyboardDown()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    // ????????? ????????? ??????
    private fun keyboardDown(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etNickname.windowToken, 0)
        binding.etNickname.clearFocus()
    }

}