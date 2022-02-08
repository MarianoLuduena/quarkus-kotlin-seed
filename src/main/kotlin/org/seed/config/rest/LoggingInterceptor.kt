package org.seed.config.rest

import io.quarkus.logging.Log
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter

class LoggingInterceptor : ClientResponseFilter {

    override fun filter(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        if (responseContext.status >= 400) logError(requestContext, responseContext)
        else logSuccessful(requestContext, responseContext)
    }

    private fun logSuccessful(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        Log.infov(
            "{0} {1}: {2} - {3}",
            requestContext.method,
            requestContext.uri,
            responseContext.status,
            responseContext.statusInfo.reasonPhrase
        )
    }

    private fun logError(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        val responseBody = responseContext.entityStream
            .bufferedReader()
            .readText()
            .also {
                responseContext.entityStream = it.byteInputStream()
            }

        Log.errorv(
            "{0} {1}: {2} - {3} | Body: {4} | Headers: {5}",
            requestContext.method,
            requestContext.uri,
            responseContext.status,
            responseContext.statusInfo.reasonPhrase,
            responseBody,
            responseContext.headers
        )
    }

}
