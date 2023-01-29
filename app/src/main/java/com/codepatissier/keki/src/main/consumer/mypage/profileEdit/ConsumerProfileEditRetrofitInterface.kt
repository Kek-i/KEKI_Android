package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import retrofit2.http.PATCH

interface ConsumerProfileEditRetrofitInterface {
    @PATCH("/users/profile")
    fun editProfile(
        @Path("")
    ) : Call<>
}