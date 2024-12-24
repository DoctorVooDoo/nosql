package ru.msu.vmk.distapp.sample.rest

import org.springframework.web.bind.annotation.*
import ru.msu.vmk.distapp.sample.dto.BookingDto
import ru.msu.vmk.distapp.sample.dto.UpdateBookingDto
import ru.msu.vmk.distapp.sample.model.Booking
import ru.msu.vmk.distapp.sample.repository.BookingRepository
import java.util.UUID

@RestController
@RequestMapping("/booking")
class BookingRestController(
    val bookingRepository: BookingRepository
) {

    @GetMapping("/user/{userId}")
    fun listBookingsByUser(@PathVariable userId: String): List<BookingDto> {
        return bookingRepository.listBookingsByUser(userId).map { it.toDto() }
    }

    @PostMapping
    fun addBooking(@RequestBody booking: UpdateBookingDto): BookingDto {
        val newBooking = Booking(
            id = UUID.randomUUID().toString(),
            roomId = booking.roomId,
            userId = booking.userId,
            dateIn = booking.dateIn,
            dateOut = booking.dateOut,
            totalPrice = booking.totalPrice,
            status = booking.status
        )
        return bookingRepository.saveBooking(newBooking).toDto()
    }

    @PutMapping("/{id}")
    fun updateBooking(@PathVariable id: String, @RequestBody booking: UpdateBookingDto): BookingDto? {
        return bookingRepository.getBookingById(id)?.let {
            bookingRepository.saveBooking(
                it.copy(
                    roomId = booking.roomId,
                    userId = booking.userId,
                    dateIn = booking.dateIn,
                    dateOut = booking.dateOut,
                    totalPrice = booking.totalPrice,
                    status = booking.status
                )
            )
        }?.toDto()
    }

    @DeleteMapping("/{id}")
    fun deleteBooking(@PathVariable id: String) {
        bookingRepository.deleteBooking(id)
    }

    private fun Booking.toDto() = BookingDto(
        id = this.id,
        roomId = this.roomId,
        userId = this.userId,
        dateIn = this.dateIn,
        dateOut = this.dateOut,
        totalPrice = this.totalPrice,
        status = this.status
    )
}