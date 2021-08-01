package com.cesarwillymc.cripto.app

import android.app.Application
import android.content.Context

class App: Application(){
    companion object{
        private lateinit var instance:App
        fun getInstanceApp():App = instance
        fun getContextApp(): Context =instance
        fun setInstance(instance:App){
            this.instance=instance
        }

    }
    override fun onCreate() {
        super.onCreate()
        setInstance(this)
    }
}