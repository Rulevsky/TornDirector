package com.example.torndirector.retrofit.model

import com.google.gson.annotations.SerializedName

class EmployeesList (
    @SerializedName("company_employees")
    var employeersList: Map<String, EmployeeModel>

) {
}