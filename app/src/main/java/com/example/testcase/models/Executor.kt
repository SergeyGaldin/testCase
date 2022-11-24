package com.example.testcase.models

class Executor(private var nameExecutor: String, private var organizationExecutor: String) {
    val getNameExecutor: String
        get() = nameExecutor

    val getOrganizationExecutor: String
        get() = organizationExecutor
}