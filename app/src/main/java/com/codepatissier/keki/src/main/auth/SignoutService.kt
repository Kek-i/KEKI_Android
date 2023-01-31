package com.codepatissier.keki.src.main.auth

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignoutService(val signoutView: SignoutView) {

    fun tryPatchSignout(){
        val patchSignoutRetrofitInterface = ApplicationClass.sRetrofit.create(SignoutRetrofitInterface::class.java)

        patchSignoutRetrofitInterface.patchUserSignout().enqueue(object: Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>,response: Response<BaseResponse>) {
                signoutView.onPatchSignoutSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                signoutView.onPatchSignoutFailure(t.message ?: "통신 오류")
            }
        })
    }

}