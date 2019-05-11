package com.sgx.spider.wenshu.wzws;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WZWSParser {

    public static String parse(String text) throws ScriptException, NoSuchMethodException {
        Matcher m = Pattern.compile("<script type=\"text/javascript\">(?<js>.+)</script>", Pattern.DOTALL).matcher(text);

        if (!m.find()) {
            throw new IllegalArgumentException("没有可以匹配的script标签，请检查text");
        }

        String js = m.group("js");

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String customJavascript = "(function(global,factory){typeof exports===\"object\"&&typeof module!==\"undefined\"?module.exports=factory(global):typeof define===\"function\"&&define.amd?define(factory):factory(global)})(typeof self!==\"undefined\"?self:typeof window!==\"undefined\"?window:typeof global!==\"undefined\"?global:this,function(global){\"use strict\";global=global||{};var _Base64=global.Base64;var version=\"2.5.1\";var buffer;if(typeof module!==\"undefined\"&&module.exports){try{buffer=eval(\"require('buffer').Buffer\")}catch(err){buffer=undefined}}var b64chars=\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\";var b64tab=function(bin){var t={};for(var i=0,l=bin.length;i<l;i++)t[bin.charAt(i)]=i;return t}(b64chars);var fromCharCode=String.fromCharCode;var cb_utob=function(c){if(c.length<2){var cc=c.charCodeAt(0);return cc<128?c:cc<2048?fromCharCode(192|cc>>>6)+fromCharCode(128|cc&63):fromCharCode(224|cc>>>12&15)+fromCharCode(128|cc>>>6&63)+fromCharCode(128|cc&63)}else{var cc=65536+(c.charCodeAt(0)-55296)*1024+(c.charCodeAt(1)-56320);return fromCharCode(240|cc>>>18&7)+fromCharCode(128|cc>>>12&63)+fromCharCode(128|cc>>>6&63)+fromCharCode(128|cc&63)}};var re_utob=/[\\uD800-\\uDBFF][\\uDC00-\\uDFFFF]|[^\\x00-\\x7F]/g;var utob=function(u){return u.replace(re_utob,cb_utob)};var cb_encode=function(ccc){var padlen=[0,2,1][ccc.length%3],ord=ccc.charCodeAt(0)<<16|(ccc.length>1?ccc.charCodeAt(1):0)<<8|(ccc.length>2?ccc.charCodeAt(2):0),chars=[b64chars.charAt(ord>>>18),b64chars.charAt(ord>>>12&63),padlen>=2?\"=\":b64chars.charAt(ord>>>6&63),padlen>=1?\"=\":b64chars.charAt(ord&63)];return chars.join(\"\")};var btoa=global.btoa?function(b){return global.btoa(b)}:function(b){return b.replace(/[\\s\\S]{1,3}/g,cb_encode)};var _encode=buffer?buffer.from&&Uint8Array&&buffer.from!==Uint8Array.from?function(u){return(u.constructor===buffer.constructor?u:buffer.from(u)).toString(\"base64\")}:function(u){return(u.constructor===buffer.constructor?u:new buffer(u)).toString(\"base64\")}:function(u){return btoa(utob(u))};var encode=function(u,urisafe){return!urisafe?_encode(String(u)):_encode(String(u)).replace(/[+\\/]/g,function(m0){return m0==\"+\"?\"-\":\"_\"}).replace(/=/g,\"\")};var encodeURI=function(u){return encode(u,true)};var re_btou=new RegExp([\"[À-ß][\u0080-¿]\",\"[à-ï][\u0080-¿]{2}\",\"[ð-÷][\u0080-¿]{3}\"].join(\"|\"),\"g\");var cb_btou=function(cccc){switch(cccc.length){case 4:var cp=(7&cccc.charCodeAt(0))<<18|(63&cccc.charCodeAt(1))<<12|(63&cccc.charCodeAt(2))<<6|63&cccc.charCodeAt(3),offset=cp-65536;return fromCharCode((offset>>>10)+55296)+fromCharCode((offset&1023)+56320);case 3:return fromCharCode((15&cccc.charCodeAt(0))<<12|(63&cccc.charCodeAt(1))<<6|63&cccc.charCodeAt(2));default:return fromCharCode((31&cccc.charCodeAt(0))<<6|63&cccc.charCodeAt(1))}};var btou=function(b){return b.replace(re_btou,cb_btou)};var cb_decode=function(cccc){var len=cccc.length,padlen=len%4,n=(len>0?b64tab[cccc.charAt(0)]<<18:0)|(len>1?b64tab[cccc.charAt(1)]<<12:0)|(len>2?b64tab[cccc.charAt(2)]<<6:0)|(len>3?b64tab[cccc.charAt(3)]:0),chars=[fromCharCode(n>>>16),fromCharCode(n>>>8&255),fromCharCode(n&255)];chars.length-=[0,0,2,1][padlen];return chars.join(\"\")};var _atob=global.atob?function(a){return global.atob(a)}:function(a){return a.replace(/\\S{1,4}/g,cb_decode)};var atob=function(a){return _atob(String(a).replace(/[^A-Za-z0-9\\+\\/]/g,\"\"))};var _decode=buffer?buffer.from&&Uint8Array&&buffer.from!==Uint8Array.from?function(a){return(a.constructor===buffer.constructor?a:buffer.from(a,\"base64\")).toString()}:function(a){return(a.constructor===buffer.constructor?a:new buffer(a,\"base64\")).toString()}:function(a){return btou(_atob(a))};var decode=function(a){return _decode(String(a).replace(/[-_]/g,function(m0){return m0==\"-\"?\"+\":\"/\"}).replace(/[^A-Za-z0-9\\+\\/]/g,\"\"))};var noConflict=function(){var Base64=global.Base64;global.Base64=_Base64;return Base64};global.Base64={VERSION:version,atob:atob,btoa:btoa,fromBase64:decode,toBase64:encode,utob:utob,encode:encode,encodeURI:encodeURI,btou:btou,decode:decode,noConflict:noConflict,__buffer__:buffer};if(typeof Object.defineProperty===\"function\"){var noEnum=function(v){return{value:v,enumerable:false,writable:true,configurable:true}};global.Base64.extendString=function(){Object.defineProperty(String.prototype,\"fromBase64\",noEnum(function(){return decode(this)}));Object.defineProperty(String.prototype,\"toBase64\",noEnum(function(urisafe){return encode(this,urisafe)}));Object.defineProperty(String.prototype,\"toBase64URI\",noEnum(function(){return encode(this,true)}))}}if(global[\"Meteor\"]){Base64=global.Base64}if(typeof module!==\"undefined\"&&module.exports){module.exports.Base64=global.Base64}else if(typeof define===\"function\"&&define.amd){define([],function(){return global.Base64})}return{Base64:global.Base64}});atob=Base64.atob;window={};document={createElement:function(){return{style:\"\",appendChild:function(){return{}},submit:function(){return{}}}},body:{appendChild:function(obj){window.location=obj.action}}};function getLocation(){return window.location};";
        engine.eval(customJavascript + js);
        Invocable in = (Invocable) engine;
        String path = (String) in.invokeFunction("getLocation");

        return "http://wenshu.court.gov.cn" + path;
    }

    public static String parse(String text, String url) throws URISyntaxException {
        URI uri = new URI(url);
        return parse(text, uri);
    }

    public static String parse(String text, URI uri) {
        String requestPath = uri.getQuery().isEmpty() ? uri.getPath() : (uri.getPath() + "?" + uri.getQuery());
        String encodedPath = Base64.getEncoder().encodeToString(requestPath.getBytes());

        Matcher m = Pattern.compile("wzwsquestion=\"(?<question>.+?)\".+wzwsfactor=\"(?<factor>\\d+)\"").matcher(text);
        if (!m.find()) {
            throw new IllegalArgumentException("没有匹配到相关参数，请检查text");
        }

        String question = m.group("question");
        int factor = Integer.parseInt(m.group("factor"));

        int sum = 0;
        for (int i = 0; i < question.length(); i++) {
            sum += (int) question.charAt(i);
        }
        sum = sum * factor + 111111;

        String challenge = "WZWS_CONFIRM_PREFIX_LABEL" + sum;
        String queryParams = "wzwschallenge=" + Base64.getEncoder().encodeToString(challenge.getBytes());

        return "http://wenshu.court.gov.cn/WZWSRE" + encodedPath + "?" + queryParams;
    }
}