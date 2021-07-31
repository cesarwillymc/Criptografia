package com.cesarwillymc.cripto.presentation

import android.os.Environment
import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesarwillymc.cripto.R
import com.cesarwillymc.cripto.app.App
import com.cesarwillymc.cripto.core.common.*
import java.io.File


class ManViewModel : ViewModel() {

    private var user = ""
    private var pass = ""
    fun updateTextUser(word: CharSequence) {
        user = word.toString()
    }

    fun updateTextPass(word: CharSequence) {
        pass = word.toString()
    }

    private var typeCrypto: Criptografia = CriptoAes()
    private var numero = 0
    fun onSplitTypeChanged(radioGroup: RadioGroup?, id: Int) {
        when (id) {
            R.id.rb_encrypt_aes -> {
                numero = 0
                typeCrypto = CriptoAes()
            }
            R.id.rb_encrypt_rsa -> {
                numero = 1
                typeCrypto = CriptoRsa()
            }

            R.id.rb_encrypt_file -> {
                numero = 2
                typeCrypto = CriptoFile()
            }




        }
    }

    val textResult = MutableLiveData<String>()
    var llave: Crypto? = null

    val errorMessage = MutableLiveData<String>()
    fun onClickListenerEncrypt() {
        try {
            when (numero) {
                0 -> {
                    if (user.isNotEmpty() && pass.isNotEmpty()) {
                        val response = typeCrypto.encrypt(user, pass)
                        textResult.value = response
                    } else {
                        errorMessage.value = "El protocolo AES necesita de usuario y contraseña"
                    }
                }
                1 -> {
                    if (user.isNotEmpty()) {
                        llave = typeCrypto.generateKeyPair()
                        val response = typeCrypto.encrypt(user, llave!!.publicKey)
                        textResult.value = response
                    } else {
                        errorMessage.value = "El protocolo RSA necesita de usuario"
                    }
                }
                2 -> {
                    if (user.isNotEmpty()) {
                        try {
                            typeCrypto.createFileEncrypt(
                                user,
                                File(Environment.getExternalStorageDirectory().path, "crytoTest")
                            )
                            textResult.value = "Creado exitosamente"
                        } catch (e: Exception) {
                            errorMessage.value = "Sucedio un error -> ${e.message}"
                        }
                    } else {
                        errorMessage.value = "No se puede guardar archivos"
                    }
                }
                3 -> {

                }
            }
        } catch (e: Exception) {
            errorMessage.value = "error -> ${e.message}"
        }
    }

    fun onClickListenerDeEncrypt() {
        try {

            when (numero) {
                0 -> {
                    if ((textResult.value ?: "").isNotEmpty() && pass.isNotEmpty()) {
                        val response = typeCrypto.deEncrypt(textResult.value ?: "", pass)
                        textResult.value = response
                    } else {
                        errorMessage.value =
                            "El protocolo AES necesita la encriptacion y la contraseña"
                    }
                }
                1 -> {
                    if (llave != null) {

                        val response =
                            typeCrypto.deEncrypt(textResult.value ?: "", llave!!.privateKey)
                        textResult.value = response
                    } else {
                        errorMessage.value = "El protocolo RSA necesita que encryptes algo "
                    }

                }
                2 -> {
                    if (user.isNotEmpty()) {
                        try {
                            val response = typeCrypto.readFileEncrypt(
                                File(
                                    Environment.getExternalStorageDirectory().path,
                                    "crytoTest"
                                )
                            )
                            textResult.value = response
                        } catch (e: Exception) {
                            errorMessage.value = "Sucedio un error -> ${e.message}"
                        }
                    } else {
                        errorMessage.value = "No se puede guardar archivos"
                    }
                }
                3 -> {

                }
            }
        } catch (e: Exception) {
            errorMessage.value = "error -> ${e.message}"
        }
    }

}