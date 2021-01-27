package edu.hmh.controllers

import edu.hmh.access.PatientDataAccess
import edu.hmh.access.TestDataAccess
import edu.hmh.data.Test
import edu.hmh.dto.TestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestDetailsController {

    @Autowired
    private lateinit var testDataAccess: TestDataAccess

    @Autowired
    private lateinit var patientDataAccess: PatientDataAccess

    @GetMapping("all")
    fun allPatientTests(
        @RequestParam("id")
        patientId: Long
    ) = testDataAccess.getTestByPatientId(patientId).map { it.dto() }

    @PostMapping("add")
    fun addNewTest(
        @RequestParam("id") patientId: Long,
        @RequestBody requestDto: TestDto
    ) {
        val patient = patientDataAccess.findById(patientId).orElseThrow()
        val data = Test(requestDto, patient)
        testDataAccess.save(data)
    }

}