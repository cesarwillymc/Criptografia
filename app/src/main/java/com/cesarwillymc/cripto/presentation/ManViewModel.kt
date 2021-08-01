package com.cesarwillymc.cripto.presentation

import android.os.Environment
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarwillymc.cripto.R
import com.cesarwillymc.cripto.app.App
import com.cesarwillymc.cripto.core.common.*
import com.cesarwillymc.cripto.core.repo.SharedPreferences
import java.io.File


class ManViewModel : ViewModel() {
    lateinit var onClick: () -> Unit
    val typeState = MutableLiveData<MainViewModelState>()
    private var user = ""
    private var pass = ""
    fun updateTextUser(word: CharSequence) {
        user = word.toString()
    }

    fun updateTextPass(word: CharSequence) {
        pass = word.toString()
    }

    private var typeCrypto: Criptografia = CriptoAes()

    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {
        textResult.value = ""
        when (id) {
            R.id.rb_encrypt_aes -> {
                typeState.value = MainViewModelState.AES
                typeCrypto = CriptoAes()
            }
            R.id.rb_encrypt_rsa -> {
                typeState.value = MainViewModelState.RSA
                typeCrypto = CriptoRsa()
            }

            R.id.rb_encrypt_file -> {
                typeState.value = MainViewModelState.FILEEncrypt
                typeCrypto = CriptoFile()
            }
        }
    }

    val textResult = MutableLiveData<String>()
    var llave: Crypto? = null

    val errorMessage = MutableLiveData<String>()

    fun onClickRSA(encrypt: Boolean) {
        onClick.invoke()
        try {
            if (encrypt) {
                if (user.isNotEmpty()) {
                    llave = typeCrypto.generateKeyPair()
                    val response = typeCrypto.encrypt(user, llave!!.publicKey)
                    textResult.value = response
                } else {
                    errorMessage.value = "El protocolo RSA necesita de usuario"
                }
            } else {
                if (llave != null) {

                    val response =
                        typeCrypto.deEncrypt(textResult.value ?: "", llave!!.privateKey)
                    textResult.value = response
                } else {
                    errorMessage.value = "El protocolo RSA necesita que encryptes algo "
                }
            }
        } catch (e: Exception) {
            errorMessage.value = "error -> ${e.message}"
        }
    }

    private val aesConst = "sharedAES"
    private val fileConst = "sharedFILE"
    fun onClickAES(encrypt: Boolean) {
        onClick.invoke()
        try {
            if (encrypt) {
                if (user.isNotEmpty() && pass.isNotEmpty()) {
                    val response = typeCrypto.encrypt(user, pass)
                    SharedPreferences.init().edit().putString(aesConst, response).apply()
                    textResult.value = response
                } else {
                    errorMessage.value = "El protocolo AES necesita de usuario y contraseña"
                }
            } else {
                if ((textResult.value ?: "").isNotEmpty() && pass.isNotEmpty()) {
                    val response = typeCrypto.deEncrypt(
                        SharedPreferences.init().getString(aesConst, "") ?: "",
                        pass
                    )
                    textResult.value = response
                } else {
                    errorMessage.value =
                        "El protocolo AES necesita la encriptacion y la contraseña"
                }
            }

        } catch (e: Exception) {
            errorMessage.value = "error -> ${e.message}"
        }
    }

    fun onClickFILE(encrypt: Boolean) {
        onClick.invoke()
        try {

            if (encrypt) {
                val rutaFile = File(Environment.getExternalStorageDirectory().path, "crytoTest")
                if (user.isNotEmpty()) {
                    try {
                        typeCrypto.createFileEncrypt(
                            user,
                            rutaFile
                        )
                        SharedPreferences.init().edit().putString(fileConst, rutaFile.path).apply()
                        textResult.value = "Creado exitosamente"
                    } catch (e: Exception) {
                        errorMessage.value = "Sucedio un error -> ${e.message}"
                    }
                } else {
                    errorMessage.value = "No se puede guardar archivos"
                }
            } else {
                if (user.isNotEmpty()) {
                    try {
                        val response = typeCrypto.readFileEncrypt(
                            File(SharedPreferences.init().getString(fileConst, "") ?: "")
                        )
                        textResult.value = response
                    } catch (e: Exception) {
                        errorMessage.value = "Sucedio un error -> ${e.message}"
                    }
                } else {
                    errorMessage.value = "No se puede guardar archivos"
                }
            }
        } catch (e: Exception) {
            errorMessage.value = "error -> ${e.message}"
        }
    }
}