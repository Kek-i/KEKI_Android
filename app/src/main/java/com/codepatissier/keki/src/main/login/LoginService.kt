package com.codepatissier.keki.src.main.login

import android.util.Log
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.login.model.SocialLoginResponse
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginView: LoginView) {

    fun tryGetKakaoLogin(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.getLoginKakaoUrl().enqueue(object: Callback<SocialLoginResponse>{
            override fun onResponse(call: Call<SocialLoginResponse>, response: Response<SocialLoginResponse>) {
                loginView.onGetLoginSuccess(response.body() as SocialLoginResponse)
            }
            override fun onFailure(call: Call<SocialLoginResponse>, t: Throwable) {
                loginView.onGetLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetNaverLogin(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.getLoginNaverUrl().enqueue(object: Callback<SocialLoginResponse>{
            override fun onResponse(call: Call<SocialLoginResponse>, response: Response<SocialLoginResponse>) {
                loginView.onGetLoginSuccess(response.body() as SocialLoginResponse)
            }
            override fun onFailure(call: Call<SocialLoginResponse>, t: Throwable) {
                loginView.onGetLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetGoogleLogin(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.getLoginGoogleUrl().enqueue(object: Callback<SocialLoginResponse>{
            override fun onResponse(call: Call<SocialLoginResponse>, response: Response<SocialLoginResponse>) {
                loginView.onGetLoginSuccess(response.body() as SocialLoginResponse)
            }
            override fun onFailure(call: Call<SocialLoginResponse>, t: Throwable) {
                loginView.onGetLoginFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetGoogleLoginToken(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.callbackGoogleToken().enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>, response: Response<SocialTokenResponse>) {
               //loginView.onGetTokenSuccess(response.body() as SocialTokenResponse)
               // Log.d("token", "${response.body()?.result}")
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
              // loginView.onGetTokenFailure(t.message ?: "통신 오류")
            }
        })
    }
    fun tryGetNaverLoginToken(){
        val loginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        loginRetrofitInterface.callbackNaverToken().enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>, response: Response<SocialTokenResponse>) {
                // loginView.onGetLoginSuccess(response.body() as SocialTokenResponse)
                //Log.d("token", "${response.body()?.result}")
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                // loginView.onGetLoginFailure(t.message ?: "통신 오류")
            }
        })
    }
}