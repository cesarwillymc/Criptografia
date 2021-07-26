package com.cesarwillymc.cripto.core.common

import android.util.Base64
import javax.crypto.Cipher

class CriptoAes : Criptografia() {

    override fun encrypt(word: String, password: String): String {
        val secretKey = generateKey(password)
        val cipher = Cipher.getInstance(aesConst)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val dataEncryptedByte = cipher.doFinal(word.toByteArray())
        return String(dataEncryptedByte)
    }

    override fun deEncrypt(wordEncrypt: String, password: String): String {
        val secretKey = generateKey(password)
        val cipher = Cipher.getInstance(aesConst)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val dataDeEncrypt = Base64.decode(wordEncrypt, Base64.DEFAULT)
        val dataDeEncryptByte = cipher.doFinal(dataDeEncrypt)
        return String(dataDeEncryptByte)
    }

}