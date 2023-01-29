package com.codepatissier.keki.src.main.login.profilesetting

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.login.model.PostSignupRequest
import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickRequest
import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService(val signupView: SignupView) {

    fun tryPostCheckNick(postNickRequest: PostNickRequest){
        val signupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)

        signupRetrofitInterface.checkNickname(postNickRequest).enqueue(object: Callback<PostNickname>{
            override fun onResponse(
                call: Call<PostNickname>,
                response: Response<PostNickname>
            ) {
                signupView.onPostNickSuccess(response.body() as PostNickname)
            }

            override fun onFailure(call: Call<PostNickname>, t: Throwable) {
                signupView.onPostNickFailure(t.message ?: "통신 오류")
            }

        })
    }

    fun tryPostUserSignup(postSignupRequest: PostSignupRequest){
        val postSignupRetrofitInterface = ApplicationClass.sRetrofit.create(SignupRetrofitInterface::class.java)

        postSignupRetrofitInterface.postUserSignup(postSignupRequest).enqueue(object: Callback<SocialTokenResponse>{
            override fun onResponse(call: Call<SocialTokenResponse>, response: Response<SocialTokenResponse>) {
                signupView.onPostSignupSuccess(response.body() as SocialTokenResponse)
            }
            override fun onFailure(call: Call<SocialTokenResponse>, t: Throwable) {
                signupView.onPostSignupFailure(t.message ?: "통신 오류")
            }
        })
    }
}