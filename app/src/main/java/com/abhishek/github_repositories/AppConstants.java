package com.abhishek.github_repositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface AppConstants {
    String API_ENDPOINT = "search/repositories";
    String API_BASE_URL = "https://api.github.com/";
    String QUERY_PARAM_KEY = "q";
    String QUERY_PER_PAGE_KEY = "per_page";
    String QUERY_SORT_KEY = "sort";
    String QUERY_ORDER_KEY = "order";
    String QUERY_PAGE_KEY = "page";
    String QUERY_SORT_VALUE = "stars";
    String QUERY_ORDER_VALUE = "desc";
    String QUERY_PARAM_VALUE = "android";


    String PAGE_MAX_SIZE = "30";
    String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    String INTENT_POST = "intent_post";

    Map<String, Integer> LANGUAGE_COLOR_MAP = Collections.unmodifiableMap(
            new HashMap<String, Integer>() {{
                put("Java", R.color.color_java);
                put("Kotlin", R.color.color_kotlin);
                put("Dart", R.color.color_dart);
                put("JavaScript", R.color.color_javaScript);
                put("CSS", R.color.color_css);
            }});
}
