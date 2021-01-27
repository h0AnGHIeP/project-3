package edu.hmh.data

import edu.hmh.dto.InstructionDto
import javax.persistence.*

@Entity
@Table(name = "instruction")
class Instruction(
    @Id
    @Column(name = "instruction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    val duration: Int,

    @Column
    val dose: Float,

    @Column
    val content: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drug_id")
    val drug: Drug,

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    val prescription: Prescription
) {

    fun dto() = InstructionDto(id, duration, dose, content, drug.id)
}