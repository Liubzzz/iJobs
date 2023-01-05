package com.example.ijobs.ui

object ProfileCharacteristics {

    private var username: String? = null
    private var email: String? = null
    private var key : String? = null

    fun getKey(): String?{
        return key
    }

    fun setKey(savekey: String){
        key = savekey
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(savedata: String) {
        username = savedata
    }

    fun getEmail(): String?{
        return email
    }

    fun setEmail(saveemail: String){
        email = saveemail
    }

}