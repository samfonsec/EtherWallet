package com.samfonsec.etherwallet.utils

import android.util.Base64
import com.google.android.material.textfield.TextInputEditText
import java.security.Key

fun TextInputEditText.value() = text.toString().trim()

fun TextInputEditText.isValid() = text?.toString()?.isNotEmpty() ?: false

fun Key?.asString() = try {
    Base64.encodeToString(this?.encoded, Base64.DEFAULT).orEmpty()
} catch (rte: RuntimeException) {
    ""
}