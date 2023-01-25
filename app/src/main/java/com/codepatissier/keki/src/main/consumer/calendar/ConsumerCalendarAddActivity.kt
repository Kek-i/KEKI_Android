package com.codepatissier.keki.src.main.consumer.calendar

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.get
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerCalendarAddBinding
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

class ConsumerCalendarAddActivity : BaseActivity<ActivityConsumerCalendarAddBinding>(
    ActivityConsumerCalendarAddBinding::inflate) {
    // 기념일 종류 메뉴 열고 닫기 여부
    private var openForLayoutOfType: Boolean = false
    // 무슨색 태그가 사용 중인지
    private var bOffWhiteIsUsed: Boolean = false
    private var bVeryLightPinkIsUsed: Boolean = false
    private var bLightPeach2IsUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 뷰, 버튼 Click Listener 설정
        setClickListenerToLayoutOfType()
        setClickListenerToDatePicker()
        setClickListenerToBackBtn()

        val hashTagArray = listOf<String>("친구", "가족", "졸업", "기념일", "크리스마스", "합격", "파티")

        // 해시태그 리스트 받아와서 chip 생성 및 Click Listener 설정
        createChip(hashTagArray)
        // 정렬용 태그 Click Listener 설정
        setClickListenerToSortedTag(binding.chipFirstSortedTag, 1)
        setClickListenerToSortedTag(binding.chipSecondSortedTag, 2)
        setClickListenerToSortedTag(binding.chipThirdSortedTag, 3)
    }

    private fun setClickListenerToLayoutOfType() {
        // 화살표 있는 레이아웃 눌렀을 때
        binding.layoutCloseCalendarAddType.setOnClickListener {
            // 선택 메뉴 펼쳐져 있을 때
            if(openForLayoutOfType) {
                binding.layoutOpenCalendarAddType.visibility = GONE
                openForLayoutOfType = false
            }
            // 선택 메뉴 펼쳐져 있지 않을 때
            else {
                binding.layoutOpenCalendarAddType.visibility = VISIBLE
                openForLayoutOfType = true
            }
        }
        // 디데이 클릭
        binding.tvTypeDday.setOnClickListener {
            binding.layoutOpenCalendarAddType.visibility = GONE
            openForLayoutOfType = false
            binding.tvSelectType.text = binding.tvTypeDday.text
        }
        // 날짜수 클릭
        binding.tvTypeNumberOfDays.setOnClickListener {
            binding.layoutOpenCalendarAddType.visibility = GONE
            openForLayoutOfType = false
            binding.tvSelectType.text = binding.tvTypeNumberOfDays.text
        }
        // 매년 반복 클릭
        binding.tvTypeRepeatEveryYear.setOnClickListener {
            binding.layoutOpenCalendarAddType.visibility = GONE
            openForLayoutOfType = false
            binding.tvSelectType.text = binding.tvTypeRepeatEveryYear.text
        }
    }

    private fun setClickListenerToDatePicker() {
        binding.ibCalendarSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarAddBack.setOnClickListener {
            finish()
        }
    }

    private fun createChip(hashTagArray: List<String>) {
        // scroll view 필요한가? 테스트해보기
        for (i in hashTagArray.indices) {
            val chip = layoutInflater.inflate(
                R.layout.single_chip_layout,
                binding.chipGroupHashtag,
                false
            ) as Chip
            chip.id = View.generateViewId()
            Log.d("id", "${chip.id}")
            chip.text = "# ${hashTagArray[i]}"
            binding.chipGroupHashtag.addView(chip)

            val firstTag = binding.chipFirstSortedTag
            val secondTag = binding.chipSecondSortedTag
            val thirdTag = binding.chipThirdSortedTag

            // 선택용 태그 ClickListener 설정
            chip.setOnClickListener {
                // 첫번째 태그 자리가 비어있다면 == 아무것도 선택하지 않은 상태
                if(firstTag.visibility == GONE) {
                    firstTag.text = chip.text
                    setBackgroundColor(firstTag)
                    chip.visibility = GONE
                    chip.isChecked = true
                    firstTag.visibility = VISIBLE
                }
                // 두번째 태그 자리가 비어있다면 == 첫번째 태그 사용 중
                else if(secondTag.visibility == GONE) {
                    secondTag.text = chip.text
                    setBackgroundColor(secondTag)
                    chip.visibility = GONE
                    chip.isChecked = true
                    secondTag.visibility = VISIBLE
                }
                // 세번째 태그 자리가 비어있다면 == 두,세번째 태그 사용 중
                else if(thirdTag.visibility == GONE) {
                    thirdTag.text = chip.text
                    setBackgroundColor(thirdTag)
                    chip.visibility = GONE
                    chip.isChecked = true
                    thirdTag.visibility = VISIBLE
                }
                // 모든 태그가 사용 중이라면
                else {
                    this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
                }
            }
        }
    }

    private fun setClickListenerToSortedTag(sortedTag: Chip, tagNumber: Int) {
        // 이미 선택된 상태에서 클릭했다면
        sortedTag.setOnClickListener {
            // 원래 태그 보여주기
            for (id in binding.chipGroupHashtag.checkedChipIds) {
                val chip: Chip = binding.chipGroupHashtag.findViewById(id)
                if(chip.text.equals(sortedTag.text)) {
                    chip.visibility = VISIBLE
                    chip.isChecked = false
                }
            }
            // 색 반환
            returnBackgroundColor(sortedTag)
            // 자리 이동
            moveTagPlace(tagNumber)
        }
    }

    private fun moveTagPlace(tagNumber: Int) {
        val firstTag = binding.chipFirstSortedTag
        val secondTag = binding.chipSecondSortedTag
        val thirdTag = binding.chipThirdSortedTag

        when(tagNumber) {
            1 -> {
                firstTag.text = ""
                if (secondTag.visibility != GONE) {
                    firstTag.text = secondTag.text
                    firstTag.chipBackgroundColor = secondTag.chipBackgroundColor
                    secondTag.text = ""
                    if (thirdTag.visibility != GONE) {
                        thirdTag.visibility = GONE
                        secondTag.text = thirdTag.text
                        secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                        thirdTag.text = ""
                    } else secondTag.visibility = GONE
                } else firstTag.visibility = GONE
            }
            2 -> {
                secondTag.text = ""
                if (thirdTag.visibility != GONE) {
                    secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                    thirdTag.visibility = GONE
                    secondTag.text = thirdTag.text
                    thirdTag.text = ""
                } else secondTag.visibility = GONE
            }
            3 -> {
                thirdTag.visibility = GONE
                thirdTag.text = ""
            }
        }
    }

    private fun returnBackgroundColor(tag: Chip) {
        when (tag.chipBackgroundColor) {
            // off_white를 사용했다면
            resources.getColorStateList(R.color.off_white, null) -> {
                Log.d("off_white", "")
                bOffWhiteIsUsed = false
            }
            // very_light_pink를 사용했다면
            resources.getColorStateList(R.color.very_light_pink, null) -> {
                Log.d("very_light_pink", "")
                bVeryLightPinkIsUsed = false
            }
            // light_peach_2를 사용했다면
            resources.getColorStateList(R.color.light_peach_2, null) -> {
                Log.d("light_peach_2", "")
                bLightPeach2IsUsed = false
            }
        }
    }

    private fun setBackgroundColor(chipSortedTag: Chip) {
        // off_white를 쓸 수 있다면
        if (!bOffWhiteIsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.off_white)
            bOffWhiteIsUsed = true
        }
        // very_light_pink를 쓸 수 있다면
        else if (!bVeryLightPinkIsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.very_light_pink)
            bVeryLightPinkIsUsed = true
        }
        // light_peach_2를 쓸 수 있다면
        else if (!bLightPeach2IsUsed) {
            chipSortedTag.setChipBackgroundColorResource(R.color.light_peach_2)
            bLightPeach2IsUsed = true
        }
    }

    private fun showDatePickerDialog() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val selectedDate: Calendar = Calendar.getInstance()

        // date edittext에 값이 있으면 해당 값으로 날짜 지정해줘야 함. 없으면 현재 날짜로 지정.
        if(binding.etSelectDate.text.isNotEmpty()) {
            selectedDate.time = dateFormat.parse(binding.etSelectDate.text.toString()) as Date
        }

        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            val strDate: String = dateFormat.format(selectedDate.time)
            binding.etSelectDate.setText(strDate)
            binding.etSelectDate.setTextColor(Color.BLACK)
        },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        ).apply {
            // 날짜수로 선택한 경우 미래 날짜는 입력 불가능
            if(binding.tvSelectType.text.equals(binding.tvTypeNumberOfDays.text))
                datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }
}