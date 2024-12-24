package ru.msu.vmk.distapp.sample.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("payment")
data class Payment(
    val id: String,
    val bookingId: String,
    val userId: String,
    val amount: Double,
    val paymentMethod: String,
    val status: String
)