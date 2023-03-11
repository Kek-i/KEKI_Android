package com.codepatissier.keki.util.viewpager.storemain.seller

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogSellerProductDetailFeedDeleteBinding
import com.codepatissier.keki.src.SellerMainActivity
import com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.SellerProductDetailFeedActivity
import com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.SellerProductFeedDetailService
import com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.SellerProductFeedDetailView
import com.codepatissier.keki.src.main.seller.store.productfeed.productdetail.model.SellerProductFeedDetailResponse


class SellerProductDetailFeedDeleteDialog(context: Context): Dialog(context),
    SellerProductFeedDetailView {
    private var dessertIdx = ApplicationClass.sSharedPreferences.getString(ApplicationClass.DessertIdx, "1")
    private lateinit var  binding : DialogSellerProductDetailFeedDeleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSellerProductDetailFeedDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.2f)
        clickCancelBtn()
        checkBtn()



    }

    override fun show(){
        if(!this.isShowing) super.show()
    }

    override fun onStop() {
        dismiss()
    }

    private fun clickCancelBtn() {
        binding.btnCancel.setOnClickListener{
            this.dismiss()
        }
    }

    private fun checkBtn(){
        binding.btnDelete.setOnClickListener{
            SellerProductFeedDetailService(this).tryDelProductFeedDetail(dessertIdx = dessertIdx!!.toLong())
            Toast.makeText(context, "삭제 완료", Toast.LENGTH_SHORT).show()
            this.dismiss()
            val intent = Intent(context, SellerMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            }
        }

    override fun onGetProductFeedSuccess(response: SellerProductFeedDetailResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPatchProductFeedSuccess(response: BaseResponse) {
        TODO("Not yet implemented")
    }

    override fun onPatchProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDelProductFeedSuccess(response: BaseResponse) {
        ApplicationClass.userInfo.remove("DessertIdx")
        ApplicationClass.userInfo.commit()
        SellerProductDetailFeedActivity().finish()
        dismiss()
    }

    override fun onDelProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }
}

