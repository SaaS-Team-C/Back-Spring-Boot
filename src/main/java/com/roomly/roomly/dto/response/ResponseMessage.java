package com.roomly.roomly.dto.response;

public interface ResponseMessage {

    String SUCCESS = "Success";

    // Http Status 400(BAD_REQUEST)
    String DUPLICATED_IMAGE = "Duplicated image. ";
    String DUPLICATED_ACCOMMODATION_NAME = "Duplicated accommodation name. ";
    String DUPLICATED_ROOM = "Duplicated room. ";
    String DUPLICATED_ID = "Duplicated id. ";
    String DUPLICATED_TELNUMBER = "Duplicated telnumber. ";
    String DUPLICATED_BUSINESS_NUMBER = "Duplicated business number. ";
    String DUPLICATED_PASSWORD = "Duplicated password. ";
    String DUPLICATED_INTRODUCE = "Duplicated introduce. ";
    String NO_EXIST_ROOM = "NO exist room. ";
    String NO_EXIST_TYPE = "No exist type. ";
    String NO_EXIST_INTRODUCE = "No exist introduce. ";
    String NO_EXIST_USER_ID = "No exist user id. ";
    String NO_EXIST_ACCOMMODATION = "No exist accommodation. ";
    String NO_EXIST_IMAGE = "NO exist image. ";
    String VALIDATION_FALIL = "Validation fail. ";
    String NO_EXIST_RESERVATION = "No exist reservation. ";
    String NO_EXIST_GUEST = "No exist guest. ";
    String NO_EXIST_HOST = "No exist host. ";
    String NO_EXIST_USE_INFORMATION = "No exist use informdation. ";
    String ENTRY_FAIL = "Entry fail. ";
    String NO_EXIST_RESERVATION_ID = "No exist reservation id";
    String NOT_MATCH_VALUE = "Not match value.";
    String NO_EXIST_REVIEW_ID = "No exist review id.";
    String NO_EXIST_BOOKMARK = "No exist BookMark id And accommodation name";
    String NO_EXIST_PAYMENT_ID ="No exist Payment Id";

    // Http Status 401(UNAUTHORIZED)
    String NO_PERMISSION = "No permission";
    String AUTHENTICATION_FAIL = "authentication fail. ";
    String TEL_AUTH_FAILE = "Tel number authentication fail. ";
    String SIGN_IN_FAIL = "Sign in fail. ";
    String DELETE_FAIL = "Delete fail. ";
    String EXISTED_RESERVATION = "Existed reservation. ";

    // http Status 500(INTERNER_SERVER_ERROR)
    String MESSAGE_SEND_FAILE = "Auth number send failed. ";
    String TOKEN_CREATE_FAIL = "Token creation failed. ";
    String DATABASE_ERROR = "Database error. ";

}
