package com.example.FireFly_backend.exceptions.token;


import com.example.FireFly_backend.exceptions.common.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {
    public ExpiredTokenException() {
        super("Токенът е изтекъл!");
    }
}
