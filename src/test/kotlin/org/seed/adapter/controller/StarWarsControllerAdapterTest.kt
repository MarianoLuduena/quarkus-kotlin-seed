package org.seed.adapter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.seed.application.port.`in`.GetCharacterByIdQuery
import org.seed.mock.CharacterMockFactory
import java.util.concurrent.CompletableFuture
import javax.inject.Inject
import javax.ws.rs.NotFoundException

@QuarkusTest
class StarWarsControllerAdapterTest {

    @Inject
    private var objectMapper: ObjectMapper? = null

    @InjectMock
    private var getCharacterByIdQuery: GetCharacterByIdQuery? = null

    @Test
    fun testGetCharacterByIdSuccessfully() {
        Mockito.`when`(getCharacterByIdQuery!!.get(Mockito.eq(CHARACTER_ID)))
            .thenReturn(CompletableFuture.completedFuture(CharacterMockFactory.getCharacter()))

        val expected = objectMapper!!.writeValueAsString(CharacterMockFactory.getCharacterControllerModel())

        given()
            .`when`().get("$CHARACTERS_BASE_URL/{id}", CHARACTER_ID)
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(`is`(expected))
    }

    @Test
    fun testGetCharacterByIdNotFound() {
        Mockito.`when`(getCharacterByIdQuery!!.get(Mockito.eq(CHARACTER_ID)))
            .thenReturn(CompletableFuture.failedFuture(NotFoundException("Character not found")))

        given()
            .`when`().get("$CHARACTERS_BASE_URL/{id}", CHARACTER_ID)
            .then()
            .log().all()
            .statusCode(404)
            .contentType(ContentType.JSON)
            .body("status", `is`(404))
            .body("code", `is`(102))
            .body("detail", `is`("Resource not found"))
    }

    companion object {
        private const val CHARACTERS_BASE_URL = "/api/v1/characters"
        private const val CHARACTER_ID = 1
    }

}
