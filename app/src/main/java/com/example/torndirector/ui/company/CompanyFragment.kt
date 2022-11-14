package com.example.torndirector.ui.company

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.torndirector.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyFragment : Fragment(R.layout.company_fragment) {
    private val companyViewModel: CompanyViewModel by viewModels()
    private lateinit var ratingTextView: TextView
    private lateinit var hiredEmployeesTextView: TextView
    private lateinit var dailyIncomeTextView: TextView
    private lateinit var weeklyIncomeTextView: TextView
    private lateinit var inStockTextView: TextView
    private lateinit var inOrderTextView: TextView
    private lateinit var totalStockTextView: TextView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.company_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("tag", " onViewCreated")
        ratingTextView = view.findViewById(R.id.ratingTextView)
        hiredEmployeesTextView = view.findViewById(R.id.hiredEmployeesTextView)
        dailyIncomeTextView = view.findViewById(R.id.dailyIncomeTextView)
        weeklyIncomeTextView = view.findViewById(R.id.weeklyIncomeTextView)
        inStockTextView = view.findViewById(R.id.inStockTextView)
        inOrderTextView = view.findViewById(R.id.inOrderTextView)
        totalStockTextView = view.findViewById(R.id.totalStockTextView)

        companyViewModel.companyDetails.observe(viewLifecycleOwner, Observer { it ->
            ratingTextView.text = it?.rating
            hiredEmployeesTextView.text = it?.employeesHired + "/" + it?.employeesCapacity
            dailyIncomeTextView.text = it?.dailyIncome
            weeklyIncomeTextView.text = it?.weeklyIncome
        })
        var summ : Long = 0
        companyViewModel.stockDetails.observe(viewLifecycleOwner, Observer { it->

//            inStockTextView.text = it?.forEach()
//            inOrderTextView.text =
//            totalStockTextView.text =
        })

    }


}