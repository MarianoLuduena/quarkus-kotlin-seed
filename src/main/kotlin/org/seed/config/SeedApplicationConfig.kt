package org.seed.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(
    prefix = "seed",
    namingStrategy = ConfigMapping.NamingStrategy.KEBAB_CASE
)
interface SeedApplicationConfig {

    fun starWarsRepository(): StarWarsRepository

    interface StarWarsRepository {
        fun baseUrl(): String
        fun peoplePath(): String
    }

}
