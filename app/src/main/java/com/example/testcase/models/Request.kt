package com.example.testcase.models

object Request {
    private lateinit var nameRequest: String
    private lateinit var priorityRequest: String
    private lateinit var statusRequest: String
    private lateinit var dateRequest: String
    private lateinit var executorRequest: String
    val getNameRequest: String
        get() = nameRequest

    val getPriorityRequest: String
        get() = priorityRequest

    val getStatusRequest: String
        get() = statusRequest

    val getDateRequest: String
        get() = dateRequest

    val getExecutorRequest: String
        get() = executorRequest

    fun setData(
        nameRequest: String,
        priorityRequest: String,
        statusRequest: String,
        dateRequest: String,
        executorRequest: String
    ) {
        this.nameRequest = nameRequest
        this.priorityRequest = priorityRequest
        this.statusRequest = statusRequest
        this.dateRequest = dateRequest
        this.executorRequest = executorRequest
    }

}