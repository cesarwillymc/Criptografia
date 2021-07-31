package com.cesarwillymc.cripto.core.common

import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.cesarwillymc.cripto.app.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets

class CriptoFile : Criptografia() {
    override fun readFileEncrypt(file: File): String {
        if(!file.exists()){
            file.mkdirs()
            file.createNewFile()

        }
        val mainKey = MasterKey.Builder(App.getContextApp())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        val encryptedFile = EncryptedFile.Builder(
            App.getContextApp(),
            file,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = encryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        val plaintext: ByteArray = byteArrayOutputStream.toByteArray()
        return String(plaintext)
    }

    override fun createFileEncrypt(text: String, file: File) {
        val newFile=File(file.path,"encrypTest.txt")
        if(!file.exists()){
            file.mkdirs()
        }else{
        }

        val mainKey = MasterKey.Builder(App.getContextApp())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val encryptedFile = EncryptedFile.Builder(
            App.getContextApp(),
            newFile,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val fileContent = text
            .toByteArray(StandardCharsets.UTF_8)
        encryptedFile.openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }
    }
}