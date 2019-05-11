## 裁判文书相关解析/解密工具 for Java

我不是专业的java开发，只是感兴趣就用java实现了一遍，所以代码风格可能也许看起来有点怪？  
就算有bug，应该也不维护的了。就仅供参考吧～

### 环境
1. java 1.8

### Demo
看test/下的com.sgx.spider.wenshu.Demo类  
或参考测试用例

### package
```bash
.
├── docid                       # 文书ID相关的
│   ├── DocIdDecryptor.java     # 解密文书ID的
│   ├── RunEvalParser.java      # 解析RunEval的
│   └── ZlibUtil.java           # 解密设计的工具类
│
├── vl5x                        # vl5x相关的
│   ├── Guid.java               # guid
│   ├── Number.java             # number
│   ├── Vjkl5.java              # cookie里的vjkl5
│   └── Vl5x.java               # 和vjkl5配对的vl5x
│
└── wzws                        # 网站卫士
    └── WZWSParser.java         # "请开启JavaScript并刷新该页"的解析
```

### 测试
使用maven进行测试  
```bash
mvn test
```

有个小问题: 我用IDEA里集成的maven运行test会报错，但是用终端运行`mvn test`却测试通过
```
java.util.zip.DataFormatException: invalid code lengths set
...
java.lang.NullPointerException
...
java.util.zip.DataFormatException: too many length or distance symbols
```  
这个我不知道是我IDEA哪里配置不对，有大佬知道的话希望可以指点一波～