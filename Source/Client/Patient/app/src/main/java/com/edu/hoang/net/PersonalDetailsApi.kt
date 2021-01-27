package com.edu.hoang.net

import com.edu.hoang.net.dto.DoctorDto
import com.edu.hoang.net.dto.PatientDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonalDetailsApi {
    @GET("/personal/patient")
    suspend fun fetchPatientDetails(@Query("id") patientId: Long): PatientDto

    @GET("/personal/doctor")
    suspend fun fetchDoctorDetails(@Query("id") doctorId: Long): DoctorDto
}