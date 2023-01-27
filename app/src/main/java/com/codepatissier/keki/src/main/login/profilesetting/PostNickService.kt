package com.codepatissier.keki.src.main.login.profilesetting

import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostNickService(val postNickView: PostNickView) {

    fun tryPostCheckNick(nickname:String){
        val postNickRetrofitInterface = ApplicationClass.sRetrofit.create(PostNickRetrofitInterface::class.java)

        postNickRetrofitInterface.checkNickname(nickname = nickname).enqueue(object: Callback<PostNickname>{
            override fun onResponse(
                call: Call<PostNickname>,
                response: Response<PostNickname>
            ) {
                postNickView.onPostNickSuccess(response.body() as PostNickname)
            }

            override fun onFailure(call: Call<PostNickname>, t: Throwable) {
                postNickView.onPostNickFailure(t.message ?: "통신 오류")
            }

        })
    }
}