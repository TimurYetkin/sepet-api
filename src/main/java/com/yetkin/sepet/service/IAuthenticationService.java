package com.yetkin.sepet.service;

import com.yetkin.sepet.model.entity.User;


public interface IAuthenticationService {
    User signInAndReturnJWT(User signInRequest);
}
