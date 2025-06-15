package com.coworking.auth_service.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import java.util.Map;

public interface IMethodInfoGoogle {
    Map<String,String> getInfoForAccountGoogle(String token);
}
