package edu.hmh

import edu.hmh.access.ClinicDataAccess
import edu.hmh.converters.DateConverter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MedicalApplicationTests {

    @Autowired
    private lateinit var clinicDataAccess: ClinicDataAccess

    @Test
    fun `test converter`() {
        assertDoesNotThrow {
            val date = DateConverter().convert("2018-06-23 12:06:33")
            print(date)
        }
    }

    @Test
    fun `clinic locations`() {
        assertEquals("Thanh Chan", clinicDataAccess.findById(2).get().name)
    }

    @Test
    fun `total clinic employees`() {
        val firstClinic = clinicDataAccess.findById(1)
        assertTrue(firstClinic.isPresent)
        assertEquals(2, firstClinic.get().doctors.size)
    }
    @Test
    fun `count working clinic of doctor`() {
        assertEquals(3, clinicDataAccess.findByDoctorId(1).size)
    }

}
