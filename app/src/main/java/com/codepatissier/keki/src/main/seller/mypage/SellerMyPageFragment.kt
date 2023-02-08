package com.codepatissier.keki.src.main.seller.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerMyPageBinding
import com.codepatissier.keki.src.main.consumer.mypage.ConsumerMyPageService
import com.codepatissier.keki.src.main.consumer.mypage.model.ConsumerMyPageResponse
import com.codepatissier.keki.src.main.consumer.mypage.notice.NoticeActivity
import com.codepatissier.keki.src.main.seller.mypage.model.SellerMyPageResponse
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.SellerProfileEditActivity
import com.google.firebase.storage.FirebaseStorage
import com.codepatissier.keki.util.dialog.LogoutDialog
import com.codepatissier.keki.util.dialog.WithdrawalDialog

class SellerMyPageFragment : BaseFragment<FragmentSellerMyPageBinding>
    (FragmentSellerMyPageBinding::bind, R.layout.fragment_seller_my_page), SellerMyPageView {
    var fbStorage : FirebaseStorage?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()
        showLoadingDialog(requireContext())
        profileEditClicked()
        noticeClicked()
        SellerMyPageService(this).tryGetSellerMyPage()
        logoutClicked()
        withdrawalClicked()
        conditionClicked()
        personalInfoClicked()
    }

    private fun profileEditClicked(){
        binding.cslProfileEdit.setOnClickListener{
            val intent = Intent(context, SellerProfileEditActivity::class.java)
            startActivity(intent)
        }
    }

    private fun noticeClicked(){
        binding.cslNotice.setOnClickListener{
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun conditionClicked(){
        binding.tvCondition.setOnClickListener{
            val intent = Intent(context, SellerConditionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun personalInfoClicked(){
        binding.tvPersonalInfo.setOnClickListener{
            val intent = Intent(context, SellerPersonalInfoActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onResume() {
        super.onResume()
        SellerMyPageService(this).tryGetSellerMyPage()
    }

    private fun logoutClicked() {
        binding.tvLogout.setOnClickListener {
            LogoutDialog(requireContext()).show()
        }
    }

    private fun withdrawalClicked() {
        binding.tvWithdrawal.setOnClickListener {
            WithdrawalDialog(requireContext()).show()
        }
    }

    override fun onGetMyPageSuccess(response: SellerMyPageResponse) {
        val defaultImg = R.drawable.bg_oval_off_white
        val imageView = binding.ivProfile

        val nim = getString(R.string.my_page_tv_nickname)
        binding.tvNickName.text = response.result.nickname + nim

        if(response.result.storeImgUrl != null){
            // 이미지 가져오기
            var storageRef = fbStorage?.reference?.child(response.result.storeImgUrl)
            storageRef?.downloadUrl?.addOnCompleteListener {
                if(it.isSuccessful){
                    Glide.with(this)
                        .load(it.result)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .fallback(defaultImg)
                        .circleCrop()
                        .into(imageView)
                }
            }
        }
        dismissLoadingDialog()
    }

    override fun onGetMyPageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}