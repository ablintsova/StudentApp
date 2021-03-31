package com.example.studentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.studentapp.R
import com.example.studentapp.application.prefs


class ProfileFragment : Fragment() {

    companion object {

        fun newInstance() = ProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(NEWS_TAG, "onViewCreated: beginning")
        setStudentData()
        Log.d(NEWS_TAG, "onViewCreated: end")
    }

    private fun setStudentData() {
        val name = activity?.findViewById<TextView>(R.id.tvFullName)
        name?.text = prefs.studentName

        val department = activity?.findViewById<TextView>(R.id.tvDepartment)
        department?.text = prefs.studentDepartment

        val group = activity?.findViewById<TextView>(R.id.tvGroup)
        group?.text = "Группа " + prefs.studentGroup

        val course = activity?.findViewById<TextView>(R.id.tvYear)
        course?.text = "Курс " + prefs.studentCourse

        val phone = activity?.findViewById<TextView>(R.id.tvPhone)
        phone?.text = "Телефон: " + prefs.studentPhone

        val email = activity?.findViewById<TextView>(R.id.tvEmail)
        email?.text = "Email: " + prefs.studentEmail
    }
}