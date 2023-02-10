package com.codepatissier.keki.src

import android.os.Bundle
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityMainBinding
import com.codepatissier.keki.databinding.ActivitySellerMainBinding
import com.codepatissier.keki.src.main.consumer.home.ConsumerHomeFragment
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageFragment
import com.codepatissier.keki.src.main.seller.store.SellerStoreMainFragment

class SellerMainActivity : BaseActivity<ActivitySellerMainBinding>(ActivitySellerMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, SellerStoreMainFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SellerStoreMainFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SellerMyPageFragment())
                            .commitAllowingStateLoss()
                    }

                }
                true
            }
            //selectedItemId = R.id.menu_consumer_main_btm_nav_home
        }
    }

}