package com.example.testcase.models

object User {
    private lateinit var login: String
    private lateinit var password: String

    fun getLogin(): String {
        return login
    }

    fun getPassword(): String {
        return password
    }

    fun setData(login: String, password: String) {
        this.login = login
        this.password = password
    }
}