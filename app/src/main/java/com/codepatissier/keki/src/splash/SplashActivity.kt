package com.codepatissier.keki.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.editor
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySplashBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.login.LoginActivity
import kotlinx.coroutines.MainScope

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*
        자동 로그인 X : 스플래시 -> 비로그인 홈화면 (고정멘트 + 랜덤 해시태그)
        자동 로그인 O : 스플래시 -> 로그인 홈화면
         */

        // 자동로그인 확인

        // 구매자 토큰 임의 저장
        editor.putString(Authorization, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWR4IjoyLCJzdWIiOiIyIiwiZXhwIjoxNjc1MDg2NTAwfQ.oKxrAAGw6bv0xxwo-Dq6Dms057iZgrctIi3NQkJYVi0")
        editor.commit()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)
    }
}