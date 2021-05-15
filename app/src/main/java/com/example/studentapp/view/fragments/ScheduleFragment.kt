package com.example.studentapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.studentapp.R
import com.example.studentapp.application.prefs
import com.example.studentapp.model.entities.Group
import com.example.studentapp.model.entities.Lesson
import com.example.studentapp.presenter.SchedulePresenter
import com.example.studentapp.presenter.contracts.SchedulePresenterContract
import com.example.studentapp.utils.Constants
import com.example.studentapp.view.contracts.NavigationViewContract
import com.example.studentapp.view.contracts.ScheduleViewContract
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


const val SCHEDULE_TAG = "ScheduleFragment"

class ScheduleFragment : Fragment(), ScheduleViewContract {

    private lateinit var presenter: SchedulePresenterContract
    private lateinit var tableLayout: TableLayout
    override var weekToShow = getCurrentWeekNumber()

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val navView = context as NavigationViewContract
            presenter = SchedulePresenter(this, navView)
        } catch (castException: ClassCastException) {
            Log.e(SCHEDULE_TAG, "onAttach failed to get parent activity")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(SCHEDULE_TAG, "onViewCreated begin")
        super.onViewCreated(view, savedInstanceState)

        tableLayout = activity?.findViewById(R.id.tblSchedule)!!

        setSpinner()
        setWeekButtons()
        setWeek()

        showTimetable(weekToShow)
        Log.d(SCHEDULE_TAG, "onViewCreated end")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    private fun setSpinner() {
        val spinner = activity?.findViewById<Spinner>(R.id.spinGroupList)
        val groups = getGroupsArray()
        val adapter = this.context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_spinner_item, groups
            )
        }
        spinner?.adapter = adapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val group = parent?.getItemAtPosition(position).toString()
                onGroupSelected(group)
            }
        }
    }

    private fun setWeekButtons() {
        val prevWeek = activity?.findViewById<Button>(R.id.btnPreviousWeek)
        prevWeek?.setOnClickListener {
            weekToShow--
            if (weekToShow < Constants.MIN_WEEK) {
                weekToShow = Constants.MAX_WEEK
            }
            setWeek()
            showTimetable(weekToShow)
        }
        val nextWeek = activity?.findViewById<Button>(R.id.btnNextWeek)
        nextWeek?.setOnClickListener {
            weekToShow++
            if (weekToShow > Constants.MAX_WEEK) {
                weekToShow = Constants.MIN_WEEK
            }
            setWeek()
            showTimetable(weekToShow)
        }
    }

    private fun setWeek() {
        val week = activity?.findViewById<TextView>(R.id.tvWeek)
        week?.text = getWeek(weekToShow)
    }


    private fun getGroupsArray(): ArrayList<String> {
        Log.d(SCHEDULE_TAG, "getGroupsArray begin")
        val type: Type = object : TypeToken<List<Group>>() {}.type
        val groups = Gson().fromJson<List<Group>>(prefs.groups, type)
        val groupsList = ArrayList<String>()
        groups.forEach { group ->
            groupsList.add(group.number)
        }
        Log.d(SCHEDULE_TAG, "getGroupsArray end")
        return groupsList
    }

    override fun onGroupSelected(group: String) {
        presenter.onGroupSelected(group)
    }

    override fun showTimetable(forWeek: Int) {
        tableLayout.removeAllViews()
        val type: Type = object : TypeToken<List<Lesson>>() {}.type
        val timetable = Gson().fromJson<List<Lesson>>(prefs.timetable, type)
        if (timetable != null) {
            var currentDate = getFullDate(timetable[0].start)
            for (i in timetable.indices) {
                if (forWeek == getWeekOfYear(timetable[i].start)) {
                    val tableRowTime = TableRow(this.context)
                    tableRowTime.setBackgroundColor(
                        ContextCompat.getColor(
                            this.context!!,
                            R.color.blue_second_200
                        )
                    )
                    val tvTime = TextView(this.context)
                    tvTime.setPadding(10, 10, 10, 10)
                    tvTime.gravity = Gravity.START
                    tvTime.text = getTime(timetable[i].start) + " - " + getTime(timetable[i].end)
                    tableRowTime.addView(tvTime)

                    val tableRowTitle = TableRow(this.context)
                    val tvTitle = TextView(this.context)
                    tvTitle.setPadding(10, 10, 10, 10)
                    tvTitle.gravity = Gravity.START
                    tvTitle.text = timetable[i].title
                    tvTitle.isSingleLine = false
                    tvTitle.width =
                        tableLayout.width * 9 / 10 // set lesson title width to 0.9 of table layout width so it doesn't go off the screen
                    tableRowTitle.addView(tvTitle)

                    val tableRowTeacher = TableRow(this.context)
                    val tvTeacher = TextView(this.context)
                    tvTeacher.setPadding(10, 10, 10, 10)
                    tvTeacher.gravity = Gravity.START
                    tvTeacher.text = timetable[i].teacher
                    tvTeacher.setTextColor(ContextCompat.getColor(this.context!!, R.color.blue_900))
                    tableRowTeacher.addView(tvTeacher)


                    val tableRowRoom = TableRow(this.context)
                    val tvRoom = TextView(this.context)
                    tvRoom.setPadding(10, 10, 10, 10)
                    tvRoom.gravity = Gravity.START
                    tvRoom.text = "ауд. " + timetable[i].room
                    tableRowRoom.addView(tvRoom)

                    if (currentDate != getFullDate(timetable[i].start) || i == 0) {
                        currentDate = getFullDate(timetable[i].start)
                        val tableRowDate = TableRow(this.context)
                        tableRowDate.setBackgroundColor(
                            ContextCompat.getColor(
                                this.context!!,
                                R.color.blue_700
                            )
                        )
                        val tvDate = TextView(this.context)
                        tvDate.setPadding(16, 24, 16, 24)
                        tvDate.gravity = Gravity.START
                        tvDate.text = currentDate
                        tvDate.setTextColor(ContextCompat.getColor(this.context!!, R.color.white))
                        tvDate.textSize = 16.0F
                        tableRowDate.addView(tvDate)
                        tableLayout.addView(tableRowDate)
                    }

                    tableLayout.addView(tableRowTime)
                    tableLayout.addView(tableRowTitle)
                    tableLayout.addView(tableRowTeacher)
                    tableLayout.addView(tableRowRoom)

                    val v = View(this.context)
                    v.layoutParams = TableLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, 1
                    )
                    tableLayout.addView(v)
                }
            }
        }
    }

    private fun getTime(dateTime: String): String {
        val date = dateTime.takeLast(8)
        return date.take(5)
    }

    private fun getDate(dateTime: String): Date {
        val pattern = "yyyy-MM-dd"
        val dateString = dateTime.take(10)
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.parse(dateString)
    }

    private fun getFullDate(dateTime: String): String {
        val pattern = "dd MMMM yyyy, EEEE"
        val date = getDate(dateTime)
        val newDateFormat = SimpleDateFormat(pattern, Locale("ru"))
        return newDateFormat.format(date)
    }

    private fun getWeekOfYear(dateTime: String): Int {
        val date = getDate(dateTime)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.WEEK_OF_YEAR)
    }

    private fun getCurrentWeekNumber(): Int {
        val cal = Calendar.getInstance()
        return cal.get(Calendar.WEEK_OF_YEAR)
    }

    private fun getWeek(weekNumber: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber)
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK))
        val firstDay = cal.time
        cal.set(Calendar.DAY_OF_WEEK,cal.getActualMaximum(Calendar.DAY_OF_WEEK))
        val lastDay = cal.time
        val pattern = "dd MMMM"
        val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
        val first = dateFormat.format(firstDay)
        val last = dateFormat.format(lastDay)
        return "$first - $last"
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}