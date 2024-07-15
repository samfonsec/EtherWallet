package com.samfonsec.etherwallet.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("EmailAddress") val emailAddress: String,
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("PinNumber") val pinNumber: String,
    @SerializedName("PublicKey") val publicKey: String = "",
    @SerializedName("DeviceID") val deviceID: String = "",
)