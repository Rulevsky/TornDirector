package com.example.torndirector.ui.emploeeListView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.torndirector.R
import com.example.torndirector.TornDirectorApplication


class EmployeesViewActivity : AppCompatActivity() {
    private val employeesListViewModel: EmployeesListViewModel by viewModels {
        EmployeesListViewModelFactory((application as TornDirectorApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_view)

        val recyclerView = findViewById<RecyclerView>(R.id.emploeesRecyclerView)
        val adapter = EmployeeViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        employeesListViewModel.allEmployees.observe(this, Observer { employees ->
            employees?.let {adapter.submitList(it)}
        })
    }
}


