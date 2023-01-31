package com.codepatissier.keki.src.main.consumer.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseFragment
import com.codepatissier.keki.databinding.FragmentConsumerMyPageBinding
import com.codepatissier.keki.src.main.consumer.mypage.notice.NoticeActivity
import com.codepatissier.keki.util.dialog.LogoutDialog
import com.codepatissier.keki.util.dialog.WithdrawalDialog
import com.codepatissier.keki.util.viewpager.storemain.StoreMainDialog

class ConsumerMyPageFragment : BaseFragment<FragmentConsumerMyPageBinding>
    (FragmentConsumerMyPageBinding::bind, R.layout.fragment_consumer_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileEditClicked()
        noticeClicked()
        logoutClicked()
        withdrawalClicked()
        conditionClicked()
        personalInfoClicked()
    }

    private fun profileEditClicked(){
        binding.cslProfileEdit.setOnClickListener{
            val intent = Intent(context, ConsumerProfileEditActivity::class.java)
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
            val intent = Intent(context, ConsumerConditionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun personalInfoClicked(){
        binding.tvPersonalInfo.setOnClickListener{
            val intent = Intent(context, ConsumerPersonalInfoActivity::class.java)
            startActivity(intent)
        }
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