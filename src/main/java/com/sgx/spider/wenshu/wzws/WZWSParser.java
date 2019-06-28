package com.sgx.spider.wenshu.wzws;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WZWSParser {
    private final static Pattern pattern = Pattern.compile("dynamicurl\\|(?<path>.+?)\\|wzwsquestion\\|(?<question>.+?)\\|wzwsfactor\\|(?<factor>\\d+)");

    public static String parse(String text) {
        Matcher m = pattern.matcher(text);
        if (!m.find()) {
            throw new IllegalArgumentException("没有匹配到相关参数，请检查text");
        }

        String question = m.group("question");
        int factor = Integer.parseInt(m.group("factor"));
        String path = m.group("path");

        int sum = 0;
        for (int i = 0; i < question.length(); i++) {
            sum += (int) question.charAt(i);
        }
        sum = sum * factor + 111111;

        String challenge = "WZWS_CONFIRM_PREFIX_LABEL" + sum;
        String queryParams = "wzwschallenge=" + Base64.getEncoder().encodeToString(challenge.getBytes());

        return "http://wenshu.court.gov.cn" + path + "?" + queryParams;
    }
}