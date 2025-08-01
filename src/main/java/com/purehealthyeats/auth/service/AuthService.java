package com.purehealthyeats.auth.service;

import com.purehealthyeats.auth.dto.SignUpRequest;
import com.purehealthyeats.user.entity.User;

public interface AuthService {
    User registerUser(SignUpRequest signUpRequest);
}
