package com.cesarwillymc.cripto.core.common

import java.security.PrivateKey
import java.security.PublicKey

data class Crypto(
    val publicKey: PublicKey,
    val privateKey: PrivateKey
)
