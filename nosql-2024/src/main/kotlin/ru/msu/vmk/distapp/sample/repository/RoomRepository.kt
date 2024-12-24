package ru.msu.vmk.distapp.sample.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.model.Room

@Repository
class RoomRepository(val mongoTemplate: MongoTemplate) {

    fun listRooms(): List<Room> {
        return mongoTemplate.findAll<Room>()
    }

    fun getRoomById(roomId: String): Room? {
        return mongoTemplate.findById(roomId)
    }

    fun saveRoom(room: Room): Room {
        return mongoTemplate.save(room)
    }

    fun deleteRoom(roomId: String) {
        val criteria = Criteria.where("_id").`is`(roomId)
        mongoTemplate.remove(Query.query(criteria), Room::class.java)
    }
}