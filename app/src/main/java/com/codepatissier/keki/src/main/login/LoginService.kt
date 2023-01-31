package com.codepatissier.keki.src.main.login

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.login.model.PostLoginRequest
import com.codepatissier.keki.src.main.login.model.PostSignupRequest
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginView: LoginView) {

    fun tryPostUserLogin(postLoginRequest: PostLoginRequest){
        val postLoginRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)

        postLoginRetrofitInterface.postUserInfo(postLoginRequest).enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>,response: Response<SocialTokenResponse>) {
                loginView.onGetUserInfoSuccess(response.body() as SocialTokenResponse)
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                loginView.onGetUserInfoFailure(t.message ?: "통신 오류")
            }
        })
    }

}