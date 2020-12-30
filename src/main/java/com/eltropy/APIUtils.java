package com.eltropy;


import com.sun.xml.internal.messaging.saaj.util.Base64;
import io.restassured.response.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.eltropy.Constants.*;
import static io.restassured.RestAssured.given;

/**
 * @author sachinb
 * @created 30/12/2020 - 11:47 PM
 */
public class APIUtils {
    private static String getAuthorizationToken(String key, String secret) throws UnsupportedEncodingException {
        String cKey = getEncodedValue(key);
        String cSecret = getEncodedValue(secret);
        String plainToken = cKey + delimiter + cSecret;
        byte[] encodedByte = Base64.encode(plainToken.getBytes());
        return new String(encodedByte, StandardCharsets.UTF_8);
    }

    private static String getEncodedValue(String key) throws UnsupportedEncodingException {
        return URLEncoder.encode(key, encodingType);
    }

    public static Response getOauthResponse() throws UnsupportedEncodingException {
        Map<String, String> defaultHeaders = getDefaultHeaders();
        defaultHeaders.put("Authorization", basic + getAuthorizationToken(cKey, cSecret));
        return given().headers(defaultHeaders).log().all()
                .body("grant_type=client_credentials").post(host + oauthApi);
    }

    private static Map<String, String> getDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        return  headers;
    }

    public static String getBearer() throws UnsupportedEncodingException {
        Response oAuthResponse = getOauthResponse();
        return oAuthResponse.jsonPath().get("access_token");
    }
}
