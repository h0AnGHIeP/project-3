package edu.hmh

import edu.hmh.access.PatientDataAccess
import edu.hmh.access.TestDataAccess
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestDetailsDaoTest {

    @Autowired
    private lateinit var testDataAccess: TestDataAccess

    @Autowired
    private lateinit var patientDao: PatientDataAccess

    @Test
    fun `test patient test query`() {
        val tests = testDataAccess.getTestByPatientId(1)
        Assertions.assertEquals(3, tests.size)
    }

    @Test
    fun `insert test`() {
        val patient = patientDao.findById(2).get()
        val initCount = testDataAccess.count()
        val firstTest = edu.hmh.data.Test(
            0, Date(), "Test in morning ", 1.0f, 1.0f, 1.0f, 1.0f, patient
        )
        val secondTest = edu.hmh.data.Test(
            0, Date(), "Test in morning ", 1.0f, 1.0f, 1.0f, 1.0f, patient
        )
        val thirdTest = edu.hmh.data.Test(
            null, Date(), "Test in morning ", 1.0f, 1.0f, 1.0f, 1.0f, patient
        )
        testDataAccess.saveAll(listOf(firstTest,secondTest,thirdTest))
        Assertions.assertEquals(initCount + 3, testDataAccess.count())
    }

}