package com.cesarwillymc.cripto.core.common


import javax.crypto.Cipher

class CriptoAESCBC:Criptografia() {

    override fun encrypt(word: String,password:String): String {
        val typeKey = generateKey()
        val cipher = Cipher.getInstance(aescbcConst)
        cipher.init(Cipher.ENCRYPT_MODE, typeKey)
        val ciphertext: ByteArray = cipher.doFinal(password.toByteArray())
        return String(ciphertext)
    }

    override fun deEncrypt(wordEncrypt: String,password: String): String {
        val typeKey = generateKey()
        val cipher = Cipher.getInstance(aescbcConst)
        cipher.init(Cipher.DECRYPT_MODE, typeKey)
        val ciphertext: ByteArray = cipher.doFinal(password.toByteArray())
        return String(ciphertext)
    }
}