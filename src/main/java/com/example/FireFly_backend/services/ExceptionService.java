package com.example.FireFly_backend.services;


import com.example.FireFly_backend.exceptions.common.ApiException;

public interface ExceptionService {

    void log(ApiException runtimeException);

    void log(RuntimeException runtimeException, int statusCode);
}
