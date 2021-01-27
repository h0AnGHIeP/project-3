package edu.hmh.controllers

import edu.hmh.access.DoctorDataAccess
import edu.hmh.access.PatientDataAccess
import edu.hmh.dto.DoctorDto
import edu.hmh.dto.PatientDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/personal")
class PersonalDetailsController {

    @Autowired
   private lateinit var patientDataAccess: PatientDataAccess

    @Autowired
   private lateinit var doctorDataAccess: DoctorDataAccess

    @GetMapping("patient")
    fun patientDetails(@RequestParam("id") patientId: Long): PatientDto? {
        return patientDataAccess.findById(patientId).orElseGet { null }?.dto()
    }

    @GetMapping("doctor")
    fun doctorDetails(@RequestParam("id") doctorId: Long): DoctorDto? {
        return doctorDataAccess.findById(doctorId).orElseGet { null }?.dto()
    }


}

