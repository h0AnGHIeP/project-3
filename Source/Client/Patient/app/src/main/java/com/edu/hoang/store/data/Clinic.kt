package com.edu.hoang.store.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.edu.hoang.net.dto.ClinicDto
import java.sql.Time

@Entity
data class Clinic(
    @PrimaryKey val id: Long,
    val name: String = "",
    val address: String = "",
    val contact: String = "",
    val open: Time = Time(0),
    val close: Time = Time(0),
    val longitude: Float = 0F,
    val latitude: Float = 0F
) {
    constructor(dto: ClinicDto) : this(
        dto.id,
        dto.name,
        dto.address,
        dto.contact,
        dto.open,
        dto.close,
        dto.longitude,
        dto.latitude
    )
}