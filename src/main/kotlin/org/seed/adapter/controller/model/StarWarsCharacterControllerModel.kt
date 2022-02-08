package org.seed.adapter.controller.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.seed.domain.Character
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StarWarsCharacterControllerModel(
    val name: String,
    val height: Int,
    val mass: Int,
    val hairColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {

    companion object {
        fun fromDomain(domain: Character): StarWarsCharacterControllerModel {
            return StarWarsCharacterControllerModel(
                name = domain.name,
                height = domain.height,
                mass = domain.mass,
                hairColor = domain.hairColor,
                eyeColor = domain.eyeColor,
                birthYear = domain.birthYear,
                gender = domain.gender,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt
            )
        }
    }

}
