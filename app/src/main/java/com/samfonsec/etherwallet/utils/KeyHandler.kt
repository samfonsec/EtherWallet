package com.samfonsec.etherwallet.utils

import android.content.SharedPreferences
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.core.content.edit
import com.samfonsec.etherwallet.utils.Constants.CIPHER_TYPE_FOR_RSA
import com.samfonsec.etherwallet.utils.Constants.KEY_SIZE
import com.samfonsec.etherwallet.utils.Constants.PRIVATE_KEY
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.SecureRandom
import javax.crypto.Cipher

class KeyHandler(
    private val encryptedPreferences: SharedPreferences
) {

    private var keyPair: KeyPair? = null

    val publicKey: String by lazy {
        keyPair?.public.asString()
    }

    fun generateKeyPair() {
        try {
            KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA).run {
                initialize(KEY_SIZE, SecureRandom())
                keyPair = generateKeyPair()
            }
            storePrivateKey()
        } catch (rte: RuntimeException) {
            rte.printStackTrace()
        }
    }

    private fun storePrivateKey() = keyPair?.run {
        val encryptedKey = private.asString().encrypted(public)
        encryptedPreferences.edit {
            putString(PRIVATE_KEY, encryptedKey)
        }
    }

    private fun String.encrypted(publicKey: PublicKey): String? = try {
        val cipher = Cipher.getInstance(CIPHER_TYPE_FOR_RSA)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        Base64.encodeToString(cipher.doFinal(this.toByteArray()), Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}