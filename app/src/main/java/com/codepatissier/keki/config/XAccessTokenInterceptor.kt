package com.codepatissier.keki.config

import android.widget.Toast
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.Provider
import com.codepatissier.keki.config.ApplicationClass.Companion.RefreshToken
import com.codepatissier.keki.config.ApplicationClass.Companion.UserEmail
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import com.codepatissier.keki.config.model.PostReissueRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.*
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader("Connection", "close")
        var jwtToken: String? = sSharedPreferences.getString(Authorization, null)
        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
        }

        val response = chain.proceed(builder.build())

        if (response.code == 401) {
            val refreshToken = sSharedPreferences.getString(RefreshToken, null)
            val userEmail = sSharedPreferences.getString(UserEmail, null)
            val provider = sSharedPreferences.getString(Provider, null)

            val postReissueRequest = PostReissueRequest(email = userEmail!!, provider = provider!!, refreshToken = refreshToken!!)
            val postReissueRetrofitInterface = ApplicationClass.sRetrofit.create(ReissueRetrofitInterface::class.java)
            val call = postReissueRetrofitInterface.reissueToken(postReissueRequest)!!
            call.enqueue(object : Callback<SocialTokenResponse> {
                override fun onResponse(call: Call<SocialTokenResponse>, response: retrofit2.Response<SocialTokenResponse>) {
                    if (response?.isSuccessful) {
                        val newRefreshToken = response.body()?.result?.refreshToken
                        val newAccessToken = response.body()?.result?.accessToken

                        ApplicationClass.userInfo.putString(Authorization, newAccessToken)
                        ApplicationClass.userInfo.putString(RefreshToken, newRefreshToken)
                        ApplicationClass.userInfo.commit()

                        builder.addHeader("Authorization", newAccessToken!!)
                        //jwtToken = sSharedPreferences.getString(Authorization, null)

                        chain.proceed(builder.build())
                    }
                    else {
                        ApplicationClass.userInfo.remove("Authorization")
                        ApplicationClass.userInfo.remove("RefreshToken")
                        ApplicationClass.userInfo.remove("UserRole")
                        ApplicationClass.userInfo.remove("UserEmail")
                        ApplicationClass.userInfo.remove("Provider")
                        ApplicationClass.userInfo.commit()
                        //리프레시 토큰 만료 -> 로그인 다시 하기
                        Toast.makeText(applicationContext,"토큰이 만료되었습니다. 다시 로그인해주세요.",Toast.LENGTH_SHORT).show()
                    }
                    chain.proceed(builder.build())
                }
                override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                    //리프레시 토큰 만료 -> 로그인 다시 하기
                    ApplicationClass.userInfo.remove("Authorization")
                    ApplicationClass.userInfo.remove("RefreshToken")
                    ApplicationClass.userInfo.remove("UserRole")
                    ApplicationClass.userInfo.remove("UserEmail")
                    ApplicationClass.userInfo.remove("Provider")
                    ApplicationClass.userInfo.commit()

                    Toast.makeText(applicationContext,"토큰이 만료되었습니다. 다시 로그인해주세요.",Toast.LENGTH_SHORT).show()
                }
            })
        }

        return  response
    }

}