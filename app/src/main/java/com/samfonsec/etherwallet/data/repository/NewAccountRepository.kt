package com.samfonsec.etherwallet.data.repository

import com.samfonsec.etherwallet.data.api.AccountApi
import com.samfonsec.etherwallet.model.ApiResponse
import com.samfonsec.etherwallet.model.UserData

class NewAccountRepository(
    private val accountApi: AccountApi
) : AccountRepository {

    override suspend fun createAccount(userData: UserData): ApiResponse<String> {
        return try {
            ApiResponse.Success(accountApi.createDeviceAccount(userData).toString())
        } catch (e: Exception) {
            ApiResponse.Error(e.message)
        }
    }
}