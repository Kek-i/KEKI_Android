package com.codepatissier.keki.src.main.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.UserEmail
import com.codepatissier.keki.config.ApplicationClass.Companion.UserRole
import com.codepatissier.keki.config.ApplicationClass.Companion.userInfo
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityLoginBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.auth.model.PostLoginRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.util.viewpager.login.AccessRightDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),LoginView{
    private var userEmail :String?= null
    private var firebaseAuth: FirebaseAuth? = null
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socialLogin()
    }

    private fun socialLogin() {
        binding.ibGoogleBtn.setOnClickListener {
            googleLogin()
        }
        binding.ibNaverBtn.setOnClickListener {
            naverLogin()
        }
        binding.ibKakaoBtn.setOnClickListener {
            kakaoLogin()
        }
    }

    //POST로 로그인id에 대한 정보(토큰값, role) 받아오기 성공하면,  role에 따라서 엑티비티 설정
    override fun onGetUserInfoSuccess(response: SocialTokenResponse) {
        checkRole(response)
    }
    override fun onGetUserInfoFailure(message: String) {
    }

    //로그인 하면 토큰값, 회원 role 가져오기
    fun getRole(userEmail: String, provider:String){
        val postLoginRequest = PostLoginRequest(email = userEmail, provider = provider)
        LoginService(this).tryPostUserLogin(postLoginRequest)
    }

    private fun checkRole(response: SocialTokenResponse){
        //토큰 저장
        userInfo.putString(Authorization, response.result.accessToken)
        userInfo.putString(UserEmail, userEmail)
        Log.d("here", "$userEmail")
        if(response.result.role == "비회원"){
            userInfo.putString(UserRole, "비회원")
            AccessRightDialog(this).show() //앱 권한 확인 -> introActivity
        }else if(response.result.role =="구매자"){
            userInfo.putString(UserRole, "구매자")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        userInfo.commit()
    }



    //소셜 로그인 구현 파트

    private fun googleLogin(){
        //구글 로그인 초기화
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()

        googleSignInClient?.signInIntent?.run {
            startActivityForResult(this, REQ_CODE_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE_SIGN_IN) {
            val signInTask = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = signInTask.getResult(ApiException::class.java)
                onGoogleSignInAccount(account)
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    private fun onGoogleSignInAccount(account: GoogleSignInAccount?) {
        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener {
                onFirebaseAuthTask(it)
            }
        }
    }

    private fun onFirebaseAuthTask(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            // Google로 로그인 성공
            userEmail = task.result?.user?.email
            if (userEmail != null) {
                getRole(userEmail!!,"구글")
            }
            Log.d("google", "user_google_email: $userEmail")
        } else {
            // Google로 로그인 실패
            Log.d(this::class.java.simpleName, "Firebase Login Failure.")
        }
    }

    companion object {
        private const val REQ_CODE_SIGN_IN = 1000
    }

    private fun naverLogin(){
        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                userEmail=response.profile?.email
                if (userEmail != null) {
                    getRole(userEmail!!,"네이버")
                }
                Log.d("naver", "user_naver_email: $userEmail")
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d("naver", "errorCode: ${errorCode}\nerrorDescription: ${errorDescription}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
          val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Log.d("naver", "errorCode: ${errorCode}\nerrorDescription: ${errorDescription}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }


    private fun kakaoLogin() {
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("kakao", "카카오계정으로 로그인 실패 : ${error}")
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        Log.d("kakao", "user_kakao_email: ${user.kakaoAccount?.email}")
                        userEmail = user.kakaoAccount?.email
                        if (userEmail != null) {
                            getRole(userEmail!!,"카카오")
                        }
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("kakao", "카카오톡으로 로그인 실패 : ${error}")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }// 카톡 로그인 에러일 경우 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } //카카오톡으로 로그인
                else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                    Log.d("kakao","카카오 로그인 성공:${user?.kakaoAccount?.email}")
                        userEmail = user?.kakaoAccount?.email
                        if (userEmail != null) {
                            getRole(userEmail!!,"카카오") } }
                }
            }
        } else { //카톡 없는 경우 카카오계정 로그인 시도
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}