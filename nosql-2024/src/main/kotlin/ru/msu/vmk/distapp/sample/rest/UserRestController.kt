package ru.msu.vmk.distapp.sample.rest

import org.springframework.web.bind.annotation.*
import ru.msu.vmk.distapp.sample.dto.UpdateUserDto
import ru.msu.vmk.distapp.sample.dto.UserDto
import ru.msu.vmk.distapp.sample.model.User
import ru.msu.vmk.distapp.sample.repository.UserRepository
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserRestController(
    val userRepository: UserRepository
) {

    @GetMapping
    fun listUsers(): List<UserDto> {
        return userRepository.listUsers().map { it.toDto() }
    }

    @PostMapping
    fun addUser(@RequestBody user: UpdateUserDto): UserDto {
        val newUser = User(
            id = UUID.randomUUID().toString(),
            name = user.name,
            email = user.email,
            phone = user.phone
        )
        return userRepository.saveUser(newUser).toDto()
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: String, @RequestBody user: UpdateUserDto): UserDto? {
        return userRepository.getUserById(id)?.let {
            userRepository.saveUser(
                it.copy(
                    name = user.name,
                    email = user.email,
                    phone = user.phone
                )
            )
        }?.toDto()
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) {
        userRepository.deleteUser(id)
    }

    private fun User.toDto() = UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone
    )
}