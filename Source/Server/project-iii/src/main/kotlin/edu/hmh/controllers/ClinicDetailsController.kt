package edu.hmh.controllers

import edu.hmh.access.ClinicDataAccess
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/clinic")
class ClinicDetailsController {

    @Autowired
    private lateinit var clinicDataAccess: ClinicDataAccess

    @GetMapping("all")
    fun findAllClinics() = clinicDataAccess.findAll().map { it.dto() }

    @GetMapping("doctor")
    fun findByDoctorId(
        @RequestParam("doctorId")
        doctorId: Long
    ) = clinicDataAccess.findByDoctorId(doctorId).map { it.dto() }
}