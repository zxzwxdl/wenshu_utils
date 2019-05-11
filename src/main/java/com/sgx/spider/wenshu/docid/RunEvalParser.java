package com.sgx.spider.wenshu.docid;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunEvalParser {
    private static final Pattern pattern = Pattern.compile("com\\.str\\._KEY=\"(?<key>\\w+)\"");

    public static String parse(String runEval) throws IllegalArgumentException, ScriptException {
        if (runEval.startsWith("w63")) {
            throw new IllegalArgumentException("invalid RunEval: w63");
        }

        String js = ZlibUtil.unzip(runEval);

        if (js.contains(convertToUnicode("系统繁忙"))) {
            throw new IllegalArgumentException("invalid RunEval: 系统繁忙");
        }

        js = js.replaceAll("_\\[_]\\[_]\\(", "return ");
        js = js.substring(0, js.length() - 4);
        js = "function getKey () { " + js + " }";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.eval(js);
        Invocable in = (Invocable) engine;
        String result = "";
        try {
            result = (String) in.invokeFunction("getKey");
        } catch (NoSuchMethodException ignore) {

        }

        if (result.contains("while")) {
            throw new IllegalArgumentException("invalid RunEval: while(1)");
        }

        Matcher m = pattern.matcher(result);
        if (m.find()) {
            return m.group("key");
        }

        throw new IllegalArgumentException("invalid RunEval: parse error");
    }

    private static String convertToUnicode(String s) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        for (char aChar : chars) {
            stringBuilder.append("\\u").append(Integer.toString(aChar, 16));
        }
        return stringBuilder.toString();
    }
}
