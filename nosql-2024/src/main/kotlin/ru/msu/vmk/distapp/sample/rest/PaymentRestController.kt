package ru.msu.vmk.distapp.sample.rest

import org.springframework.web.bind.annotation.*
import ru.msu.vmk.distapp.sample.dto.PaymentDto
import ru.msu.vmk.distapp.sample.dto.UpdatePaymentDto
import ru.msu.vmk.distapp.sample.model.Payment
import ru.msu.vmk.distapp.sample.repository.PaymentRepository
import java.util.UUID

@RestController
@RequestMapping("/payment")
class PaymentRestController(
    val paymentRepository: PaymentRepository
) {

    @GetMapping("/user/{userId}")
    fun listPaymentsByUser(@PathVariable userId: String): List<PaymentDto> {
        return paymentRepository.listPaymentsByUser(userId).map { it.toDto() }
    }

    @GetMapping("/booking/{bookingId}")
    fun listPaymentsByBooking(@PathVariable bookingId: String): List<PaymentDto> {
        return paymentRepository.listPaymentsByBooking(bookingId).map { it.toDto() }
    }

    @PostMapping
    fun addPayment(@RequestBody payment: UpdatePaymentDto): PaymentDto {
        val newPayment = Payment(
            id = UUID.randomUUID().toString(),
            bookingId = payment.bookingId,
            userId = payment.userId,
            amount = payment.amount,
            paymentMethod = payment.paymentMethod,
            status = payment.status
        )
        return paymentRepository.savePayment(newPayment).toDto()
    }

    @PutMapping("/{id}")
    fun updatePayment(@PathVariable id: String, @RequestBody payment: UpdatePaymentDto): PaymentDto? {
        return paymentRepository.getPaymentById(id)?.let {
            paymentRepository.savePayment(
                it.copy(
                    bookingId = payment.bookingId,
                    userId = payment.userId,
                    amount = payment.amount,
                    paymentMethod = payment.paymentMethod,
                    status = payment.status
                )
            )
        }?.toDto()
    }

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable id: String) {
        paymentRepository.deletePayment(id)
    }

    private fun Payment.toDto() = PaymentDto(
        id = this.id,
        bookingId = this.bookingId,
        userId = this.userId,
        amount = this.amount,
        paymentMethod = this.paymentMethod,
        status = this.status
    )
}