# morphia-mongo-tool
Morphia 框架操作Mongo数据库的相关方法二次封装, 旨在简化操作
## 一、数据配置文件
在resources文件夹下创建一个名为```mongo.properties```配置文件，
在里面配置数据库相关配置信息。

### 配置要求
* 配置的key命名规则： ```关键词 + . + 你设置的key```
* 关键词只能是 ***DB*** 或 ***URI***开头；DB表示数据库名，URI表示数据库连接地址
* ***DEFAULT_DB_KEY*** 是配置默认使用的数据库，配置的值为你设置的key，非必须配置，当没有配置时，会自动取其中一个。

### 举个栗子
只会使用一个数据库的配置:

    #数据库连接地址
    URI.KEY = mongodb://demo:demo123456@127.0.0.1:27017/DEMO
    DB.KEY = demo
    #默认使用的数据库；非必须配置，当没有配置时，会自动取其中一个
    DEFAULT_DB_KEY = KEY

会动态使用多个数据库的配置：

    #数据库连接地址
    URI.KEY1 = mongodb://demo:demo123456@127.0.0.1:27017/DEMO1
    DB.KEY1 = DEMO1
    URI.KEY2 = mongodb://demo:demo123456@127.0.0.1:27017/DEMO2
    DB.KEY2 = DEMO2
    URI.KEY3 = mongodb://demo:demo123456@127.0.0.1:27017/DEMO3
    DB.KEY3 = demo
    #默认使用的数据库；非必须配置，当没有配置时，会自动取其中一个
    DEFAULT_DB_KEY = KEY1
    
## 二、使用说明




