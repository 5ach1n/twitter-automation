package com.eltropy;

import io.restassured.response.Response;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author sachinb
 * @created 31/12/2020 - 12:16 AM
 */
public class CommonUtil {

    public static List<Map<String, Object>> getNoReTweets(Response response){
        List<Map<String, Object>> tweets = response.jsonPath().get();
        tweets = tweets.stream().filter(x -> x.get("retweeted").equals(false)).collect(Collectors.toList());
        return tweets;
    }

    public static List<Map<String, Object>> getTopRetweets(List<Map<String, Object>> tweets){
        tweets.sort(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (int)o2.get("retweet_count") - (int) o1.get("retweet_count");
            }
        });

        return tweets.stream().limit(10).collect(Collectors.toList());
    }
}
