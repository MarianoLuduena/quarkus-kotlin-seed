package org.seed.config

import io.opentracing.contrib.jaxrs2.client.ClientTracingFeature
import org.eclipse.microprofile.context.ManagedExecutor
import org.seed.config.rest.LoggingInterceptor
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.inject.Singleton
import javax.ws.rs.Produces
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

@Singleton
class AppConfig {

    @Produces
    @ApplicationScoped
    fun getRestClient(): Client {
        return ClientBuilder.newBuilder()
            .register(ClientTracingFeature::class.java)
            .register(LoggingInterceptor())
            .build()
    }

    @ApplicationScoped
    @Named("asyncExecutor")
    fun asyncExecutor(): ManagedExecutor {
        return ManagedExecutor.builder()
            .maxAsync(1000)
            .build()
    }

}
