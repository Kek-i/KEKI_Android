package com.codepatissier.keki.util.viewpager.storemain.seller

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.codepatissier.keki.config.ApplicationClass
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.DialogSellerProductDetailFeedDeleteBinding
import com.codepatissier.keki.src.main.seller.store.productfeed.SellerProductFeedDetailService
import com.codepatissier.keki.src.main.seller.store.productfeed.SellerProductFeedDetailView
import com.codepatissier.keki.src.main.seller.store.productfeed.model.SellerProductFeedDetailResponse


class SellerProductDetailFeedDeleteDialog(context: Context): Dialog(context), SellerProductFeedDetailView {
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
                this.dismiss()
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
        dismiss()
    }

    override fun onDelProductFeedFailure(message: String) {
        TODO("Not yet implemented")
    }
}

