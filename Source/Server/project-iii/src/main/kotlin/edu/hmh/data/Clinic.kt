package edu.hmh.data

import edu.hmh.dto.ClinicDto
import java.sql.Time
import javax.persistence.*


@Entity
@Table(name = "clinic")
data class Clinic(
    @Id
    @Column(name = "clinic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Column
    val address: String,
    @Column
    val contact: String,
    @Column
    val open: Time,
    @Column
    val close: Time,
    @Column
    val longitude: Double,
    @Column
    val latitude: Double,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "clinic_employee", joinColumns = [JoinColumn(name = "clinic_id")],
        inverseJoinColumns = [JoinColumn(name = "doctor_id")]
    )
    val doctors: List<Doctor>
) {

    fun dto() = ClinicDto(
        id,
        name,
        address,
        contact,
        open,
        close,
        longitude,
        latitude,
        doctors.map { it.dto() })
}