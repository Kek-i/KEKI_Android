package com.codepatissier.keki.src.main.consumer.mypage.profileEdit

import com.codepatissier.keki.src.main.consumer.mypage.profileEdit.model.ConsumerProfileEditResponse

interface ConsumerProfileEditView {
    fun onPatchProfileSuccess(response:ConsumerProfileEditResponse)
    fun onPatchProfileFailure(message:String)
}