package com.codepatissier.keki.src.main.consumer.calendar

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.databinding.ActivityConsumerCalendarAddBinding
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

        // 해시태그별로 ClickListener 설정
        setClickListenerToHashTag(binding.tvHashtagFriend)
        setClickListenerToHashTag(binding.tvHashtagFamily)
        setClickListenerToHashTag(binding.tvHashtagLover)
        setClickListenerToHashTag(binding.tvHashtagAnniversary)
        setClickListenerToHashTag(binding.tvHashtagBirthday)
        setClickListenerToHashTag(binding.tvHashtagEmployment)
        setClickListenerToHashTag(binding.tvHashtagGraduationExhibition)
        setClickListenerToHashTag(binding.tvHashtagParty)
        // 정렬용 태그 ClickListener 설정
        setClickListenerToSortedTag(binding.tvFirstTag, 1)
        setClickListenerToSortedTag(binding.tvSecondTag, 2)
        setClickListenerToSortedTag(binding.tvThirdTag, 3)
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

    private fun setClickListenerToHashTag(tvHashtag: TextView) {
        val firstTag = binding.tvFirstTag
        val secondTag = binding.tvSecondTag
        val thirdTag = binding.tvThirdTag

        // 해당 태그 선택
        tvHashtag.setOnClickListener {
            // 첫번째 태그 자리가 비어있다면 == 아무것도 선택하지 않은 상태
            if(firstTag.visibility == GONE) {
                firstTag.text = tvHashtag.text
                setBackgroundResourceOfTag(firstTag)
                tvHashtag.visibility = GONE
                firstTag.visibility = VISIBLE
            }
            // 두번째 태그 자리가 비어있다면 == 첫번째 태그 사용 중
            else if(secondTag.visibility == GONE) {
                secondTag.text = tvHashtag.text
                setBackgroundResourceOfTag(secondTag)
                tvHashtag.visibility = GONE
                secondTag.visibility = VISIBLE
            }
            // 세번째 태그 자리가 비어있다면 == 두,세번째 태그 사용 중
            else if(thirdTag.visibility == GONE) {
                thirdTag.text = tvHashtag.text
                setBackgroundResourceOfTag(thirdTag)
                tvHashtag.visibility = GONE
                thirdTag.visibility = VISIBLE
            }
            // 모든 태그가 사용 중이라면
            else {
                this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }

    }

    private fun setClickListenerToSortedTag(sortedTag: TextView, tagNumber: Int) {
        // 이미 선택된 상태에서 클릭했다면 -> 클릭된 거 지우기 : 색깔 반환, , 앞쪽부터 채워지게 정렬태그 이동, 정렬태그 GONE 처리, 해당 tv VISIBLE 처리
        sortedTag.setOnClickListener {
            when (sortedTag.text) {
                // 원래 태그 보여주기
                binding.tvHashtagFriend.text -> binding.tvHashtagFriend.visibility = VISIBLE
                binding.tvHashtagFamily.text -> binding.tvHashtagFamily.visibility = VISIBLE
                binding.tvHashtagLover.text -> binding.tvHashtagLover.visibility = VISIBLE
                binding.tvHashtagAnniversary.text -> binding.tvHashtagAnniversary.visibility = VISIBLE
                binding.tvHashtagBirthday.text -> binding.tvHashtagBirthday.visibility = VISIBLE
                binding.tvHashtagEmployment.text -> binding.tvHashtagEmployment.visibility = VISIBLE
                binding.tvHashtagGraduationExhibition.text -> binding.tvHashtagGraduationExhibition.visibility = VISIBLE
                binding.tvHashtagParty.text -> binding.tvHashtagParty.visibility = VISIBLE
            }
            // 색 반환
            restoreBackgroundResourceOfTag(sortedTag)
            // 자리 이동
            moveTagPlace(tagNumber)
        }
    }

    private fun moveTagPlace(tagNumber: Int) {
        val firstTag = binding.tvFirstTag
        val secondTag = binding.tvSecondTag
        val thirdTag = binding.tvThirdTag
        
        when(tagNumber) {
            1 -> {
                firstTag.text = ""
                if (secondTag.visibility != GONE) {
                    firstTag.text = secondTag.text
                    firstTag.background = secondTag.background
                    secondTag.text = ""
                    if (thirdTag.visibility != GONE) {
                        thirdTag.visibility = GONE
                        secondTag.text = thirdTag.text
                        secondTag.background = thirdTag.background
                        thirdTag.text = ""
                    } else secondTag.visibility = GONE
                } else firstTag.visibility = GONE
            }
            2 -> {
                secondTag.text = ""
                if (thirdTag.visibility != GONE) {
                    secondTag.background = thirdTag.background
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

    private fun restoreBackgroundResourceOfTag(tag: TextView) {
        when (tag.background.constantState) {
            // off_white를 사용했다면
            ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_radius_13_off_white, null)!!.constantState -> bOffWhiteIsUsed = false
            // very_light_pink를 사용했다면
            ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_radius_13_very_light_pink, null)!!.constantState -> bVeryLightPinkIsUsed = false
            // light_peach_2를 사용했다면
            ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_radius_13_light_peach_2, null)!!.constantState -> bLightPeach2IsUsed = false
        }
    }

    private fun setBackgroundResourceOfTag(tvHashtagSorted: TextView) {
        // off_white를 쓸 수 있다면
        if (!bOffWhiteIsUsed) {
            tvHashtagSorted.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
            bOffWhiteIsUsed = true
        }
        // very_light_pink를 쓸 수 있다면
        else if (!bVeryLightPinkIsUsed) {
            tvHashtagSorted.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
            bVeryLightPinkIsUsed = true
        }
        // light_peach_2를 쓸 수 있다면
        else if (!bLightPeach2IsUsed) {
            tvHashtagSorted.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
            bLightPeach2IsUsed = true
        }
    }

    private fun showDatePickerDialog() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val selectedDate: Calendar = Calendar.getInstance()

        // date edittext에 값이 있으면 해당 값으로 날짜 지정해줘야 함. 없으면 현재 날짜로 지정.
        if(binding.etSelectDate.text.isNotEmpty()) {
            selectedDate.time = dateFormat.parse(binding.etSelectDate.text.toString())
        }

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
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