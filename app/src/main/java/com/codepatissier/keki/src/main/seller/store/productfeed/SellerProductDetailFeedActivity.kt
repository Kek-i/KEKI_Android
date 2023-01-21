package com.codepatissier.keki.src.main.seller.store.productfeed

import android.os.Bundle
import android.widget.PopupMenu
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivitySellerProductDetailFeedBinding
import com.codepatissier.keki.databinding.DialogSellerProductDetailFeedDeleteBinding
import com.codepatissier.keki.util.viewpager.storemain.SellerProductDetailFeedDeleteDialog

class SellerProductDetailFeedActivity : BaseActivity<ActivitySellerProductDetailFeedBinding> (ActivitySellerProductDetailFeedBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToStoreMain()
        selectMenu()

    }


    private fun navigateToStoreMain(){
        binding.ivProductFeedLeftChevron.setOnClickListener {
            finish()
        }
    }

    private fun selectMenu(){
        binding.ivProductFeedSetting.setOnClickListener{
            showMenu()
        }
    }

    private fun showMenu() {
        var popupMenu = PopupMenu(applicationContext, binding.ivProductFeedSetting)
        menuInflater?.inflate(R.menu.seller_product_detail_feed_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.seller_product_detail_feed_menu1 -> {
                    return@setOnMenuItemClickListener true
                }
                R.id.seller_product_detail_feed_menu2 -> {
                    SellerProductDetailFeedDeleteDialog(this).show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
    }


}