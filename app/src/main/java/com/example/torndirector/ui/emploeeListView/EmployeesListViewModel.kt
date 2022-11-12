package com.example.torndirector.ui.emploeeListView

import androidx.lifecycle.*
import com.example.torndirector.EmployeeRepository
import com.example.torndirector.room.Employee
import kotlinx.coroutines.launch

class EmployeesListViewModel(private val repository: EmployeeRepository) : ViewModel() {
    val allEmployees : LiveData<List<Employee>> = repository.allEmployees.asLiveData()

    fun insert(employee: Employee) = viewModelScope.launch{
        repository.insert(employee)
    }
}

class EmployeesListViewModelFactory(private val repository: EmployeeRepository) :
        ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeesListViewModel::class.java)) {
            return EmployeesListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}
