package com.example.testcase.models

class Request(
    private var nameRequest: String,
    private var priorityRequest: String,
    private var statusRequest: String,
    private var dateRequest: String,
    private var executorRequest: String
) {
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
}