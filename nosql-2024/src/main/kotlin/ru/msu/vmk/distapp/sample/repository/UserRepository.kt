package ru.msu.vmk.distapp.sample.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.model.User

@Repository
class UserRepository(val mongoTemplate: MongoTemplate) {

    fun listUsers(): List<User> {
        return mongoTemplate.findAll<User>()
    }

    fun getUserById(userId: String): User? {
        return mongoTemplate.findById(userId)
    }

    fun saveUser(user: User): User {
        return mongoTemplate.save(user)
    }

    fun deleteUser(userId: String) {
        val criteria = Criteria.where("_id").`is`(userId)
        mongoTemplate.remove(Query.query(criteria), User::class.java)
    }
}