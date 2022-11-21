package com.example.testcase.models

class Request(
    nameRequest: String,
    priorityRequest: String,
    statusRequest: String,
    dateRequest: String,
    executorRequest: String
) {
    private var nameRequests: String = nameRequest
    private var priorityRequests: String = priorityRequest
    private var statusRequests: String = statusRequest
    private var dateRequests: String = dateRequest
    private var executorRequests: String = executorRequest

    val getNameRequest: String
        get() = nameRequests

    val getPriorityRequest: String
        get() = priorityRequests

    val getStatusRequest: String
        get() = statusRequests

    val getDateRequest: String
        get() = dateRequests

    val getExecutorRequest: String
        get() = executorRequests
}