package com.codepatissier.keki.src.main.seller.store


import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerStoreMainBinding
import com.codepatissier.keki.util.viewpager.storemain.StoreMainDialog
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainTabAdapter

class SellerStoreMainActivity : BaseActivity<ActivitySellerStoreMainBinding>(ActivitySellerStoreMainBinding::inflate){
    var storeIdx: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getStoreIdx()
        tabSetting()
        back()
        infoClick()
    }

    private fun getStoreIdx(){
        storeIdx = intent.getLongExtra("storeIdx", 1)
    }

    private fun tabSetting(){
        val sellerStoreMainTabAdapter = SellerStoreMainTabAdapter(this, storeIdx)
        binding.vpStore.adapter = sellerStoreMainTabAdapter

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
                tab?.icon?.setTint(ResourcesCompat.getColor(getResources(),R.color.tab_select,null))
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
            StoreMainDialog(this).setStoreIdx(storeIdx)
            StoreMainDialog(this).show()
        }
    }

//    private fun setViewMore(contentTextView: TextView, viewMoreTextView:TextView){
//        contentTextView.post{
//            val lineCount = contentTextView.layout.lineCount
//            if (lineCount > 0) {
//                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
//                    viewMoreTextView.visibility = View.VISIBLE
//
//                    viewMoreTextView.setOnClickListener {
//                        contentTextView.maxLines = Int.MAX_VALUE
//                        viewMoreTextView.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    }
}



