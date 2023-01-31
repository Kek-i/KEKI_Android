package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutService(val logoutView: LogoutView) {

    fun tryPatchUserLogout(){
        val patchLogoutRetrofitInterface = ApplicationClass.sRetrofit.create(LogoutRetrofitInterface::class.java)

        patchLogoutRetrofitInterface.patchUserLogout().enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>,response: Response<BaseResponse>) {
                logoutView.onPatchUserLogoutSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                logoutView.onPatchUserLogoutFailure(t.message ?: "통신 오류")
            }
        })
    }

}