package ru.msu.vmk.distapp.sample.repository

import org.opensearch.data.client.osc.OpenSearchTemplate
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Repository
import ru.msu.vmk.distapp.sample.dto.RoomDto

@Repository
class OpenSearchRoomRepository(
    val openSearchTemplate: OpenSearchTemplate
) {

    val indexCoordinates = IndexCoordinates.of("rooms_index")

    fun search(query: String): List<RoomDto> {
        val criteria = Criteria("title").contains(query)
            .or(Criteria("description").contains(query))
            .or(Criteria("location").contains(query))
            .or(Criteria("amenities").contains(query))

        try {
            val price = query.toDouble()
            criteria.or(Criteria("pricePerNight").`is`(price))
        } catch (e: NumberFormatException) {
            // query не число, не проверяем pricePerNight
        }
        val criteriaQuery = CriteriaQuery(criteria, Pageable.ofSize(100))
        return openSearchTemplate.search(criteriaQuery, RoomDto::class.java, indexCoordinates)
            .map { searchHit ->
                val room = searchHit.content
                RoomDto(
                    id = room.id,
                    title = room.title,
                    description = room.description,
                    pricePerNight = room.pricePerNight,
                    location = room.location,
                    amenities = room.amenities
                )
            }.toList()
    }

    fun save(room: RoomDto) {
        openSearchTemplate.save(room, indexCoordinates)
    }

    fun delete(id: String) {
        openSearchTemplate.delete(id, indexCoordinates)
    }
}

data class RoomDto(
    val id: String?,
    val title: String?,
    val description: String?,
    val pricePerNight: Double?,
    val location: String?,
    val amenities: List<String>?
)