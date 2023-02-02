package com.codepatissier.keki.config

import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(Authorization, null)
        if (jwtToken != null) {
            builder.addHeader("Authorization", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}