package com.cesarwillymc.cripto.core.common

import java.security.Key
import java.security.KeyPairGenerator
import java.security.MessageDigest
import javax.crypto.spec.SecretKeySpec

open class Criptografia {
    protected val aesConst = "AES"
    private val rscConst = "RSA"

    private val sha256Const = "SHA-256"

    protected val optionRSA = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    private val cryptoBytes = 2048

    open fun encrypt(word: String, typeKey: Key): String = ""

    open fun deEncrypt(wordEncrypt: String, typeKey: Key): String = ""

    open fun encrypt(word: String, password: String): String = ""

    open fun deEncrypt(wordEncrypt: String, password: String): String = ""

    protected fun generateKey(password: String): SecretKeySpec {
        val sha = MessageDigest.getInstance(sha256Const)
        var key = password.toByteArray()
        key = sha.digest(key)
        return SecretKeySpec(key, aesConst)
    }

    protected fun generateKeyPair(password: String): Crypto {
        val kpg = KeyPairGenerator.getInstance(rscConst)
        kpg.initialize(cryptoBytes)
        val kp = kpg.generateKeyPair()
        return Crypto(
            publicKey = kp.public,
            privateKey = kp.private
        )
    }
}