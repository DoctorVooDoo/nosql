package ru.msu.vmk.distapp.sample.dto

data class PaymentDto(
    val id: String,
    val bookingId: String,
    val userId: String,
    val amount: Double,
    val paymentMethod: String,
    val status: String
)

data class UpdatePaymentDto(
    val bookingId: String,
    val userId: String,
    val amount: Double,
    val paymentMethod: String,
    val status: String
)