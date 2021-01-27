package edu.hmh.data

import edu.hmh.dto.PrescriptionDto
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "prescription")
class Prescription(
    @Id
    @Column(name = "prescription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "start_timestamp")
    @Temporal(TemporalType.DATE)
    val startTime: Date,

    @Column(name = "end_timestamp")
    @Temporal(TemporalType.DATE)
    val endTime: Date,

    @Column
    val expect: String,

    @Column
    val note: String,

    @ManyToOne
    @JoinColumn(name = "patient_id")
    val patient: Patient,

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    val doctor: Doctor,

    @OneToMany(mappedBy = "prescription", fetch = FetchType.EAGER)
    val instructions: List<Instruction>
) {

    fun dto() = PrescriptionDto(
        id,
        startTime,
        endTime,
        expect,
        note,
        patient.id,
        instructions.map {
            it.dto()
        })
}
