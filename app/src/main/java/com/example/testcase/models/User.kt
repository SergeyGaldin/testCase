package com.example.testcase.models

object User {
    private lateinit var login: String
    private lateinit var password: String
    private lateinit var user_name: String
    private lateinit var organization: String

    fun getLogin(): String {
        return login
    }

    fun getPassword(): String {
        return password
    }

    fun getUserName(): String {
        return user_name
    }

    fun getOrganization(): String {
        return organization
    }

    fun setUserName(user_name: String) {
        this.user_name = user_name
    }

    fun setOrganization(organization: String) {
        this.organization = organization
    }

    fun setData(login: String, password: String) {
        this.login = login
        this.password = password
    }
}