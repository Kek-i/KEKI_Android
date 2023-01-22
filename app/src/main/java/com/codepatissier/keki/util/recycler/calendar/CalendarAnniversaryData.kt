package com.codepatissier.keki.util.recycler.calendar

import android.os.Parcel
import android.os.Parcelable

data class CalendarAnniversaryData(
    var title: String,
    var date: String,
    var dday: String,
    var type: String,
    var firstTag: String?,
    var secondTag: String?,
    var thirdTag: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(dday)
        parcel.writeString(type)
        parcel.writeString(firstTag)
        parcel.writeString(secondTag)
        parcel.writeString(thirdTag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalendarAnniversaryData> {
        override fun createFromParcel(parcel: Parcel): CalendarAnniversaryData {
            return CalendarAnniversaryData(parcel)
        }

        override fun newArray(size: Int): Array<CalendarAnniversaryData?> {
            return arrayOfNulls(size)
        }
    }
}