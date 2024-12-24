package ru.msu.vmk.distapp.sample.dto

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val phone: String
)

data class UpdateUserDto(
    val name: String,
    val email: String,
    val phone: String
)