package org.seed.adapter.rest

import io.quarkus.logging.Log
import org.seed.adapter.rest.model.CharacterRestModel
import org.seed.application.port.out.StarWarsRepository
import org.seed.config.SeedApplicationConfig
import org.seed.domain.Character
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.client.Client
import javax.ws.rs.core.MediaType

@ApplicationScoped
class StarWarsRestAdapter(private val client: Client, config: SeedApplicationConfig) : StarWarsRepository {

    private val swRepositoryConfig: SeedApplicationConfig.StarWarsRepository = config.starWarsRepository()

    override fun getCharacterById(id: Int): Character {
        val baseUrl = swRepositoryConfig.baseUrl()
        val path = swRepositoryConfig.peoplePath().replaceFirst(ID_PATH_PARAM, id.toString())

        Log.infov("Building request for {0}{1}", baseUrl, path)

        val characterRestModel =
            client.target(baseUrl)
                .path(path)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(CharacterRestModel::class.java)

        Log.infov("Got the response {0}", characterRestModel)

        return characterRestModel.toDomain()
    }

    companion object {
        private const val ID_PATH_PARAM: String = "{id}"
    }

}
