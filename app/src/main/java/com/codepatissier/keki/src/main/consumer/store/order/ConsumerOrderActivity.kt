package com.codepatissier.keki.src.main.consumer.store.order

import android.app.DatePickerDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerOrderBinding
import com.codepatissier.keki.util.recycler.storefeedadd.FeedImageAdapter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ConsumerOrderActivity : BaseActivity<ActivityConsumerOrderBinding>(ActivityConsumerOrderBinding::inflate) {

    private val maxImage = 5
    private lateinit var feedImageAdapter: FeedImageAdapter
    private var feedImageUriList = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigateToStoreMain()
        setListenerToDatePicker()
        initFeedImage()
    }

    private fun setListenerToDatePicker(){
        binding.ibOrderSelectDate.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog(){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val selectedDate: Calendar = Calendar.getInstance()

        // date edittxt에 값이 있으면 해당 값으로 날짜 지정. 없으면 현재 날짜로 지정
        if(binding.etSelectDate.text.isNotEmpty()){
            selectedDate.time = dateFormat.parse(binding.etSelectDate.text.toString()) as Date
        }

        val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            val strDate: String = dateFormat.format(selectedDate.time)
            binding.etSelectDate.setText(strDate)
            binding.etSelectDate.setTextColor(Color.BLACK)
            binding.tvDateErrorNoInput.visibility = GONE
        },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )

        dialog.show()
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.soft_pink, null))
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.pinkish, null))
    }

    private fun initFeedImage(){
        // rv item 삭제 시 깜빡임 방지
        binding.rvFeedImage.itemAnimator = null

        binding.ibAddImage.setOnClickListener{
            if(maxImage <= feedImageUriList.size){
                showCustomToast(getString(R.string.seller_feed_add_max_image))
            }else{
                val max = maxImage - feedImageUriList.size

                TedImagePicker.with(this)
                    .mediaType(MediaType.IMAGE)
                    .showCameraTile(false)
                    .title(getString(R.string.seller_feed_add_image_picker_title))
                    .buttonTextColor(R.color.black)
                    .buttonBackground(R.color.white)
                    .max(max, "최대 ${max}개 선택할 수 있습니다.")
                    .startMultiImage { uriList ->
                        if(uriList.isNotEmpty()){
                            for(uri in uriList){
                                feedImageUriList.add(uri)
                            }
                            feedImageAdapter = FeedImageAdapter(feedImageUriList, this)
                            feedImageAdapter.setItemClickListener(object: FeedImageAdapter.ImgDeleteBtnClickListener{
                                override fun onClickDeleteBtn(position: Int) {
                                    feedImageAdapter.dataList.removeAt(position)
                                    feedImageAdapter.notifyItemRemoved(position)
                                    feedImageAdapter.notifyItemRangeRemoved(position, feedImageUriList.size)
                                    feedImageUriList = feedImageAdapter.dataList
                                }

                            })

                            binding.rvFeedImage.adapter = feedImageAdapter
                            val mLinearLayoutManager = LinearLayoutManager(this)
                            mLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                            // 가장 최근에 추가한 이미지가 맨왼쪽에 위치
                            mLinearLayoutManager.reverseLayout = true
                            mLinearLayoutManager.stackFromEnd = true
                            binding.rvFeedImage.layoutManager = mLinearLayoutManager
                        }
                    }
            }
        }
    }

    private fun navigateToStoreMain(){
        binding.ivOrderLeftChevron.setOnClickListener {
            finish()
        }
    }

}