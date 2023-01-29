package com.codepatissier.keki.src.main.login.profilesetting

import com.codepatissier.keki.src.main.login.model.SocialTokenResponse
import com.codepatissier.keki.src.main.login.profilesetting.model.PostNickname

interface SignupView {
    fun onPostNickSuccess(response: PostNickname)
    fun onPostNickFailure(message: String)

    fun onPostSignupSuccess(response: SocialTokenResponse)
    fun onPostSignupFailure(message: String)
}