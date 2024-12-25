package ru.msu.vmk.distapp.sample.rest

import org.springframework.web.bind.annotation.*
import ru.msu.vmk.distapp.sample.dto.RoomDto
import ru.msu.vmk.distapp.sample.dto.UpdateRoomDto
import ru.msu.vmk.distapp.sample.dto.UserDto
import ru.msu.vmk.distapp.sample.model.Room
import ru.msu.vmk.distapp.sample.repository.OpenSearchRoomRepository
import ru.msu.vmk.distapp.sample.repository.RoomRepository
import java.util.UUID

@RestController
@RequestMapping("/room")
class RoomRestController(
    val roomRepository: RoomRepository,
    val openSearchRoomRepository: OpenSearchRoomRepository
) {

    @GetMapping("/search")
    fun searchRooms(@RequestParam query: String): List<RoomDto> {
        return openSearchRoomRepository.search(query)
    }

    @GetMapping
    fun listRooms(): List<RoomDto> {
        return roomRepository.listRooms().map { it.toDto() }
    }

    @PostMapping
    fun addRoom(@RequestBody room: UpdateRoomDto): RoomDto {
        val newRoom = Room(
            id = UUID.randomUUID().toString(),
            title = room.title,
            description = room.description,
            pricePerNight = room.pricePerNight,
            location = room.location,
            amenities = room.amenities
        )
        return roomRepository.saveRoom(newRoom).toDto().also {
            openSearchRoomRepository.save(it)
        }
    }

    @PutMapping("/{id}")
    fun updateRoom(@PathVariable id: String, @RequestBody room: UpdateRoomDto): RoomDto? {
        return roomRepository.getRoomById(id)?.let {
            roomRepository.saveRoom(
                it.copy(
                    title = room.title,
                    description = room.description,
                    pricePerNight = room.pricePerNight,
                    location = room.location,
                    amenities = room.amenities
                )
            )
        }?.toDto()?.also {
            openSearchRoomRepository.save(it)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteRoom(@PathVariable id: String) {
        roomRepository.deleteRoom(id)
        openSearchRoomRepository.delete(id)
    }

    private fun Room.toDto() = RoomDto(
        id = this.id,
        title = this.title,
        description = this.description,
        pricePerNight = this.pricePerNight,
        location = this.location,
        amenities = this.amenities
    )
}