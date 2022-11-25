package com.example.testcase.models

class SetRequest(
    private var nameRequest: String,
    private var depositRequest: Int,
    private var serviceRequest: Int,
    private var executorRequest: Int,
    private var priorityRequest: Int,
    private var dateCreationRequest: String,
    private var dateBegineRequest: String,
    private var authorRequest: String
) {
    val getNameRequest: String
        get() = nameRequest

    val getDepositRequest: Int
        get() = depositRequest

    val getServiceRequest: Int
        get() = serviceRequest

    val getExecutorRequest: Int
        get() = executorRequest

    val getPriorityRequest: Int
        get() = priorityRequest

    val getDateCreationRequest: String
        get() = dateCreationRequest

    val getDateBegineRequest: String
        get() = dateBegineRequest

    val getAuthorRequest: String
        get() = authorRequest
}