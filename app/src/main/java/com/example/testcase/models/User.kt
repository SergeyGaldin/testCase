package com.example.testcase.models

object User {
    private var login: String? = null
    private var password: String? = null
    private var userName: String? = null
    private var organization: String? = null
    private var role: String? = null

    val getLogin: String?
        get() = login

    val getPassword: String?
        get() = password

    val getUserName: String?
        get() = userName

    val getOrganization: String?
        get() = organization

    val getRole: String?
        get() = role

    fun setExtraData(userName: String, organization: String, role: String) {
        this.userName = userName
        this.organization = organization
        this.role = role
    }

    fun setData(login: String, password: String) {
        this.login = login
        this.password = password
    }
}