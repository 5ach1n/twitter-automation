package com.eltropy;

import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author sachinb
 * @created 30/12/2020 - 11:46 PM
 */
public class TestTwitter {

    @BeforeTest
    public void initializeData() throws UnsupportedEncodingException {
        Response tweets = APIUtils.getTweets(10000, APIUtils.getBearer());
        List<Map<String, Object>> nonRetweets = CommonUtil.getNoReTweets(tweets);
        List<Map<String, Object>> topRetweets = CommonUtil.getTopRetweets(nonRetweets);
        System.out.println(tweets.getBody().asString());
    }

    @Test
    public void runTest(){

    }
}
