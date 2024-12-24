package ru.msu.vmk.distapp.sample.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.model.Booking

@Repository
class BookingRepository(val mongoTemplate: MongoTemplate) {

    fun listBookingsByUser(userId: String): List<Booking> {
        val criteria = Criteria.where("userId").`is`(userId)
        return mongoTemplate.find(Query.query(criteria), Booking::class.java)
    }

    fun getBookingById(bookingId: String): Booking? {
        return mongoTemplate.findById(bookingId)
    }

    fun saveBooking(booking: Booking): Booking {
        return mongoTemplate.save(booking)
    }

    fun deleteBooking(bookingId: String) {
        val criteria = Criteria.where("_id").`is`(bookingId)
        mongoTemplate.remove(Query.query(criteria), Booking::class.java)
    }
}