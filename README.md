## 裁判文书网 参数加解密工具库 for Python

**本项目并非长期维护的项目，如果代码无法正常运行，那就是不能用了  
另外请勿将本项目用于商业用途**

### Demo (Java示例请切换到java分支)
最新demo参考`new_demo.py`   
旧版文书网demo参考`old_demo.py`

### 环境
1. python3.5+
2. 安装requirements.txt

<details>
<summary>文书网变更历史</summary>
    
2019.12.20
    
    恢复了瑞数的cookie校验，并加入sojson

2019.12.12左右

    瑞数cookie再次下线...
    
2019.10.18

    恢复了瑞数的cookie校验
    
2019.09.14

    不需要相关的cookie也能访问

2019.09.07

    加上瑞数，主要参数为cookie中的 HM4hUBT0dDOn80S 和 HM4hUBT0dDOn80T 解决即可正常请求

2019.09.01

    文书网大更新，主要参数为请求参数中的ciphertext，以及响应中的des3加密数据
    相关参数已经通过python实现并开源

<details/>
