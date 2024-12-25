package ru.msu.vmk.distapp.sample.repository

import org.opensearch.data.client.osc.OpenSearchTemplate
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.dto.UserDto

@Repository
class OpenSearchUserRepository(
    val openSearchTemplate: OpenSearchTemplate
) {

    val indexCoordinates = IndexCoordinates.of("users_index")

    fun search(query: String): List<UserDto> {
        val criteria = Criteria("name").contains(query)
            .or(Criteria("email").contains(query))
            .or(Criteria("phone").contains(query))
        val criteriaQuery = CriteriaQuery(criteria, Pageable.ofSize(100))
        return openSearchTemplate.search(criteriaQuery, UserDto::class.java, indexCoordinates)
            .map { searchHit ->
                val user = searchHit.content
                UserDto(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    phone = user.phone
                )
            }.toList()
    }

    fun save(user: UserDto) {
        openSearchTemplate.save(user, indexCoordinates)
    }

    fun delete(id: String) {
        openSearchTemplate.delete(id, indexCoordinates)
    }
}

data class UserDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val phone: String?
)