package edu.hmh.access

import edu.hmh.data.Prescription
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PrescriptionDataAccess : CrudRepository<Prescription, Long> {

    @Query(
        "Select * from prescription where patient_id = :patientId order by " +
                "start_timestamp desc limit 1",
        nativeQuery = true
    )
    fun newestPrescription(@Param("patientId") patientId: Long): Prescription?

    @Query("select * from prescription where patient_id = :patientId", nativeQuery = true)
    fun findByPatientId(@Param("patientId") patientId: Long): List<Prescription>
}