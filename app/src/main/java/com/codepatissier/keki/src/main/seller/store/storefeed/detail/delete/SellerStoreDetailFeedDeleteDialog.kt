package com.codepatissier.keki.src.main.seller.store.storefeed.detail.delete

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogDeleteSellerStoreFeedDetailBinding
import com.codepatissier.keki.src.SellerMainActivity
import com.codepatissier.keki.src.main.seller.store.SellerStoreMainFragment
import com.codepatissier.keki.src.main.seller.store.storefeed.detail.SellerStoreFeedDetailActivity

class SellerStoreDetailFeedDeleteDialog(context: Context): Dialog(context), SellerStoreFeedDetailDeleteView {

    private lateinit var binding: DialogDeleteSellerStoreFeedDetailBinding
    var postIdx : Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDeleteSellerStoreFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())

        btnFalse()
        btnDelete()
    }

    private fun btnFalse(){
        // 취소
        binding.btnDeleteStoreNone.setOnClickListener {
            dismiss()
        }
    }

    private fun btnDelete(){
        // 삭제하기
        binding.btnDeleteStoreOk.setOnClickListener {
            SellerStoreFeedDetailDeleteService(this).tryDeleteSellerStoreFeedDetailDeleteInterface(postIdx!!)
        }
    }

    override fun onDeleteSellerStoreFeedDetailDeleteSuccess(response: BaseResponse) {
        Toast.makeText(context, "삭제하기를 성공했습니다", Toast.LENGTH_SHORT).show()
        dismiss()
        val intent = Intent(context, SellerMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.context.startActivity(intent)
    }

    override fun onDeleteSellerStoreFeedDetailDeleteFailure(message: String) {
        Toast.makeText(context, "삭제하기를 실패했습니다", Toast.LENGTH_SHORT).show()
        dismiss()
    }
}