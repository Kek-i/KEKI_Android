package com.codepatissier.keki.src.main.login.profilesetting

import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname

interface PostNickView {
    fun onPostNickSuccess(response: PostNickname)
    fun onPostNickFailure(message: String)
}