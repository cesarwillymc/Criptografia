package com.cesarwillymc.cripto.presentation

sealed class MainViewModelState{
    object RSA: MainViewModelState()
    object AES: MainViewModelState()
    object FILEEncrypt: MainViewModelState()

    fun isRSA()  = this is RSA
    fun isAES()  = this is AES
    fun isFILEEncrypt()  = this is FILEEncrypt
}
