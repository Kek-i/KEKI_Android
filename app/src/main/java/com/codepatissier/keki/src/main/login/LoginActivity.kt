package com.codepatissier.keki.src.main.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityLoginBinding
import com.codepatissier.keki.src.main.login.model.SocialLoginResponse
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL


class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginView{

    private var urlString : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        socialLogin()
    }

    override fun onRestart() {
        super.onRestart()
        val thread = ThreadExtends(urlString)
        thread.start()
    }

    class ThreadExtends(val  urlString: String) : Thread(){
        override fun run() {
            if(urlString != null) {
                println("original URL: $urlString")
                val redUrl = GetRedirectUrl(urlString)
                println("ReDirect URL : $redUrl")
                try {

                    //URL에서 JSON 값 구하는 코드
                    val link = URL(redUrl).openStream()
                    val rd = BufferedReader(InputStreamReader(link, "UTF-8"))
                    var str: String?
                    val buffer = StringBuffer()
                    while (rd.readLine().also { str = it } != null) {
                        buffer.append(str)
                    }
                    val receiveMsg = buffer.toString()
                    println(receiveMsg)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
    }


    override fun onGetLoginSuccess(response: SocialLoginResponse) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.result))
        urlString = "${response.result}"
        startActivity(intent)
    }

    override fun onGetLoginFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun socialLogin(){
        binding.ibGoogleBtn.setOnClickListener{
            LoginService(this).tryGetGoogleLogin()
        }
        binding.ibNaverBtn.setOnClickListener{
            LoginService(this).tryGetNaverLogin()
        }
        binding.ibKakaoBtn.setOnClickListener{
            LoginService(this).tryGetKakaoLogin()
        }
    }

}
