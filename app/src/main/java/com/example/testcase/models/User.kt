package com.example.testcase.models

object User {
    private lateinit var login: String
    private lateinit var password: String
    private lateinit var user_name: String
    private lateinit var organization: String
    private lateinit var role: String

    val getLogin: String
        get() = login

    val getPassword: String
        get() = password

    val getUserName: String
        get() = user_name

    val getOrganization: String
        get() = organization

    val getRole: String
        get() = role

    fun setExtraData(user_name: String, organization: String, role: String) {
        this.user_name = user_name
        this.organization = organization
        this.role = role
    }

    fun setData(login: String, password: String) {
        this.login = login
        this.password = password
    }
}