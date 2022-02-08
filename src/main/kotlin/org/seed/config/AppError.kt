package org.seed.config

enum class AppError(val errorCode: Int, val defaultMessage: String) {
    INTERNAL_ERROR(100, "Internal Server Error"),
    BAD_REQUEST(101, "Bad Request"),
    RESOURCE_NOT_FOUND(102, "Resource not found"),
    REQUEST_TIMEOUT(103, "Operation timed out"),
    BAD_GATEWAY(104, "Bad Gateway")
}
