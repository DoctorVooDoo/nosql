package ru.msu.vmk.distapp.sample.dto

import java.time.LocalDate

data class BookingDto(
    val id: String,
    val roomId: String,
    val userId: String,
    val dateIn: LocalDate,
    val dateOut: LocalDate,
    val totalPrice: Double,
    val status: String
)

data class UpdateBookingDto(
    val roomId: String,
    val userId: String,
    val dateIn: LocalDate,
    val dateOut: LocalDate,
    val totalPrice: Double,
    val status: String
)