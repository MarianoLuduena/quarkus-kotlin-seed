package org.seed.adapter.rest

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.seed.adapter.rest.model.CharacterRestModel
import org.seed.config.SeedApplicationConfig
import org.seed.mock.CharacterMockFactory
import javax.ws.rs.client.Client
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

@QuarkusTest
class StarWarsRestAdapterTest {

    private val config = object : SeedApplicationConfig {
        override fun starWarsRepository(): SeedApplicationConfig.StarWarsRepository {
            return object : SeedApplicationConfig.StarWarsRepository {
                override fun baseUrl(): String = BASE_URL
                override fun peoplePath(): String = PEOPLE_URL
            }
        }
    }

    @Test
    fun testGetCharacterByIdOk() {
        val client = Mockito.mock(Client::class.java)
        val webTarget = Mockito.mock(WebTarget::class.java)
        val invocationBuilder = Mockito.mock(Invocation.Builder::class.java)

        Mockito.`when`(client.target(Mockito.eq(BASE_URL))).thenReturn(webTarget)

        Mockito.`when`(webTarget.path(Mockito.eq(PEOPLE_URL.replaceFirst("{id}", CHARACTER_ID.toString()))))
            .thenReturn(webTarget)

        Mockito.`when`(webTarget.request(Mockito.any(MediaType::class.java))).thenReturn(invocationBuilder)

        Mockito.`when`(invocationBuilder.get(Mockito.eq(CharacterRestModel::class.java)))
            .thenReturn(CharacterMockFactory.getCharacterRestModel())

        val expected = CharacterMockFactory.getCharacter()

        val adapter = StarWarsRestAdapter(client, config)

        val actual = adapter.getCharacterById(CHARACTER_ID)
        Assertions.assertEquals(expected, actual)
    }

    companion object {
        private const val BASE_URL = "http://localhost:666"
        private const val PEOPLE_URL = "/people/{id}"
        private const val CHARACTER_ID = 1

//        inline fun <reified T> any(): T = Mockito.any(T::class.java)
//        fun <T> eq(value: T): T = Mockito.eq(value)
    }

}
