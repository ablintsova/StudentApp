package com.example.studentapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.studentapp.R
import com.example.studentapp.application.prefs
import com.example.studentapp.model.entities.Group
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

const val SCHEDULE_TAG = "ScheduleFragment"

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(SCHEDULE_TAG, "onViewCreated begin")
        super.onViewCreated(view, savedInstanceState)

        val spinner = activity?.findViewById<Spinner>(R.id.spinGroupList)
        val groups = getGroupsArray()
        val adapter = this.context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_spinner_item, groups)
        }
        spinner?.adapter = adapter
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

}