## 裁判文书相关解析/解密工具 for Java

## 2019.09.01文书网更新，java分支暂不可用，等待更新...


我不是专业的java开发，只是感兴趣就用java实现了一遍，仅供参考～

### 环境
1. java 1.8

### Demo
看test/下的com.sgx.spider.wenshu.Demo类  
或参考测试用例

### packages
```bash
.
├── docid                       # 文书ID相关
│   ├── DocIdDecryptor.java     # 解密文书ID
│   ├── RunEvalParser.java      # 解析RunEval
│   └── ZlibUtil.java           # 解密涉及的工具类
│
├── vl5x                        # vl5x相关
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
