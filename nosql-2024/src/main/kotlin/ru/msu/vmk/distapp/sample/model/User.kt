package ru.msu.vmk.distapp.sample.model

import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String
)