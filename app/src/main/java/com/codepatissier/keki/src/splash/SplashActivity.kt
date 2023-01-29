package com.codepatissier.keki.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.UserRole
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySplashBinding
import com.codepatissier.keki.src.MainActivity
import com.codepatissier.keki.src.main.login.IntroActivity
import com.codepatissier.keki.src.main.login.LoginActivity
import com.codepatissier.keki.util.viewpager.login.AccessRightDialog

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*
        자동 로그인 X : 스플래시 -> 비로그인 홈화면 (고정멘트 + 랜덤 해시태그)
        자동 로그인 O : 스플래시 -> 로그인 홈화면

         */

        val AccessToken = sSharedPreferences.getString(Authorization, null)
        val userRole = sSharedPreferences.getString(UserRole, "비회원")

        if(AccessToken != null) {
            //토큰은 있지만 role은 비회원
            //소셜 로그인 진행하면 유저 토큰이 나오기 때문에, 토큰은 있으나 아직 role선택/닉네임 설정 안하고 종료했을 경우
            if(userRole == "비회원") {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }, 1500)
            }
            else if(userRole =="구매자"){
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 1500)
            }
        }
        //토큰이 없을 경우 비회원 메인화면으로
        else{
            Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            }, 1500)

        }


        // 구매자 토큰 임의 저장

//        userInfo.putString(Authorization, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWR4IjoyLCJzdWIiOiIyIiwiZXhwIjoxNjc1MDg2NTAwfQ.oKxrAAGw6bv0xxwo-Dq6Dms057iZgrctIi3NQkJYVi0")
//
//        userInfo.commit()

//
//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }, 1500)
    }
}