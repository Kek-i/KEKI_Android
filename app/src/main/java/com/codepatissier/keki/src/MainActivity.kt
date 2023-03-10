package com.codepatissier.keki.src

import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.ApplicationClass.Companion.Authorization
import com.codepatissier.keki.config.ApplicationClass.Companion.sSharedPreferences
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityMainBinding
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarFragment
import com.codepatissier.keki.util.NonConsumerFragment
import com.codepatissier.keki.src.main.consumer.home.ConsumerHomeFragment
import com.codepatissier.keki.src.main.consumer.like.ConsumerLikeFragment
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageFragment
import com.codepatissier.keki.src.main.consumer.mypage.NonConsumerMyPageFragment
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val accessToken = sSharedPreferences.getString(Authorization, null)
    private val userRole = sSharedPreferences.getString(ApplicationClass.UserRole, "λΉνμ")
    var itemId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, ConsumerHomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                if(itemId != item.itemId){
                    when (item.itemId) {
                        R.id.menu_consumer_main_btm_nav_home -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_frm, ConsumerHomeFragment())
                                .commitAllowingStateLoss()
                        }
                        R.id.menu_consumer_main_btm_nav_calendar -> {
                            if(accessToken != null && userRole == "κ΅¬λ§€μ") {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, ConsumerCalendarFragment())
                                    .commitAllowingStateLoss()
                            } else {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, NonConsumerFragment())
                                    .commitAllowingStateLoss()
                            }
                        }
                        R.id.menu_consumer_main_btm_nav_search -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_frm, ConsumerSearchFragment())
                                .commitAllowingStateLoss()
                        }
                        R.id.menu_consumer_main_btm_nav_like -> {
                             if(accessToken != null && userRole == "κ΅¬λ§€μ") {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, ConsumerLikeFragment())
                                    .commitAllowingStateLoss()
                            } else {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, NonConsumerFragment())
                                    .commitAllowingStateLoss()
                            }
                        }
                        R.id.menu_consumer_main_btm_nav_my_page -> {
                            if (accessToken != null && userRole == "κ΅¬λ§€μ") {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, ConsumerMyPageFragment())
                                    .commitAllowingStateLoss()
                            } else {
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, NonConsumerMyPageFragment())
                                    .commitAllowingStateLoss()
                            }
                        }
                    }
                }
                itemId = item.itemId
                true
            }
            //selectedItemId = R.id.menu_consumer_main_btm_nav_home
        }
    }

}
