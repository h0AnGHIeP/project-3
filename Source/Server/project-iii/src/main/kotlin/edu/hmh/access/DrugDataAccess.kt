package edu.hmh.access

import edu.hmh.data.Drug
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface DrugDataAccess : CrudRepository<Drug, Long> {

    @Query(
        "select distinct drug.* from prescription join instruction on " +
                "instruction.prescription_id=prescription.prescription_id join drug on instruction.drug_id=drug.drug_id\n" +
                "where prescription.patient_id = :patientId", nativeQuery = true
    )
    fun fetchPatientDrugs(@Param("patientId") patientId: Long): List<Drug>
}