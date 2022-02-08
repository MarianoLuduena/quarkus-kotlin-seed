package org.seed.config

import io.quarkus.logging.Log
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.ws.rs.InternalServerErrorException
import javax.ws.rs.ServerErrorException
import javax.ws.rs.ServiceUnavailableException
import javax.ws.rs.BadRequestException
import javax.ws.rs.NotFoundException
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider
import kotlin.Exception

data class ApiErrorResponse(
    val name: String,
    val status: Int,
    val timestamp: String,
    val code: Int,
    val detail: String
)

abstract class ExceptionHandler {
    protected fun buildResponseError(
        httpStatus: Response.StatusType,
        ex: Throwable,
        errorCode: Int,
        errorMessage: String = ex.message ?: ""
    ): Response {
        val apiErrorResponse = ApiErrorResponse(
            name = httpStatus.reasonPhrase,
            status = httpStatus.statusCode,
            timestamp = LocalDateTime.now(ZoneOffset.UTC).toString(),
            code = errorCode,
            detail = errorMessage
        )

        return Response
            .status(httpStatus)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(apiErrorResponse)
            .build()
    }
}

@Provider
class ExceptionMapper : ExceptionHandler(), ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.INTERNAL_SERVER_ERROR,
            exception,
            AppError.INTERNAL_ERROR.errorCode,
            AppError.INTERNAL_ERROR.defaultMessage
        )
    }
}

@Provider
class WebApplicationExceptionMapper : ExceptionHandler(), ExceptionMapper<WebApplicationException> {
    override fun toResponse(exception: WebApplicationException): Response {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.INTERNAL_SERVER_ERROR,
            exception,
            AppError.INTERNAL_ERROR.errorCode,
            AppError.INTERNAL_ERROR.defaultMessage
        )
    }
}

@Provider
class BadRequestExceptionMapper : ExceptionHandler(), ExceptionMapper<BadRequestException> {
    override fun toResponse(exception: BadRequestException): Response {
        Log.error(Response.Status.BAD_REQUEST.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.BAD_REQUEST,
            exception,
            AppError.BAD_REQUEST.errorCode,
            AppError.BAD_REQUEST.defaultMessage
        )
    }
}

@Provider
class NotFoundExceptionMapper : ExceptionHandler(), ExceptionMapper<NotFoundException> {
    override fun toResponse(exception: NotFoundException): Response {
        Log.error(Response.Status.NOT_FOUND.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.NOT_FOUND,
            exception,
            AppError.RESOURCE_NOT_FOUND.errorCode,
            AppError.RESOURCE_NOT_FOUND.defaultMessage
        )
    }
}

@Provider
class ServerErrorExceptionMapper : ExceptionHandler(), ExceptionMapper<ServerErrorException> {
    override fun toResponse(exception: ServerErrorException): Response {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.INTERNAL_SERVER_ERROR,
            exception,
            AppError.INTERNAL_ERROR.errorCode,
            AppError.INTERNAL_ERROR.defaultMessage
        )
    }
}

@Provider
class InternalServerErrorExceptionMapper : ExceptionHandler(), ExceptionMapper<InternalServerErrorException> {
    override fun toResponse(exception: InternalServerErrorException): Response {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.INTERNAL_SERVER_ERROR,
            exception,
            AppError.INTERNAL_ERROR.errorCode,
            AppError.INTERNAL_ERROR.defaultMessage
        )
    }
}

@Provider
class ServiceUnavailableExceptionMapper : ExceptionHandler(), ExceptionMapper<ServiceUnavailableException> {
    override fun toResponse(exception: ServiceUnavailableException): Response {
        Log.error(Response.Status.INTERNAL_SERVER_ERROR.reasonPhrase, exception)
        return buildResponseError(
            Response.Status.INTERNAL_SERVER_ERROR,
            exception,
            AppError.INTERNAL_ERROR.errorCode,
            AppError.INTERNAL_ERROR.defaultMessage
        )
    }
}
