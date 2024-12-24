package ru.msu.vmk.distapp.sample.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("room")
data class Room(
    var id: String,
    val title: String,
    val description: String,
    val pricePerNight: Double,
    val location: String,
    val amenities: List<String>
)