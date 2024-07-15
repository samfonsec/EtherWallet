package com.samfonsec.etherwallet.ui.newAccount

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samfonsec.etherwallet.data.repository.AccountRepository
import com.samfonsec.etherwallet.model.ApiResponse.Error
import com.samfonsec.etherwallet.model.ApiResponse.Success
import com.samfonsec.etherwallet.model.UiState
import com.samfonsec.etherwallet.model.UserData
import com.samfonsec.etherwallet.utils.KeyHandler
import kotlinx.coroutines.launch


class NewAccountViewModel(
    private val accountRepository: AccountRepository,
    private val keyHandler: KeyHandler
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>(UiState.Default)
    val uiState: LiveData<UiState> = _uiState

    var deviceId: String = ""
        private set

    init {
        keyHandler.generateKeyPair()
    }

    @SuppressLint("HardwareIds")
    fun generateDeviceID(contentResolver: ContentResolver?) {
        deviceId = try {
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: RuntimeException) {
            ""
        }
    }

    fun createAccount(userData: UserData) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            accountRepository.createAccount(
                userData.copy(deviceID = deviceId, publicKey = keyHandler.publicKey)
            ).let { result ->
                when (result) {
                    is Success -> onResponse(result.data)
                    is Error -> onResponse(result.errorMessage)
                }
            }
            _uiState.value = UiState.Default
        }
    }

    private fun onResponse(result: String?) {
        _uiState.value = UiState.Feedback(result)
    }
}