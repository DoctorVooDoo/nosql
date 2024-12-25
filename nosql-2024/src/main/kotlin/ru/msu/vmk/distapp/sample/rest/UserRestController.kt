package ru.msu.vmk.distapp.sample.rest

import org.springframework.web.bind.annotation.*
import ru.msu.vmk.distapp.sample.dto.UpdateUserDto
import ru.msu.vmk.distapp.sample.dto.UserDto
import ru.msu.vmk.distapp.sample.model.User
import ru.msu.vmk.distapp.sample.repository.UserRepository
import ru.msu.vmk.distapp.sample.repository.OpenSearchUserRepository
import java.util.UUID

@RestController
@RequestMapping("/user")
class UserRestController(
    val userRepository: UserRepository,
    val openSearchUserRepository: OpenSearchUserRepository
) {

    @GetMapping("/search")
    fun searchUsers(@RequestParam query: String): List<UserDto> {
        return openSearchUserRepository.search(query)
    }

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
        return userRepository.saveUser(newUser).toDto().also {
            openSearchUserRepository.save(it)
        }
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
        }?.toDto()?.also {
            openSearchUserRepository.save(it)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String) {
        userRepository.deleteUser(id)
        openSearchUserRepository.delete(id)
    }

    private fun User.toDto() = UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone
    )
}