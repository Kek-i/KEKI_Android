package com.umc.keki.src.storeMain


import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.umc.keki.R
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityStoreMainBinding
import com.umc.keki.util.viewpager.storemain.StoreMainDialog
import com.umc.keki.util.viewpager.storemain.StoreMainVPAdapter

class StoreMainActivity : BaseActivity<ActivityStoreMainBinding>(ActivityStoreMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tabSetting()
        back()
        infoClick()
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



