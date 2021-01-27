package edu.hmh.access

import edu.hmh.data.Test
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TestDataAccess : CrudRepository<Test, Long> {

    @Query("select test.* from test where patient_id = :patientId", nativeQuery = true)
    fun getTestByPatientId(@Param("patientId") id: Long): List<Test>
}