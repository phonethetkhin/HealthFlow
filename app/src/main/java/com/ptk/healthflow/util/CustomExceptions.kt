package com.ptk.healthflow.util

sealed class ApiException(message: String) : Exception(message)

class TokenExpiredException(message: String) : ApiException(message) // 4xx Errors
class ClientErrorException(message: String) : ApiException(message) // 4xx Errors
class ServerErrorException(message: String) : ApiException(message) // 5xx Errors
class NetworkException(message: String) : ApiException(message) // Network Issues
class UnknownApiException(message: String) : ApiException(message) // Other Errors

