package com.example.testcase.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class Request(
    private var idRequest: String,
    private var nameRequest: String,
    private var depositRequest: String,
    private var serviceRequest: String,
    private var priorityRequest: String,
    private var statusRequest: String,
    private var dateCreateRequest: String,
    private var dateBeginRequest: String,
    private var dateEndRequest: String,
    private var authorRequest: String,
    private var executorRequest: String,
    private var organizationRequest: String
) : Parcelable {
    val getIdRequest: String
        get() = idRequest

    val getNameRequest: String
        get() = nameRequest

    val getDepositRequest: String
        get() = depositRequest

    val getServiceRequest: String
        get() = serviceRequest

    val getPriorityRequest: String
        get() = priorityRequest

    val getStatusRequest: String
        get() = statusRequest

    val getDateCreateRequest: String
        get() = dateCreateRequest

    val getDateBeginRequest: String
        get() = dateBeginRequest

    val getDateEndRequest: String
        get() = dateEndRequest

    val getAuthorRequest: String
        get() = authorRequest

    val getExecutorRequest: String
        get() = executorRequest

    val getOrganizationRequest: String
        get() = organizationRequest

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel?, p1: Int) {}
}