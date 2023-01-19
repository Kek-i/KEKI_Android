package com.codepatissier.keki.src

import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityMainBinding
import com.codepatissier.keki.src.main.consumer.calendar.ConsumerCalendarFragment
import com.codepatissier.keki.src.main.consumer.home.ConsumerHomeFragment
import com.codepatissier.keki.src.main.consumer.like.ConsumerLikeFragment
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageFragment
import com.codepatissier.keki.src.main.consumer.search.ConsumerSearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, ConsumerHomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_consumer_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ConsumerHomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_consumer_main_btm_nav_calendar -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ConsumerCalendarFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_consumer_main_btm_nav_search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ConsumerSearchFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_consumer_main_btm_nav_like -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ConsumerLikeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_consumer_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ConsumerMyPageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }
}