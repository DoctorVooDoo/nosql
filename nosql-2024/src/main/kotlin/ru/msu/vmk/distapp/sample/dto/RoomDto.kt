package ru.msu.vmk.distapp.sample.dto

data class RoomDto(
    val id: String,
    val title: String,
    val description: String,
    val pricePerNight: Double,
    val location: String,
    val amenities: List<String>
)

data class UpdateRoomDto(
    val title: String,
    val description: String,
    val pricePerNight: Double,
    val location: String,
    val amenities: List<String>
)