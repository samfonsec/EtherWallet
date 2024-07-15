package com.samfonsec.etherwallet.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme
import androidx.security.crypto.MasterKeys
import com.samfonsec.etherwallet.data.api.AccountApi
import com.samfonsec.etherwallet.data.repository.AccountRepository
import com.samfonsec.etherwallet.data.repository.NewAccountRepository
import com.samfonsec.etherwallet.ui.newAccount.NewAccountViewModel
import com.samfonsec.etherwallet.utils.Constants.BASE_URL
import com.samfonsec.etherwallet.utils.Constants.SAFE_PREFERENCES
import com.samfonsec.etherwallet.utils.KeyHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    fun provideHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
        .build()

    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideEncryptedSharedPreferences(context: Context) = EncryptedSharedPreferences.create(
        SAFE_PREFERENCES,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        PrefKeyEncryptionScheme.AES256_SIV,
        PrefValueEncryptionScheme.AES256_GCM
    )

    single { provideHttpClient() }
    single { provideRetrofit(get()) }
    single { get<Retrofit>().create(AccountApi::class.java) }
    single<AccountRepository> { NewAccountRepository(get()) }
    single { provideEncryptedSharedPreferences(androidContext()) }
    single { KeyHandler(get()) }

    viewModel { NewAccountViewModel(get(), get()) }
}
