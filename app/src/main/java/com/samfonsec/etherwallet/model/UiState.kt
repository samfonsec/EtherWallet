package com.samfonsec.etherwallet.model

sealed class UiState {
    data object Default : UiState()
    data object Loading : UiState()
    class Feedback(val message: String?) : UiState()
}