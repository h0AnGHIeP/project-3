package edu.hmh.access

import edu.hmh.data.Doctor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DoctorDataAccess : CrudRepository<Doctor, Long> {
}