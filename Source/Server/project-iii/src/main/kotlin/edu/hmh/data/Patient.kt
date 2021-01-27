package edu.hmh.data

import edu.hmh.dto.PatientDto
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "patient")
class Patient(
    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    val name: String,

    @Temporal(TemporalType.DATE)
    @Column
    val birth: Date,

    @Column
    val job: String,

    @Column(name = "i_card")
    val iCard: String,

    @Column
    val address: String,

    @Column
    val contact: String,

    @Column
    val height: Int,

    @Column
    val weight: Int,

    @Column
    val sex: Boolean,

    @Column
    val country: String,

    @Temporal(TemporalType.DATE)
    @Column
    val begin: Date,

    @Temporal(TemporalType.DATE)
    @Column(name = "start_treatment")
    val startTreatment: Date,

    @Column(name = "usual_symptom")
    val usualSymptom: String,

    @Column
    val history: String,

    @Column
    val type: Int,

    @OneToMany(mappedBy = "patient")
    val prescriptions: List<Prescription>,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    val doctor: Doctor,

    @OneToMany(mappedBy = "patient")
    val tests: List<Test>
) {

    fun dto() = PatientDto(
        id,
        name,
        birth,
        job,
        iCard,
        address,
        contact,
        height,
        weight,
        sex,
        country,
        begin,
        startTreatment,
        usualSymptom,
        history,
        type,
        doctor.dto()
    )
}