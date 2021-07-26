package com.cesarwillymc.cripto.core.common

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher

class CriptoRsa:Criptografia() {

    override fun encrypt(word: String, typeKey: Key): String {

        val cipher = Cipher.getInstance(optionRSA)
        cipher.init(Cipher.DECRYPT_MODE, typeKey)
        val dataEncryptedByte = cipher.doFinal(word.toByteArray())
        return Base64.encodeToString(dataEncryptedByte,Base64.DEFAULT)
    }

    override fun deEncrypt(wordEncrypt: String, typeKey: Key): String {

        val cipher = Cipher.getInstance(aesConst)
        cipher.init(Cipher.DECRYPT_MODE, typeKey)
        val dataDeEncrypt = Base64.decode(wordEncrypt, Base64.DEFAULT)
        val dataDeEncryptByte = cipher.doFinal(dataDeEncrypt)
        return String(dataDeEncryptByte)
    }
}