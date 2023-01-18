package com.umc.keki.src.main.consumer.calendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.umc.keki.R
import com.umc.keki.config.BaseActivity
import com.umc.keki.databinding.ActivityConsumerCalendarAddBinding
import java.text.SimpleDateFormat
import java.util.*

class ConsumerCalendarAddActivity : BaseActivity<ActivityConsumerCalendarAddBinding>(
    ActivityConsumerCalendarAddBinding::inflate) {
    // 기념일 종류 메뉴 열고 닫기 여부
    private var openForLayoutOfType: Boolean = false
    // 해시태그별 선택 여부
    private var bFriendTagIsSelected: Boolean = false
    private var bFamilyTagIsSelected: Boolean = false
    private var bLoverTagIsSelected: Boolean = false
    private var bAnniversaryTagIsSelected: Boolean = false
    private var bBirthdayTagIsSelected: Boolean = false
    private var bEmploymentTagIsSelected: Boolean = false
    private var bGraduationExhibitionTagIsSelected: Boolean = false
    private var bPartyTagIsSelected: Boolean = false
    // 무슨색(몇번째) 태그가 클릭되었는지
    private var bOffWhiteIsUsed: Boolean = false
    private var bVeryLightPinkIsUsed: Boolean = false
    private var bLightPeach2IsUsed: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setClickListenerToLayoutOfType()
        setClickListenerToDatePicker()
        setClickListenerToBackBtn()
        setClickListenerToHashTag()
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
            showDatePickerDialog(it)
        }
    }

    private fun setClickListenerToBackBtn() {
        binding.ibCalendarAddBack.setOnClickListener {
            finish()
        }
    }

    // 무슨 태그가 선택되었는지는 각 태그별 Boolean 값으로 알 수 있음
    private fun setClickListenerToHashTag() {
        binding.tvHashtagFriend.setOnClickListener {
            // 이미 선택된 상태에서 클릭했다면 -> 배경 하얀색으로 변경, 선택 false, 해당 태그 번호 false
            if(bFriendTagIsSelected) {
                // 첫번째 태그(off_white)를 사용했다면
                if(binding.tvHashtagFriend.background.constantState == resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState)
                    bOffWhiteIsUsed = false
                // 두번째 태그(very_light_pink)를 사용했다면
                else if(binding.tvHashtagFriend.background.constantState == resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState)
                    bVeryLightPinkIsUsed = false
                // 세번째 태그(light_peach_2)를 사용했다면
                else if(binding.tvHashtagFriend.background.constantState == resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState)
                    bLightPeach2IsUsed = false
                bFriendTagIsSelected = false
                binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            // 해당 태그 선택
            else {
                // 첫번째 태그(off_white)를 쓸 수 있다면
                if(!bOffWhiteIsUsed) {
                    bFriendTagIsSelected = true
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                // 두번째 태그(very_light_pink)를 쓸 수 있다면
                else if(!bVeryLightPinkIsUsed) {
                    bFriendTagIsSelected = true
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                // 세번째 태그(light_peach_2)를 쓸 수 있다면
                else if(!bLightPeach2IsUsed) {
                    bFriendTagIsSelected = true
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                // 모든 3개의 태그가 사용 중이라면
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagFamily.setOnClickListener {
            if(bFamilyTagIsSelected) {
                when (binding.tvHashtagFamily.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bFamilyTagIsSelected = false
                binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bFamilyTagIsSelected = true
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bFamilyTagIsSelected = true
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bFamilyTagIsSelected = true
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagLover.setOnClickListener {
            if(bLoverTagIsSelected) {
                when (binding.tvHashtagLover.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bLoverTagIsSelected = false
                binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bLoverTagIsSelected = true
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bLoverTagIsSelected = true
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bLoverTagIsSelected = true
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagAnniversary.setOnClickListener {
            if(bAnniversaryTagIsSelected) {
                when (binding.tvHashtagAnniversary.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bAnniversaryTagIsSelected = false
                binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bAnniversaryTagIsSelected = true
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bAnniversaryTagIsSelected = true
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bAnniversaryTagIsSelected = true
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagBirthday.setOnClickListener {
            if(bBirthdayTagIsSelected) {
                when (binding.tvHashtagBirthday.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bBirthdayTagIsSelected = false
                binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bBirthdayTagIsSelected = true
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bBirthdayTagIsSelected = true
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bBirthdayTagIsSelected = true
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagEmployment.setOnClickListener {
            if(bEmploymentTagIsSelected) {
                when (binding.tvHashtagEmployment.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bEmploymentTagIsSelected = false
                binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bEmploymentTagIsSelected = true
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bEmploymentTagIsSelected = true
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bEmploymentTagIsSelected = true
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagGraduationExhibition.setOnClickListener {
            if(bGraduationExhibitionTagIsSelected) {
                when (binding.tvHashtagGraduationExhibition.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bGraduationExhibitionTagIsSelected = false
                binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bGraduationExhibitionTagIsSelected = true
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bGraduationExhibitionTagIsSelected = true
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bGraduationExhibitionTagIsSelected = true
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagParty.setOnClickListener {
            if(bPartyTagIsSelected) {
                when (binding.tvHashtagParty.background.constantState) {
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_off_white, null).constantState -> bOffWhiteIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_very_light_pink, null).constantState -> bVeryLightPinkIsUsed = false
                    resources.getDrawable(R.drawable.bg_rectangle_radius_13_light_peach_2, null).constantState -> bLightPeach2IsUsed = false
                }
                bPartyTagIsSelected = false
                binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                if(!bOffWhiteIsUsed) {
                    bPartyTagIsSelected = true
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    bOffWhiteIsUsed = true
                }
                else if(!bVeryLightPinkIsUsed) {
                    bPartyTagIsSelected = true
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    bVeryLightPinkIsUsed = true
                }
                else if(!bLightPeach2IsUsed) {
                    bPartyTagIsSelected = true
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    bLightPeach2IsUsed = true
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
    }

    private fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.binding = this.binding
        newFragment.show(supportFragmentManager, "datePicker")
    }

    // 날짜 선택 다이얼로그 Class
    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
        lateinit var binding: ActivityConsumerCalendarAddBinding
        private val c: Calendar = Calendar.getInstance()

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(this.requireContext(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            c.set(year, month, day)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val strDate: String = dateFormat.format(c.time)
            binding.etSelectDate.setText(strDate)
            binding.etSelectDate.setTextColor(Color.BLACK)
        }
    }
}