package com.cesarwillymc.cripto.core.repo

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences

import androidx.security.crypto.MasterKey
import com.cesarwillymc.cripto.BuildConfig
import com.cesarwillymc.cripto.app.App


class SharedPreferences {
    companion object {
        fun init(): SharedPreferences {


            val masterKey = MasterKey.Builder(App.getContextApp())
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                App.getContextApp(),
                BuildConfig.SHARED_PREFERENCE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }
}