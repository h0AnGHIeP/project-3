package edu.hmh

import edu.hmh.access.DrugDataAccess
import edu.hmh.access.PrescriptionDataAccess
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PrescriptionDaoTest {

    @Autowired
    private lateinit var prescriptionDataAccess: PrescriptionDataAccess

    @Autowired
    private lateinit var drugDataAccess: DrugDataAccess

    @Test
    fun `test prescription repository`() {
        val pres = prescriptionDataAccess.newestPrescription(1)
        Assertions.assertNotNull(pres)
        Assertions.assertEquals(1, pres!!.patient.id)
    }

    @Test
    fun `test patient drug query`() {
        val drugs = drugDataAccess.fetchPatientDrugs(3)
        Assertions.assertEquals(2, drugs.size)
    }

}