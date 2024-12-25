package ru.msu.vmk.distapp.sample.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.model.Payment

@Repository
class PaymentRepository(val mongoTemplate: MongoTemplate) {

    fun listPaymentsByUser(userId: String): List<Payment> {
        val criteria = Criteria.where("userId").`is`(userId)
        return mongoTemplate.find(Query.query(criteria), Payment::class.java)
    }

    fun listPaymentsByBooking(bookingId: String): List<Payment> {
        val criteria = Criteria.where("bookingId").`is`(bookingId)
        return mongoTemplate.find(Query.query(criteria), Payment::class.java)
    }

    fun getPaymentById(paymentId: String): Payment? {
        return mongoTemplate.findById(paymentId)
    }

    fun savePayment(payment: Payment): Payment {
        return mongoTemplate.save(payment)
    }

    fun deletePayment(paymentId: String) {
        val criteria = Criteria.where("_id").`is`(paymentId)
        mongoTemplate.remove(Query.query(criteria), Payment::class.java)
    }
}