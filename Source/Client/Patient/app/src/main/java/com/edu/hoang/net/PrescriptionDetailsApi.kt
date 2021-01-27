package com.edu.hoang.net

import com.edu.hoang.net.dto.DrugDto
import com.edu.hoang.net.dto.PrescriptionDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PrescriptionDetailsApi {
    @GET("prescription/newest")
    suspend fun fetchLatestPrescription(@Query("id") patientId: Long): PrescriptionDto

    @GET("prescription/all")
    suspend fun fetchAllPrescription(@Query("id") patientId: Long): List<PrescriptionDto>

    @GET("prescription/drugs/all")
    suspend fun fetchAllDrugTypes(): List<DrugDto>

    @GET("prescription/drugs")
    suspend fun fetchUsingDrug(@Query("id") patientId: Long): List<DrugDto>

    @GET("prescription/drug")
    suspend fun fetchSpecificDrug(@Query("drugId") drugId: Long): DrugDto

}