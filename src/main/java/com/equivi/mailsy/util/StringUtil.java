package com.equivi.mailsy.util;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtil {

    public static String buildStringWithSeparator(List<String> list, char separator) {
        StringBuilder stringAppender = new StringBuilder();

        if (list != null && !list.isEmpty()) {
            for (String value : list) {
                stringAppender.append(value);
                stringAppender.append(separator);
            }
            stringAppender.deleteCharAt(stringAppender.length() - 1);
        }

        return stringAppender.toString();
    }

    public static String buildQueryParameters(Map<String, String> parameterMap) {

        StringBuilder sbQueryParam = new StringBuilder();

        if (parameterMap != null && !parameterMap.isEmpty()) {
            Set<String> keySet = parameterMap.keySet();
            Iterator<String> keyIterator = keySet.iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                if (!parameterMap.get(key).isEmpty()) {
                    sbQueryParam.append(key);
                    sbQueryParam.append("=");
                    sbQueryParam.append(parameterMap.get(key));
                    sbQueryParam.append("&");
                }
            }
            sbQueryParam.deleteCharAt(sbQueryParam.length() - 1);
        }

        return sbQueryParam.toString();
    }
}
