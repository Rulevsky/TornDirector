package com.example.torndirector.ui.emploeeListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.torndirector.R
import com.example.torndirector.TornDirectorApplication
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class EmployeesViewActivity : Fragment(R.layout.employees_list_fragment) {

    private val employeesListViewModel: EmployeesListViewModel by viewModels<EmployeesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var adapter = EmployeeViewAdapter()
        var view = inflater.inflate(R.layout.employees_list_fragment, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.employeeLisRecViewFrag)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
        employeesListViewModel.allEmployees.observe(viewLifecycleOwner, Observer { employees ->
            employees?.let { adapter.submitList(it) }

        })
         return view
    }

}


