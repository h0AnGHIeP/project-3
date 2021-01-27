package edu.hmh.controllers

import edu.hmh.access.DrugDataAccess
import edu.hmh.access.PrescriptionDataAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/prescription")
class PrescriptionDetailsController {

    @Autowired
    lateinit var drugDataAccess: DrugDataAccess

    @Autowired
    lateinit var prescriptionDataAccess: PrescriptionDataAccess

    @GetMapping("newest")
    fun getDetailsPrescription(
        @RequestParam("id")
        patientId: Long
    ) = prescriptionDataAccess.newestPrescription(patientId)?.dto()

    @GetMapping("all")
    fun allPrescriptions(
        @RequestParam("id")
        patientId: Long
    ) = prescriptionDataAccess.findByPatientId(patientId).map { it.dto() }

    @GetMapping("drugs/all")
    fun allDrugs() = drugDataAccess.findAll().map { it.dto() }

    @GetMapping("drugs")
    fun allDrugsOfPatient(
        @RequestParam("id")
        patientId: Long
    ) = drugDataAccess.fetchPatientDrugs(patientId)


    @GetMapping("drug")
    fun specificDrug(
        @RequestParam("drugId")
        drugId: Long
    ) = drugDataAccess.findById(drugId)


}