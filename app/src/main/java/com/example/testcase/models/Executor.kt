package com.example.testcase.models

class Executor(private var idExecutor: String,private var nameExecutor: String, private var roleExecutor: String) {
    val getIdExecutor: String
        get() = idExecutor

    val getNameExecutor: String
        get() = nameExecutor

    val getRoleExecutor: String
        get() = roleExecutor
}