package com.example.studentapp.view.contracts

interface ScheduleViewContract {
    var weekToShow: Int
    fun onGroupSelected(group: String)
    fun showTimetable(forWeek: Int)
}