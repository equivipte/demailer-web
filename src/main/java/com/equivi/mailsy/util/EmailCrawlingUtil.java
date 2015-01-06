package com.equivi.mailsy.util;

import com.equivi.mailsy.service.constant.dEmailerWebPropertyKey;

import java.util.regex.Pattern;

public class EmailCrawlingUtil {
    final static String REGEX_MAIL_KEYWORD = "^((?!(" + WebConfigUtil.getValue(dEmailerWebPropertyKey.EMAIL_CRAWLING_FILTER) + ")).)*$";
    final static Pattern PATTERN_MAIL_FILTERS = Pattern.compile(".*(\\.(ashx|youtube|css|axd|js|bmp|gif|jpe?g|doc|png|xls|xlsx|tiff?|mid|mp2|mp3|htm|csv|pptx|ppt|mp4|wav|avi|mov|mpeg|ram|m4v|rm|smil|wmv|swf|wma|rar|gz))$");
    private final static String REGEX_MAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    final static Pattern PATTERN_MAIL = Pattern.compile(REGEX_MAIL);
}
