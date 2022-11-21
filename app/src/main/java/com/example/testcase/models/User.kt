package com.example.testcase.models

object User {
    private lateinit var login: String
    private lateinit var password: String
    private lateinit var user_name: String
    private lateinit var organization: String

    val getLogin: String
        get() = login

    val getPassword: String
        get() = password

    val getUserName: String
        get() = user_name

    val getOrganization: String
        get() = organization

    fun setOrganization(organization: String) {
        this.organization = organization
    }

    fun setUserName(user_name: String) {
        this.user_name = user_name
    }

    fun setData(login: String, password: String) {
        this.login = login
        this.password = password
    }
}