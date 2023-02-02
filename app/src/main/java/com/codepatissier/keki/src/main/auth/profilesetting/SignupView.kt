package com.codepatissier.keki.src.main.auth.profilesetting

import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname

interface SignupView {
    fun onPostNickSuccess(response: PostNickname)
//    fun onPostNickFailure(message: String)

    fun onPostSignupSuccess(response: SocialTokenResponse)
//    fun onPostSignupFailure(message: String)
}