package com.codepatissier.keki.src.main.auth.profilesetting

import android.util.Log
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.auth.model.PostStoreSignupRequest
import com.codepatissier.keki.src.main.auth.model.PostUserSignupRequest
import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickRequest
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreSignupService(val storeSignupView: StoreSignupView) {

    fun tryPostStoreSignup(postStoreSignupRequest: PostStoreSignupRequest){
        val postSignupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)

        postSignupRetrofitInterface.postStoreSignup(postStoreSignupRequest).enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>, response: Response<SocialTokenResponse>) {
                storeSignupView.onPostStoreSignupSuccess(response.body() as SocialTokenResponse)
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                Log.e("Signup-error","${t.message}")
            }
        })
    }

}