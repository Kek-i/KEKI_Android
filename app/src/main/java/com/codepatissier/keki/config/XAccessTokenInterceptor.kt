package com.codepatissier.keki.config

import com.codepatissier.keki.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWR4IjoyLCJzdWIiOiIyIiwiZXhwIjoxNjc1MDg2NTAwfQ.oKxrAAGw6bv0xxwo-Dq6Dms057iZgrctIi3NQkJYVi0")
        }
        return chain.proceed(builder.build())
    }
}