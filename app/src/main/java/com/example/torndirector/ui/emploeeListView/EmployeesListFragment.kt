package com.example.torndirector.ui.emploeeListView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.torndirector.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesListFragment : Fragment(R.layout.employees_list_fragment) {
    private val employeesListViewModel: EmployeesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = EmployeeListAdapter()
        val view = inflater.inflate(R.layout.employees_list_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.employeeLisRecViewFrag)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
        employeesListViewModel.allEmployees.observe(viewLifecycleOwner) { employees ->
            employees?.let { adapter.submitList(it) }
        }
        return view
    }
}


