package com.example.amplifieradmin

data class ErrorResponse(
        var errorCode: Int?,
        var statusCode: Int?,
        var sts: Int?,
        var error: String?,
        var success: String?,
        var message: String?
)