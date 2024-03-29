package com.example.testcase.constants

object Constants {
    // Ссылки на сервер
    private const val ROOT_URL = "http://vhost287635.cpsite.ru/testCasePHP-main/v1/"
    const val URL_LOGIN = ROOT_URL + "SignIn.php"
    const val URL_UPDATE_REQUEST = ROOT_URL + "UpdateRequest.php"
    const val URL_DELETE_REQUEST = ROOT_URL + "DeleteRequest.php"
    const val URL_SET_DATA_REQUEST = ROOT_URL + "SetDataRequest.php"
    const val URL_GET_DATA_REQUEST = ROOT_URL + "GetDataRequest.php"
    const val URL_GET_DATA_EXECUTOR = ROOT_URL + "GetDataExecutor.php"
    const val URL_GET_DATA_DEPOSIT = ROOT_URL + "GetDataDeposit.php"
    const val URL_GET_DATA_SERVICE = ROOT_URL + "GetDataService.php"
    const val URL_GET_DATA_PRIORITY = ROOT_URL + "GetDataPriority.php"
}