package com.samfonsec.etherwallet.data.repository

import com.samfonsec.etherwallet.model.ApiResponse
import com.samfonsec.etherwallet.model.UserData

interface AccountRepository {
    suspend fun createAccount(userData: UserData): ApiResponse<String>
}