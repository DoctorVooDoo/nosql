package ru.msu.vmk.distapp.sample.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("booking")
data class Booking(
    val id: String,
    val roomId: String,
    val userId: String,
    val dateIn: LocalDate,
    val dateOut: LocalDate,
    val totalPrice: Double,
    val status: String
)