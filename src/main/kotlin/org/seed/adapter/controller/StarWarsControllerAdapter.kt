package org.seed.adapter.controller

import io.quarkus.logging.Log
import org.seed.adapter.controller.model.StarWarsCharacterControllerModel
import org.seed.application.port.`in`.GetCharacterByIdQuery
import java.util.concurrent.CompletionStage
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam

@Path("/api/v1/characters")
class StarWarsControllerAdapter(private val getCharacterByIdQuery: GetCharacterByIdQuery) {

    @GET
    @Path("/{id}")
    fun get(@PathParam("id") id: Int): CompletionStage<StarWarsCharacterControllerModel> {
        Log.infov("Got query for character {0}", id)
        return getCharacterByIdQuery.get(id).thenApply {
            StarWarsCharacterControllerModel.fromDomain(it)
                .also { response -> Log.infov("Sending response to character query {0}", response) }
        }
    }

}
