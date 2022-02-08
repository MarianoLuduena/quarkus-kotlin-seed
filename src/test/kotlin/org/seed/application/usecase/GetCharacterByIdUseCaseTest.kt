package org.seed.application.usecase

import io.quarkus.test.junit.QuarkusTest
import org.eclipse.microprofile.context.ManagedExecutor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.seed.application.port.out.StarWarsRepository
import org.seed.mock.CharacterMockFactory

@QuarkusTest
class GetCharacterByIdUseCaseTest {

    private val executor = ManagedExecutor.builder().maxAsync(5).maxQueued(10).build()

    @Test
    fun getCharacterByIdSuccessfully() {
        val expected = CharacterMockFactory.getCharacter()

        val swRepository = Mockito.mock(StarWarsRepository::class.java)
        Mockito.`when`(swRepository.getCharacterById(Mockito.eq(CHARACTER_ID)))
            .thenReturn(expected)

        val useCase = GetCharacterByIdUseCase(swRepository, executor)

        val actual = useCase.get(CHARACTER_ID).toCompletableFuture().join()

        Assertions.assertEquals(expected, actual)
    }

    companion object {
        private const val CHARACTER_ID = 1
    }

}
