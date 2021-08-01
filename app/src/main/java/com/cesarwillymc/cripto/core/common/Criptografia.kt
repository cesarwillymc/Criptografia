package com.cesarwillymc.cripto.core.common

import java.io.File
import java.security.Key
import java.security.KeyPairGenerator
import java.security.MessageDigest
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

open class Criptografia {
    protected val aesConst = "AES"
    protected val aescbcConst = "AES/CBC/PKCS5PADDING"
    private val rscConst = "RSA"

    private val sha256Const = "SHA-256"

    protected val optionRSA = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    private val cryptoBytes = 1024

    open fun encrypt(word: String, typeKey: Key): String = ""

    open fun deEncrypt(wordEncrypt: String, typeKey: Key): String = ""

    open fun encrypt(word: String, password: String): String = ""

    open fun deEncrypt(wordEncrypt: String, password: String): String = ""

    open fun createFileEncrypt(text: String,file:File) {}

    open fun readFileEncrypt(file:File): String = ""

    protected fun generateKey(password: String): SecretKeySpec {
        val sha = MessageDigest.getInstance(sha256Const)
        var key = password.toByteArray()
        key = sha.digest(key)
        return SecretKeySpec(key, aesConst)
    }
    lateinit var keyYen:SecretKey
     fun generateKey(){
        val keygen = KeyGenerator.getInstance(aesConst)
        keygen.init(256)
         keyYen= keygen.generateKey()
    }

    fun generateKeyPair(): Crypto {
        val kpg = KeyPairGenerator.getInstance(rscConst)
        kpg.initialize(cryptoBytes)
        val kp = kpg.generateKeyPair()
        return Crypto(
            publicKey = kp.public,
            privateKey = kp.private
        )
    }

}