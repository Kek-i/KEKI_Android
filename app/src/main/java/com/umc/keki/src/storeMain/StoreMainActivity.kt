<<<<<<<< HEAD:app/src/main/java/com/umc/keki/src/main/consumer/store/ConsumerStoreMainActivity.kt
package com.umc.keki.src.main.consumer.store
========
package com.umc.keki.src.storeMain
>>>>>>>> origin/develop:app/src/main/java/com/umc/keki/src/storeMain/StoreMainActivity.kt


import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.keki.R
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerStoreMainBinding
import com.umc.keki.util.viewpager.storemain.StoreMainDialog
import com.umc.keki.util.viewpager.storemain.StoreMainVPAdapter

class ConsumerStoreMainActivity : BaseActivity<ActivityConsumerStoreMainBinding>(ActivityConsumerStoreMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getNickname()
        tabSetting()
        back()
        infoClick()
    }

    private fun getNickname(){
        var nickname = intent.getStringExtra("nickname")
        Log.d("nickname", nickname!!)
        binding.tvStoreName.text = nickname
    }

    private fun tabSetting(){
        val storeMainVPAdapter = StoreMainVPAdapter(this)
        binding.vpStore.adapter = storeMainVPAdapter

        val tabIconArray = arrayOf(R.drawable.ic_store_main_grid, R.drawable.ic_store_main_cake)

        TabLayoutMediator(binding.tabStore, binding.vpStore){tab, position ->
            tab.setIcon(tabIconArray[position])
        }.attach()

        tabClickColor()
    }

    private fun tabClickColor(){
        binding.tabStore.getTabAt(0)?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))

        binding.tabStore.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_unselect,null))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun back(){
        binding.ivBack.setOnClickListener{
            finish()
        }
    }

    private fun infoClick(){
        binding.ivInfo.setOnClickListener{
            StoreMainDialog(this).show()
        }
    }
}


