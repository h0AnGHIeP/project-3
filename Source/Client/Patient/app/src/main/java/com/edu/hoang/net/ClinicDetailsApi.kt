package com.edu.hoang.net

import com.edu.hoang.net.dto.ClinicDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ClinicDetailsApi {
    @GET("clinic/all")
    suspend fun fetchAllAvailableClinics():List<ClinicDto>

    @GET("clinic/doctor")
    suspend fun fetchClinicsOfDoctor(@Query("doctorId")doctorId:Long):List<ClinicDto>
}