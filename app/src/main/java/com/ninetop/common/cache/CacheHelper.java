package com.ninetop.common.cache;

import java.util.Map;

/**
 * Created by jill on 2017/1/12.
 */

public class CacheHelper {

    public static String url2Key(String url, Map<String, String> paramMap){
        if(url == null || url.length() == 0){
            return "";
        }

        String paramString = "";
        if(paramMap != null){
            for(String param : paramMap.keySet()){
                paramString = param + "=" + paramMap.get(param) + "&";
            }
        }

        return url + "?" + paramString;
    }
}
