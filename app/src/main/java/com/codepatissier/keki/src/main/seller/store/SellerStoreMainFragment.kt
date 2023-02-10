package com.codepatissier.keki.src.main.seller.store

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerStoreMainBinding
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageService
import com.codepatissier.keki.src.main.seller.mypage.SellerMyPageView
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainDialog
import com.codepatissier.keki.util.viewpager.storemain.seller.SellerStoreMainTabAdapter
import com.google.firebase.storage.FirebaseStorage

class SellerStoreMainFragment : BaseFragment<FragmentSellerStoreMainBinding>(FragmentSellerStoreMainBinding::bind, R.layout.fragment_seller_store_main), SellerMyPageView{
    var storeIdx: Long = 1
    var fbStorage : FirebaseStorage?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbStorage = FirebaseStorage.getInstance()

        showLoadingDialog(requireContext())
        SellerMyPageService(this).tryGetSellerMyPage()

        infoClick()
    }

    private fun tabSetting(){
        val sellerStoreMainTabAdapter = SellerStoreMainTabAdapter(requireActivity(), storeIdx)
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

    private fun infoClick(){
        binding.ivInfo.setOnClickListener{
            SellerStoreMainDialog(requireContext()).show()
        }
    }

    override fun onGetMyPageSuccess(response: SellerMyPageResponse) {
        storeIdx = response.result.storeIdx
        tabSetting()
        binding.tvStoreName.text = response.result.nickname
        binding.tvStoreDetail.text = response.result.introduction

        val defaultImg = R.drawable.bg_oval_light_yellow
        val imageView = binding.ivProfile
        if(response.result.storeImgUrl != null) {
            // 프로필 이미지 띄우기
            var storageRef = fbStorage?.reference?.child(response.result.storeImgUrl)
            storageRef?.downloadUrl?.addOnCompleteListener {
                if (it.isSuccessful) {
                    Glide.with(this)
                        .load(it.result)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .circleCrop()
                        .into(imageView)
                    dismissLoadingDialog()
                }else{
                    dismissLoadingDialog()
                }
            }
        }else{
            dismissLoadingDialog()
        }
        setViewMore(binding.tvStoreDetail, binding.tvViewMore)

    }

    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    private fun setViewMore(contentTextView: TextView, viewMoreTextView:TextView){
        contentTextView.post{
            val lineCount = contentTextView.layout.lineCount
            if (lineCount > 0) {
                if (contentTextView.layout.getEllipsisCount(lineCount - 1) > 0) {
                    viewMoreTextView.visibility = View.VISIBLE

                    viewMoreTextView.setOnClickListener {
                        contentTextView.maxLines = Int.MAX_VALUE
                        viewMoreTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}



