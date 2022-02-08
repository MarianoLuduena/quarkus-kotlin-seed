package org.seed.application.port.`in`

import org.seed.domain.Character
import java.util.concurrent.CompletionStage

interface GetCharacterByIdQuery {
    fun get(id: Int): CompletionStage<Character>
}
