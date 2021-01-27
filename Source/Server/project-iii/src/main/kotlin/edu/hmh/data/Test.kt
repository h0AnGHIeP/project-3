package edu.hmh.data

import edu.hmh.dto.TestDto
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "test")
data class Test(
    @Id
    @Column(name = "test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val time: Date,
    @Column
    val description: String,

    @Column(name = "hba1c_index")
    val HbA1CIndex: Float,

    @Column(name = "random_index")
    val randomIndex: Float,

    @Column(name = "craving_index")
    val cravingIndex: Float,

    @Column(name = "after_meal_index")
    val afterMealIndex: Float,

    @ManyToOne
    @JoinColumn(name = "patient_id")
    val patient: Patient
) {

    constructor(dto: TestDto, patient: Patient) : this(
        null, dto.time, dto.description, dto.HbA1CIndex,
        dto.randomIndex, dto.cravingIndex, dto.afterMealIndex, patient
    )

    fun dto() = TestDto(
        id!!,
        time,
        description,
        HbA1CIndex,
        randomIndex,
        cravingIndex,
        afterMealIndex,
        patient.id
    )
}