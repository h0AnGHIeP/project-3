package edu.hmh.access

import edu.hmh.data.Patient
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface PatientDataAccess:CrudRepository<Patient,Long> {
}