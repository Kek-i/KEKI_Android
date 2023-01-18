package com.umc.keki.src.main.consumer.calendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
    private var openForLayoutOfType: Boolean = false
    // 해시태그별 클릭 여부
    private var bFriendTagIsClicked: Boolean = false
    private var bFamilyTagIsClicked: Boolean = false
    private var bLoverTagIsClicked: Boolean = false
    private var bAnniversaryTagIsClicked: Boolean = false
    private var bBirthdayTagIsClicked: Boolean = false
    private var bEmploymentTagIsClicked: Boolean = false
    private var bGraduationExhibitionTagIsClicked: Boolean = false
    private var bPartyTagIsClicked: Boolean = false
    // 몇번째 태그가 클릭되었는지
    private var firstTag: String? = null
    private var secondTag: String? = null
    private var thirdTag: String? = null


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

    private fun setClickListenerToHashTag() {
        binding.tvHashtagFriend.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bFriendTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagFriend.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagFriend.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagFriend.text.toString()))
                    thirdTag = null
                bFriendTagIsClicked = false
                binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bFriendTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagFriend.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagFriend.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagFriend.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagFriend.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagFamily.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bFamilyTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagFamily.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagFamily.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagFamily.text.toString()))
                    thirdTag = null
                bFamilyTagIsClicked = false
                binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bFamilyTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagFriend.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagFriend.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagFamily.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagFamily.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagLover.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bLoverTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagLover.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagLover.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagLover.text.toString()))
                    thirdTag = null
                bLoverTagIsClicked = false
                binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bLoverTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagLover.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagLover.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagLover.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagLover.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagAnniversary.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bAnniversaryTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagAnniversary.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagAnniversary.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagAnniversary.text.toString()))
                    thirdTag = null
                bAnniversaryTagIsClicked = false
                binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bAnniversaryTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagAnniversary.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagAnniversary.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagAnniversary.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagAnniversary.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagBirthday.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bBirthdayTagIsClicked) {
                binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
                if(firstTag.equals(binding.tvHashtagBirthday.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagBirthday.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagBirthday.text.toString()))
                    thirdTag = null
                bBirthdayTagIsClicked = false
            }
            else {
                bBirthdayTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagBirthday.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagBirthday.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagBirthday.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagBirthday.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagEmployment.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bEmploymentTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagEmployment.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagEmployment.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagEmployment.text.toString()))
                    thirdTag = null
                bEmploymentTagIsClicked = false
                binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bEmploymentTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagEmployment.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagEmployment.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagEmployment.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagEmployment.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagGraduationExhibition.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bGraduationExhibitionTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagGraduationExhibition.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagGraduationExhibition.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagGraduationExhibition.text.toString()))
                    thirdTag = null
                bGraduationExhibitionTagIsClicked = false
                binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bGraduationExhibitionTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagGraduationExhibition.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagGraduationExhibition.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagGraduationExhibition.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagGraduationExhibition.text.toString()
                }
                else this.showCustomToast("이미 해시태그 3개를 모두 선택하셨습니다.")
            }
        }
        binding.tvHashtagParty.setOnClickListener {
            // 이미 클릭된 상태였다면
            if(bPartyTagIsClicked) {
                if(firstTag.equals(binding.tvHashtagParty.text.toString()))
                    firstTag = null
                else if(secondTag.equals(binding.tvHashtagParty.text.toString()))
                    secondTag = null
                else if(thirdTag.equals(binding.tvHashtagParty.text.toString()))
                    thirdTag = null
                bPartyTagIsClicked = false
                binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_white)
            }
            else {
                bPartyTagIsClicked = true
                if(firstTag == null) {
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_off_white)
                    firstTag = binding.tvHashtagParty.text.toString()
                }
                else if(secondTag == null) {
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_very_light_pink)
                    secondTag = binding.tvHashtagParty.text.toString()
                }
                else if(thirdTag == null) {
                    binding.tvHashtagParty.setBackgroundResource(R.drawable.bg_rectangle_radius_13_light_peach_2)
                    thirdTag = binding.tvHashtagParty.text.toString()
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