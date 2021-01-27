package edu.hmh.data

import edu.hmh.dto.DoctorDto
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "doctor")
class Doctor(
    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Temporal(TemporalType.DATE)
    @Column
    val birth: Date,
    @Column
    val country: String,
    @Column(name = "i_card")
    val iCard: String,
    @Column
    val address: String,
    @Column
    val contact: String,
    @Temporal(TemporalType.DATE)
    @Column(name = "start_working")
    val startWorking: Date,
    @Column
    val faculty: String,
    @Column
    val hospital: String,
    @Column
    val position: String,
    @ManyToMany(mappedBy = "doctors")
    val privateClinics: List<Clinic>,
    @OneToMany(mappedBy = "doctor", cascade = [CascadeType.PERSIST])
    val patients: MutableList<Patient>,
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, orphanRemoval = true)
    val prescriptions: List<Prescription>
) {

    fun dto() = DoctorDto(
        id,
        name,
        birth,
        country,
        iCard,
        address,
        contact,
        startWorking,
        faculty,
        hospital,
        position
    )
}