package com.codepatissier.keki.src.main.seller.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentSellerMyPageBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.NoticeActivity
import com.codepatissier.keki.src.main.seller.mypage.profileEdit.SellerProfileEditActivity
import com.google.firebase.storage.FirebaseStorage
import com.codepatissier.keki.util.dialog.LogoutDialog
import com.codepatissier.keki.util.dialog.WithdrawalDialog

class SellerMyPageFragment : BaseFragment<FragmentSellerMyPageBinding>
    (FragmentSellerMyPageBinding::bind, R.layout.fragment_seller_my_page) {
    var fbStorage : FirebaseStorage?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fbStorage = FirebaseStorage.getInstance()

        profileEditClicked()
        noticeClicked()

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
}