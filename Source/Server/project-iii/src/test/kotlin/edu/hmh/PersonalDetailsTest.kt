package edu.hmh

import edu.hmh.access.DoctorDataAccess
import edu.hmh.access.PatientDataAccess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonalDetailsTest {

    @Autowired
    private lateinit var doctorDao: DoctorDataAccess

    @Autowired
    private lateinit var patientDao: PatientDataAccess

    @Test
    fun `test doctor repository`() {
        assertEquals(2, doctorDao.count())
    }

    @Test
    fun `test patient repository`() {
        assertEquals(4, patientDao.count())
    }



}