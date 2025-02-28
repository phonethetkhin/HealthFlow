package com.ptk.healthflow.util

sealed class ApiException(message: String) : Exception(message)

class TokenExpiredException(message: String) : ApiException(message) // 4xx Errors
class ClientErrorException(message: String) : ApiException(message) // 4xx Errors
class NotImplementedException(message: String) : ApiException(message) // 4xx Errors
class MissingParamsException(message: String) : ApiException(message) // 4xx Errors
class InternalServerExcetion(message: String) : ApiException(message) // 4xx Errors
class ServiceUnavailableException(message: String) : ApiException(message) // 4xx Errors
class ServerErrorException(message: String) : ApiException(message) // 5xx Errors
class NetworkException(message: String) : ApiException(message) // Network Issues
class UnknownApiException(message: String) : ApiException(message) // Other Errors

