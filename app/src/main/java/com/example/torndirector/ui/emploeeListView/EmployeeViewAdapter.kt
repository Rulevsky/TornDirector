package com.example.torndirector.ui.emploeeListView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.torndirector.R
import com.example.torndirector.room.Employee


class EmployeeViewAdapter :
    ListAdapter<Employee, EmployeeViewAdapter.EmployeeViewHolder>(EmployeesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bindName(current.employeeName)
        holder.bindEffectiveness(current.employeeEffectiveness)
        holder.bindLastAction(current.employeeLastAction)

    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val effectivenessSetTextView: TextView =
            itemView.findViewById(R.id.effectivenessTextView)
        private val lastActionTextView: TextView = itemView.findViewById(R.id.lastActionTextView)

        fun bindName(text: String?) { nameTextView.text = text}

        fun bindEffectiveness(text: String?) { effectivenessSetTextView.text = text }

        fun bindLastAction(text: String?) { lastActionTextView.text = text }

        companion object {
            fun create(parent: ViewGroup): EmployeeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.employee_holder, parent, false)
                return EmployeeViewHolder(view)
            }
        }
    }

    class EmployeesComparator : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
}




