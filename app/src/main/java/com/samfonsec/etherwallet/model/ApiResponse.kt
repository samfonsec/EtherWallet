package com.samfonsec.etherwallet.model

sealed class ApiResponse<out T : Any> {
    class Success<out T : Any>(val data: T) : ApiResponse<T>()
    class Error(val errorMessage: String?) : ApiResponse<Nothing>()
}