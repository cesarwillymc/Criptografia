package com.cesarwillymc.cripto.core.common

import android.util.Base64
import android.util.Log
import javax.crypto.Cipher

class CriptoAes : Criptografia() {

    override fun encrypt(word: String, password: String): String {
        val secretKey = generateKey(password)
        Log.e("data","entro $secretKey")
        val cipher = Cipher.getInstance(aesConst)

        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val dataEncryptedByte = cipher.doFinal(word.toByteArray())

        return Base64.encodeToString(dataEncryptedByte, Base64.DEFAULT)
    }

    override fun deEncrypt(wordEncrypt: String, password: String): String {
        val secretKey = generateKey(password)
        Log.e("data","entro $secretKey")
        val cipher = Cipher.getInstance(aesConst)
        Log.e("data","entro instance $secretKey")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        Log.e("data","init instance ${wordEncrypt.toByteArray()}")
        val dataDeEncrypt = Base64.decode(wordEncrypt.toByteArray(), Base64.DEFAULT)
        Log.e("data","final instance $secretKey")
        val dataDeEncryptByte = cipher.doFinal(dataDeEncrypt)
        Log.e("data","finalpost instance $secretKey")
        return String(dataDeEncryptByte)
    }

}