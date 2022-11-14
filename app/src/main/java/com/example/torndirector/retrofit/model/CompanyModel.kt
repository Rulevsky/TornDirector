package com.example.torndirector.retrofit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class CompanyModel(
    @SerializedName("company")
    var company: CompanyDetails

) {

}




