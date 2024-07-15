package com.samfonsec.etherwallet.data.api

import com.google.gson.JsonObject
import com.samfonsec.etherwallet.model.UserData
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountApi {

    @POST(CREATE_DEVICE_ACCOUNT)
    suspend fun createDeviceAccount(@Body userData: UserData): JsonObject

    companion object {
        private const val CREATE_DEVICE_ACCOUNT = "Users/AddDeviceAccount"
    }
}