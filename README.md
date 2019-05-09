## 裁判文书相关解析/解密工具 for Java

我不是专业的java开发，只是感兴趣就用java实现了一遍，所以代码风格可能也许看起来有点怪？

有简单的测试用例，在src/test下，不过我用IDEA的maven test运行测试会报错，但是手动运行却没问题  
这个我不知道是我哪里操作不对，这个日后再研究了或者有大佬知道的话也可以指点一波～

### 环境
1. java 1.8

### package和用法
用法参考测试用例

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

### 其他
就算有bug，我也不维护的了。就仅供参考吧～
