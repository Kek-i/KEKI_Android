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

        editor.putString(Authorization, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWR4IjoyLCJzdWIiOiIyIiwiZXhwIjoxNjc1MDg2NTAwfQ.oKxrAAGw6bv0xxwo-Dq6Dms057iZgrctIi3NQkJYVi0")
        editor.commit()


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}