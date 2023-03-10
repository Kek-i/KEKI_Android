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

class SignupService(val signupView: SignupView) {

    fun tryPostCheckNick(postNickRequest: PostNickRequest){
        val signupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)

        signupRetrofitInterface.checkNickname(postNickRequest).enqueue(object: Callback<PostNickname>{
            override fun onResponse(call: Call<PostNickname>,response: Response<PostNickname>) {
                signupView.onPostNickSuccess(response.body() as PostNickname)
            }
            override fun onFailure(call: Call<PostNickname>, t: Throwable) {
                Log.e("signup-error","${t.message}")
            }

        })
    }

    fun tryPostUserSignup(postUserSignupRequest: PostUserSignupRequest){
        val postSignupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)

        postSignupRetrofitInterface.postUserSignup(postUserSignupRequest).enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>, response: Response<SocialTokenResponse>) {
                signupView.onPostSignupSuccess(response.body() as SocialTokenResponse)
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                Log.e("Signup-error","${t.message}")
            }
        })
    }


}