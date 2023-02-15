package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConsumerProfileEditService(val consumerProfileEditView: ConsumerProfileEditView) {
    fun tryPatchProfile(body : ConsumerProfileEditBody){
        val consumerProfileEditRetofitInterface = ApplicationClass.sRetrofit.create(ConsumerProfileEditRetrofitInterface::class.java)
        consumerProfileEditRetofitInterface.patchProfile(body).enqueue(object : Callback<ConsumerProfileEditResponse>{
            override fun onResponse(
                call: Call<ConsumerProfileEditResponse>,
                response: Response<ConsumerProfileEditResponse>
            ) {
                consumerProfileEditView.onPatchProfileSuccess(response.body() as ConsumerProfileEditResponse)
            }

            override fun onFailure(call: Call<ConsumerProfileEditResponse>, t: Throwable) {
                consumerProfileEditView.onPatchProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}