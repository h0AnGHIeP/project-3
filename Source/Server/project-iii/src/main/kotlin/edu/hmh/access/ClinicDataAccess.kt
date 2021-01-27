package edu.hmh.access

import edu.hmh.data.Clinic
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClinicDataAccess : CrudRepository<Clinic, Long> {

    @Query(
        "select clinic.* from clinic join clinic_employee on clinic.clinic_id = " +
                "clinic_employee.clinic_id where doctor_id = :doctorId",
        nativeQuery = true
    )
    fun findByDoctorId(@Param("doctorId") doctorId: Long): List<Clinic>
}