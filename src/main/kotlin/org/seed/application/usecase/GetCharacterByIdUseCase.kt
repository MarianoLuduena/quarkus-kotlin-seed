package org.seed.application.usecase

import io.quarkus.logging.Log
import org.seed.application.port.`in`.GetCharacterByIdQuery
import org.seed.application.port.out.StarWarsRepository
import org.seed.domain.Character
import java.util.concurrent.Executor
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
class GetCharacterByIdUseCase(
    private val starWarsRepository: StarWarsRepository,
    @Named("asyncExecutor") private val executor: Executor
) : GetCharacterByIdQuery {

    override fun get(id: Int): CompletionStage<Character> {
        return CompletableFuture.supplyAsync({
            Log.infov("Getting info for character {0}", id)
            starWarsRepository.getCharacterById(id).also {
                Log.infov("Got info for character: {0}", it)
            }
        }, executor)
    }

}
