package com.example.torndirector.ui.emploeeListView

import android.util.Log
import androidx.lifecycle.*
import com.example.torndirector.repositories.EmployeeRepository
import com.example.torndirector.room.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesListViewModel @Inject constructor (private val repository: EmployeeRepository) : ViewModel() {
    val allEmployees : LiveData<List<Employee>> = repository.allEmployees.asLiveData()

    fun insert(employee: Employee) = viewModelScope.launch{
        repository.insert(employee)
    }

}

class EmployeesListViewModelFactory(private val repository: EmployeeRepository) :
        ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeesListViewModel::class.java)) {
            return EmployeesListViewModel( repository) as T
            Log.e("TAG", "EmployeesListViewModelFactory")
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}
