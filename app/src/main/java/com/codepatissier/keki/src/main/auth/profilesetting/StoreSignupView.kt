package com.codepatissier.keki.src.main.auth.profilesetting

import com.codepatissier.keki.src.main.auth.model.SocialTokenResponse
import com.codepatissier.keki.src.main.auth.profilesetting.model.PostNickname

interface StoreSignupView {
    fun onPostStoreSignupSuccess(response: SocialTokenResponse)

}