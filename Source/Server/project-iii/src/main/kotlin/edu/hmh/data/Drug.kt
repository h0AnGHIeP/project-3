package edu.hmh.data

import edu.hmh.dto.DrugDto
import java.sql.Time
import javax.persistence.*

@Entity
@Table(name = "drug")
class Drug(
    @Id
    @Column(name = "drug_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column
    val name: String,

    @Column
    val instruct: String,

    @Column(name = "daily_using")
    val dailyUsing: Time,

    @Column
    val unit: String,

    @Column
    val note: String,
) {

    fun dto() = DrugDto(id, name, instruct, dailyUsing, unit, note)
}