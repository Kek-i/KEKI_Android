package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditBody
import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ConsumerProfileEditRetrofitInterface {
    @PATCH("/users/profile")
    fun patchProfile(
        @Body parameter : ConsumerProfileEditBody
    ) : Call<ConsumerProfileEditResponse>
}