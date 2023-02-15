package com.codepatissier.keki.src.main.consumer.calendar.calendarmodify

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.children
import com.codepatissier.keki.R
import com.codepatissier.keki.config.BaseActivity
import com.codepatissier.keki.config.BaseResponse
import com.codepatissier.keki.databinding.ActivityConsumerCalendarAddBinding
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.ConsumerCalendarModifyViewResponse
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.ResultCalendarModifyView
import com.codepatissier.keki.src.main.consumer.calendar.calendarmodify.model.UpdateCalendarRequest
import com.google.android.material.chip.Chip
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

// 캘린더 수정 화면은 캘린더 추가 화면 그대로 사용
class ConsumerCalendarModifyActivity : BaseActivity<ActivityConsumerCalendarAddBinding>
    (ActivityConsumerCalendarAddBinding::inflate), ConsumerCalendarModifyView {
    private var calendarIdx: Long = 0
    // 무슨색 태그가 사용 중인지
    private var bOffWhiteIsUsed: Boolean = false
    private var bVeryLightPinkIsUsed: Boolean = false
    private var bLightPeach2IsUsed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCalendarIdx()
        // 레이아웃 뷰, 버튼 Click Listener 설정
        binding.tvCalendarAddMainText.text = getString(R.string.calendar_modify_main_text)
        setListenerToLayoutOfType()
        setListenerToDatePicker()
        setListenerToBackBtn()
        setListenerForFocus()
        setListenerToCompletionBtn()
        // 정렬용 태그 Click Listener 설정
        setListenerToSortedTag(binding.chipFirstSortedTag, 1)
        setListenerToSortedTag(binding.chipSecondSortedTag, 2)
        setListenerToSortedTag(binding.chipThirdSortedTag, 3)

        // 수정할 기념일 상세, 해시태그 목록 서버에서 가져오기
        showLoadingDialog(this)
        ConsumerCalendarModifyService(this).tryGetCalendarModifyView(calendarIdx)
    }

    private fun initCalendarIdx() {
        calendarIdx = intent.getLongExtra("calendarIdx", 0)
    }

    override fun onUpdateCalendarSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        binding.chipGroupHashtag.clearCheck()
        finish()
    }

    override fun onUpdateCalendarFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetCalendarModifyViewSuccess(response: ConsumerCalendarModifyViewResponse) {
        dismissLoadingDialog()

        // 해시태그 전체 목록
        val hashTagList = mutableListOf<String>()
        for(tag in response.result.addHashTags) {
            hashTagList.add(tag["calendarHashTag"]!!)
        }
        // 사용자가 선택한 해시태그 목록
        val selectedTagList = mutableListOf<String>()
        for(tag in response.result.hashTags) {
            selectedTagList.add(tag["calendarHashTag"]!!)
        }
        // 해시태그 전체 목록 받아와서 chip 생성 및 Click Listener 설정
        createChip(hashTagList)
        // 기존 기념일 정보 init
        initCalendar(response.result, selectedTagList)
    }

    override fun onGetCalendarModifyViewFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    private fun initCalendar(result: ResultCalendarModifyView, selectedTagList: List<String>) {
        // 기념일 기존 정보
        binding.tvSelectType.text = result.kindOfCalendar
        binding.etTitle.setText(result.title)
        binding.etSelectDate.setTextColor(getColor(R.color.black))
        binding.etSelectDate.setText(result.date)
        // 해시태그
        if(selectedTagList.isNotEmpty()) {
            val firstTag = binding.chipFirstSortedTag
            val secondTag = binding.chipSecondSortedTag
            val thirdTag = binding.chipThirdSortedTag

            for(tag in selectedTagList) {
                val tagName = "# $tag"

                // 첫번째 태그 자리가 비어있다면 == 아무것도 선택하지 않은 상태
                if(firstTag.visibility == View.GONE) {
                    firstTag.setTextColor(getColor(R.color.black))
                    firstTag.text = tagName
                    setBackgroundColor(firstTag)
                    setChipToGONE(tagName)
                    firstTag.isChecked = false
                    firstTag.visibility = View.VISIBLE
                }
                // 두번째 태그 자리가 비어있다면 == 첫번째 태그 사용 중
                else if(secondTag.visibility == View.GONE) {
                    secondTag.setTextColor(getColor(R.color.black))
                    secondTag.text = tagName
                    setBackgroundColor(secondTag)
                    setChipToGONE(tagName)
                    secondTag.isChecked = false
                    secondTag.visibility = View.VISIBLE
                }
                // 세번째 태그 자리가 비어있다면 == 두,세번째 태그 사용 중
                else if(thirdTag.visibility == View.GONE) {
                    thirdTag.setTextColor(getColor(R.color.black))
                    thirdTag.text = tagName
                    setBackgroundColor(thirdTag)
                    setChipToGONE(tagName)
                    thirdTag.isChecked = false
                    thirdTag.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setChipToGONE(tagName: String) {
        for (child in binding.chipGroupHashtag.children) {
            val chip: Chip = binding.chipGroupHashtag.findViewById(child.id)
            if (chip.text.equals(tagName)) {
                chip.visibility = View.GONE
                chip.isChecked = true
            }
        }
    }

    private fun setListenerToCompletionBtn() {
        binding.btnCalendarAddCompletion.setOnClickListener {
            // 필수 입력 항목 다 채웠는지 확인
            if(checkAllRequiredInputIsEntered()) {
                val type = binding.tvSelectType.text.toString()
                val title = binding.etTitle.text.toString()
                val date = binding.etSelectDate.text.toString()
                val hashTags: MutableList<Map<String, String>> = mutableListOf()

                for(id in binding.chipGroupHashtag.checkedChipIds) {
                    val tag: Chip = binding.chipGroupHashtag.findViewById(id)
                    var tagText = tag.text.toString().replace(" ", "")
                    tagText = tagText.replace("#", "")
                    hashTags.add(mapOf("calendarHashTag" to tagText))
                }

                val updateCalendarRequest = UpdateCalendarRequest(
                    kindOfCalendar = type, title = title, date = date, hashTags = hashTags
                )
                showLoadingDialog(this)
                ConsumerCalendarModifyService(this).tryUpdateCalendar(calendarIdx, updateCalendarRequest)
            }
        }
    }

    private fun checkAllRequiredInputIsEntered(): Boolean {
        var check = true

        if(binding.tvSelectType.text.equals(getString(R.string.calendar_anniversary_type))) {
            binding.tvTypeErrorNoInput.visibility = View.VISIBLE
            check = false
        } else binding.tvTypeErrorNoInput.visibility = View.GONE
        if(binding.etTitle.text.isNullOrBlank()) {
            binding.tvTitleErrorNoInput.visibility = View.VISIBLE
            check = false
        } else binding.tvTitleErrorNoInput.visibility = View.GONE
        if(binding.etSelectDate.text.isNullOrEmpty()) {
            binding.tvDateErrorNoInput.visibility = View.VISIBLE
            check = false
        } else binding.tvDateErrorNoInput.visibility = View.GONE

        return check
    }

    private fun setListenerToLayoutOfType() {
        // 화살표 있는 레이아웃 눌렀을 때
        binding.layoutCloseCalendarAddType.setOnClickListener {
            outOfFocusOnTitle()
            // 선택 메뉴 펼쳐져 있을 때
            if(binding.layoutOpenCalendarAddType.visibility == View.VISIBLE) {
                binding.layoutOpenCalendarAddType.visibility = View.GONE
            }
            // 선택 메뉴 펼쳐져 있지 않을 때
            else if(binding.layoutOpenCalendarAddType.visibility == View.GONE) {
                binding.layoutOpenCalendarAddType.visibility = View.VISIBLE
            }
        }
        binding.ibOpenType.setOnClickListener {
            outOfFocusOnTitle()
            if(binding.layoutOpenCalendarAddType.visibility == View.VISIBLE) {
                binding.layoutOpenCalendarAddType.visibility = View.GONE
            }
            else if(binding.layoutOpenCalendarAddType.visibility == View.GONE) {
                binding.layoutOpenCalendarAddType.visibility = View.VISIBLE
            }
        }

        // 디데이 클릭
        binding.tvTypeDday.setOnClickListener {
            binding.tvTypeErrorNoInput.visibility = View.GONE
            outOfFocusOnTitle()
            binding.layoutOpenCalendarAddType.visibility = View.GONE
            binding.tvSelectType.text = binding.tvTypeDday.text
        }
        // 날짜수 클릭
        binding.tvTypeNumberOfDays.setOnClickListener {
            binding.tvTypeErrorNoInput.visibility = View.GONE
            outOfFocusOnTitle()
            binding.layoutOpenCalendarAddType.visibility = View.GONE
            binding.tvSelectType.text = binding.tvTypeNumberOfDays.text
        }
        // 매년 반복 클릭
        binding.tvTypeRepeatEveryYear.setOnClickListener {
            binding.tvTypeErrorNoInput.visibility = View.GONE
            outOfFocusOnTitle()
            binding.layoutOpenCalendarAddType.visibility = View.GONE
            binding.tvSelectType.text = binding.tvTypeRepeatEveryYear.text
        }
    }

    private fun setListenerToDatePicker() {
        binding.ibCalendarSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setListenerToBackBtn() {
        binding.ibCalendarAddBack.setOnClickListener {
            finish()
        }
    }

    private fun setListenerForFocus() {
        binding.etTitle.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(!binding.etTitle.text.isNullOrBlank())
                    binding.tvTitleErrorNoInput.visibility = View.GONE
                outOfFocusOnTitle()
                return@setOnEditorActionListener true
            } else return@setOnEditorActionListener false
        }
        binding.layoutOpenCalendarAddType.setOnClickListener {
            outOfFocusOnTitle()
        }
    }

    // edittext 포커스 해제
    private fun outOfFocusOnTitle() {
        // 키보드 내리기
        val inputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etTitle.windowToken, 0)
        // focus 해제
        binding.etTitle.clearFocus()
    }

    private fun createChip(hashTagList: List<String>) {
        for (i in hashTagList.indices) {
            val chip = layoutInflater.inflate(
                R.layout.single_chip_layout,
                binding.chipGroupHashtag,
                false
            ) as Chip
            chip.id = View.generateViewId()
            chip.text = "# ${hashTagList[i]}"
            binding.chipGroupHashtag.addView(chip)

            val firstTag = binding.chipFirstSortedTag
            val secondTag = binding.chipSecondSortedTag
            val thirdTag = binding.chipThirdSortedTag

            // 선택용 태그 ClickListener 설정
            chip.setOnClickListener {
                // 첫번째 태그 자리가 비어있다면 == 아무것도 선택하지 않은 상태
                if(firstTag.visibility == View.GONE) {
                    firstTag.text = chip.text
                    setBackgroundColor(firstTag)
                    chip.visibility = View.GONE
                    firstTag.visibility = View.VISIBLE
                }
                // 두번째 태그 자리가 비어있다면 == 첫번째 태그 사용 중
                else if(secondTag.visibility == View.GONE) {
                    secondTag.text = chip.text
                    setBackgroundColor(secondTag)
                    chip.visibility = View.GONE
                    secondTag.visibility = View.VISIBLE
                }
                // 세번째 태그 자리가 비어있다면 == 두,세번째 태그 사용 중
                else if(thirdTag.visibility == View.GONE) {
                    thirdTag.text = chip.text
                    setBackgroundColor(thirdTag)
                    chip.visibility = View.GONE
                    thirdTag.visibility = View.VISIBLE
                }
                // 모든 태그가 사용 중이라면
                else {
                    chip.isChecked = false
                    this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
                }
            }
        }
    }

    private fun setListenerToSortedTag(sortedTag: Chip, tagNumber: Int) {
        // 이미 선택된 상태에서 클릭했다면
        sortedTag.setOnClickListener {
            // 원래 태그 보여주기
            for (id in binding.chipGroupHashtag.checkedChipIds) {
                val chip: Chip = binding.chipGroupHashtag.findViewById(id)
                if(chip.text.equals(sortedTag.text)) {
                    chip.visibility = View.VISIBLE
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
                if (secondTag.visibility != View.GONE) {
                    firstTag.text = secondTag.text
                    firstTag.chipBackgroundColor = secondTag.chipBackgroundColor
                    secondTag.text = ""
                    if (thirdTag.visibility != View.GONE) {
                        thirdTag.visibility = View.GONE
                        secondTag.text = thirdTag.text
                        secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                        thirdTag.text = ""
                    } else secondTag.visibility = View.GONE
                } else firstTag.visibility = View.GONE
            }
            2 -> {
                secondTag.text = ""
                if (thirdTag.visibility != View.GONE) {
                    secondTag.chipBackgroundColor = thirdTag.chipBackgroundColor
                    thirdTag.visibility = View.GONE
                    secondTag.text = thirdTag.text
                    thirdTag.text = ""
                } else secondTag.visibility = View.GONE
            }
            3 -> {
                thirdTag.visibility = View.GONE
                thirdTag.text = ""
            }
        }
    }

    private fun returnBackgroundColor(tag: Chip) {
        when (tag.chipBackgroundColor) {
            // off_white를 사용했다면
            resources.getColorStateList(R.color.off_white, null) -> {
                bOffWhiteIsUsed = false
            }
            // very_light_pink를 사용했다면
            resources.getColorStateList(R.color.very_light_pink, null) -> {
                bVeryLightPinkIsUsed = false
            }
            // light_peach_2를 사용했다면
            resources.getColorStateList(R.color.light_peach_2, null) -> {
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
        outOfFocusOnTitle()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val selectedDate: Calendar = Calendar.getInstance()

        // date edittext에 값이 있으면 해당 값으로 날짜 지정해줘야 함. 없으면 현재 날짜로 지정.
        if(binding.etSelectDate.text.isNotEmpty()) {
            selectedDate.time = dateFormat.parse(binding.etSelectDate.text.toString()) as Date
        }

        val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            val strDate: String = dateFormat.format(selectedDate.time)
            binding.etSelectDate.setText(strDate)
            binding.etSelectDate.setTextColor(Color.BLACK)
            binding.etTitle.clearFocus()
            binding.tvDateErrorNoInput.visibility = View.GONE
        },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        dialog.apply {
            // 날짜수로 선택한 경우 미래 날짜는 입력 불가능
            if(binding.tvSelectType.text.equals(binding.tvTypeNumberOfDays.text))
                datePicker.maxDate = System.currentTimeMillis()
        }.show()
        dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.soft_pink, null))
        dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.pinkish, null))
    }
}