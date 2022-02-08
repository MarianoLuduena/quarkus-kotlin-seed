package org.seed.application.port.out

import org.seed.domain.Character

interface StarWarsRepository {
    fun getCharacterById(id: Int): Character
}
