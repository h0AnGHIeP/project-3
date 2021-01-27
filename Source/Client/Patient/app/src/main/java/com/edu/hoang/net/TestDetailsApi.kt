package com.edu.hoang.net

import com.edu.hoang.net.dto.TestDto
import retrofit2.http.*

interface TestDetailsApi {
    @GET("/test/all")
    suspend fun fetchAllAvailableTests(@Query("id") patientId: Long): List<TestDto>

    @POST("/test/add")
    suspend fun pushNewTestResult(@Query("id") patientId: Long, @Body testDto: TestDto)
}