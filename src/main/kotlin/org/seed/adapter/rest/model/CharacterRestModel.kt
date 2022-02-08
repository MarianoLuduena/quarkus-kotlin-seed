package org.seed.adapter.rest.model

import org.seed.domain.Character
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterRestModel(
    val name: String,
    val height: String,
    val mass: String,
    @JsonProperty("hair_color") val hairColor: String,
    @JsonProperty("skin_color") val skinColor: String,
    @JsonProperty("eye_color") val eyeColor: String,
    @JsonProperty("birth_year") val birthYear: String,
    val gender: String,
    val homeworld: String,
    val films: List<String>,
    val created: LocalDateTime,
    val edited: LocalDateTime
) {

    fun toDomain(): Character {
        return Character(
            name = name,
            height = height.toIntOrNull() ?: -1,
            mass = mass.toIntOrNull() ?: -1,
            hairColor = hairColor,
            eyeColor = eyeColor,
            birthYear = birthYear,
            gender = gender,
            createdAt = created,
            updatedAt = edited
        )
    }

}
