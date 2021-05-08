package com.example.studentapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.studentapp.R
import com.example.studentapp.application.prefs
import com.example.studentapp.model.entities.Group
import com.example.studentapp.model.entities.Lesson
import com.example.studentapp.presenter.SchedulePresenter
import com.example.studentapp.presenter.contracts.SchedulePresenterContract
import com.example.studentapp.view.contracts.NavigationViewContract
import com.example.studentapp.view.contracts.ScheduleViewContract
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList


const val SCHEDULE_TAG = "ScheduleFragment"

class ScheduleFragment : Fragment(), ScheduleViewContract {

    private lateinit var presenter: SchedulePresenterContract
    private lateinit var tableLayout: TableLayout

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
        showTimetable()
        Log.d(SCHEDULE_TAG, "onViewCreated end")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
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

    override fun showTimetable() {
        tableLayout.removeAllViews()
        val type: Type = object : TypeToken<List<Lesson>>() {}.type
        val timetable = Gson().fromJson<List<Lesson>>(prefs.timetable, type)
        if (timetable != null) {
            for (i in timetable.indices) {

                val tableRowTime = TableRow(this.context)
                val tvTime = TextView(this.context)
                tvTime.setPadding(10, 10, 10, 10)
                tvTime.gravity = Gravity.START
                tvTime.text = timetable[i].start + " - " + timetable[i].end //getTime(timetable[i].start) + " - " + getTime(timetable[i].end)
                tableRowTime.addView(tvTime)

                val tableRowTitle = TableRow(this.context)
                val tvTitle = TextView(this.context)
                tvTitle.setPadding(10, 10, 10, 10)
                tvTitle.gravity = Gravity.START
                tvTitle.text = timetable[i].title
                tableRowTitle.addView(tvTitle)

                val tableRowTeacher = TableRow(this.context)
                val tvTeacher = TextView(this.context)
                tvTeacher.setPadding(10, 10, 10, 10)
                tvTeacher.gravity = Gravity.START
                tvTeacher.text = timetable[i].teacher
                tableRowTeacher.addView(tvTeacher)


                val tableRowRoom = TableRow(this.context)
                val tvRoom = TextView(this.context)
                tvRoom.setPadding(10, 10, 10, 10)
                tvRoom.gravity = Gravity.START
                tvRoom.text = timetable[i].room
                tableRowRoom.addView(tvRoom)

                tableLayout.addView(tableRowTime)
                tableLayout.addView(tableRowTitle)
                tableLayout.addView(tableRowTeacher)
                tableLayout.addView(tableRowRoom)

                val v = View(this.context)
                v.layoutParams = TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1
                )
                //v.setBackgroundColor(Color.rgb(51, 51, 51))
                tableLayout.addView(v)
            }
        }
    }

    private fun getTime(dateTime: String): String {
        //todo: find a way to format date for <API 26
        return ""
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}