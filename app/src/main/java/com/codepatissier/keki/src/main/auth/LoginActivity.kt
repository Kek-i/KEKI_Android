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
import com.codepatissier.keki.src.SellerMainActivity
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

    //POST??? ?????????id??? ?????? ??????(?????????, role) ???????????? ????????????,  role??? ????????? ???????????? ??????
    override fun onGetUserInfoSuccess(response: SocialTokenResponse) {
        checkRole(response)
    }
    override fun onGetUserInfoFailure(message: String) {
    }

    //????????? ?????? ?????????, ?????? role ????????????
    fun getRole(userEmail: String, provider:String){
        val postLoginRequest = PostLoginRequest(email = userEmail, provider = provider)
        LoginService(this).tryPostUserLogin(postLoginRequest)
    }

    private fun checkRole(response: SocialTokenResponse){
        //?????? ??????
        userInfo.putString(Authorization, response.result.accessToken)
        userInfo.putString(UserEmail, userEmail)
        Log.d("here", "$userEmail")
        if(response.result.role == "?????????"){
            userInfo.putString(UserRole, "?????????")
            AccessRightDialog(this).show() //??? ?????? ?????? -> introActivity
        }else if(response.result.role =="?????????"){
            userInfo.putString(UserRole, "?????????")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else if(response.result.role =="?????????"){
            userInfo.putString(UserRole, "?????????")
            val intent = Intent(this, SellerMainActivity::class.java)
            startActivity(intent)
            finish()
        }
        userInfo.commit()
    }



    //?????? ????????? ?????? ??????

    private fun googleLogin(){
        //?????? ????????? ?????????
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
            // Google??? ????????? ??????
            userEmail = task.result?.user?.email
            if (userEmail != null) {
                getRole(userEmail!!,"??????")
            }
            Log.d("google", "user_google_email: $userEmail")
        } else {
            // Google??? ????????? ??????
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
                    getRole(userEmail!!,"?????????")
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
        // ?????????????????? ????????? ??? ??? ?????? ????????????????????? ???????????? ?????? ?????????
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("kakao", "????????????????????? ????????? ?????? : ${error}")
            } else if (token != null) {
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        Log.d("kakao", "user_kakao_email: ${user.kakaoAccount?.email}")
                        userEmail = user.kakaoAccount?.email
                        if (userEmail != null) {
                            getRole(userEmail!!,"?????????")
                        }
                    }
                }
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.d("kakao", "?????????????????? ????????? ?????? : ${error}")
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }// ?????? ????????? ????????? ?????? ????????????????????? ????????? ??????
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } //?????????????????? ?????????
                else if (token != null) {
                    UserApiClient.instance.me { user, error ->
                    Log.d("kakao","????????? ????????? ??????:${user?.kakaoAccount?.email}")
                        userEmail = user?.kakaoAccount?.email
                        if (userEmail != null) {
                            getRole(userEmail!!,"?????????") } }
                }
            }
        } else { //?????? ?????? ?????? ??????????????? ????????? ??????
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}