package com.songguoxiong.wenshu.utils.old.docid;

import com.songguoxiong.wenshu.utils.old.common.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class RunEvalParser {
    private static final Pattern pattern = Pattern.compile("com\\.str\\._KEY=\"(?<key>\\w+)\"");

    public static String parse(String runEval) throws IllegalArgumentException, ScriptException, DataFormatException {
        // TODO 2019.09.10更新，w63好像不是脏数据了，没仔细测试
        //if (runEval.startsWith("w63")) {
        //    throw new IllegalArgumentException("invalid RunEval: w63");
        //}

        String js = ZlibUtil.unzip(runEval);

        if (js.contains(StringUtils.toUnicode("系统繁忙"))) {
            throw new IllegalArgumentException("invalid RunEval: 系统繁忙");
        }

        js = js.replace("_[_][_](", "return ");
        js = js.substring(0, js.length() - 4);
        js = "function getKey(){" + js + "}";

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
}
