# 
## 一、简介和说明
这是一个对morphia操作mongodb的二次封装工具包，旨在简化某些操作；支持多个数据切换。

当前使用的相关配置和环境

    JDK版本：1.8、mongodb3.6及以上

maven依赖如下：

    <!--mongodb start-->
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver</artifactId>
        <version>3.6.4</version>
    </dependency>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>3.6.4</version>
    </dependency>
    <!--mongodb end-->
    <!--morphia start-->
    <dependency>
        <groupId>org.mongodb.morphia</groupId>
        <artifactId>morphia</artifactId>
        <version>1.3.2</version>
    </dependency>
    <!--morphia end-->
    <!--fastjson start-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.47</version>
    </dependency>
    <!--fastjson end-->

## 二、配置数据库
在resources文件夹下创建一个名为```mongo.properties```配置文件，
在里面配置数据库相关配置信息。

### 2.1 配置要求
* 配置的key命名规则： ```关键词 + . + 你设置的key```
* 关键词只能是 ***DB*** 或 ***URI*** 开头；DB表示数据库名，URI表示数据库连接地址
* ***DEFAULT_DB_KEY*** 是配置默认使用的数据库，配置的值为你设置的key，非必须配置，当没有配置时，会自动取其中一个。

### 2.2 举个栗子
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
    
## 三、工具方法使用说明
### 3.1 获取Datastore

所在类路径：```com.bxhh.db.mongo.morphia.MongoConnectUtil```

* 获取默认的连接

    	Datastore datastore = MongoConnectUtil.getInstance().getDatastore()   
* 获取指定的连接

	    Datastore datastore = MongoConnectUtil.getInstance().getDatastore("KEY2")   
 
### 3.2 插入数据
这里使用`UserInfo`为基础对象，对象内容如下：

	@Entity(value = "user_info" , noClassnameStored = true)
	public class UserInfo implements Serializable {
	
	    @Id
	    @Property("_id")
	    @JSONField(name = "id")
	    private String id;
	
	    @Property("account")
	    @Indexed(value = IndexDirection.ASC, unique = true)//创建索引，此字段必须唯一
	    private String account;
	
	    private String password;
	
	    private int age;
	
	    @Property("create_date")
	    @JSONField(name = "createDate")
	    private Date createDate;
		
		get和set方法略........
	}


插入单条数据

	UserInfo user = new UserInfo();
    user.setAccount("root1");
	try {
	    String id = MongoService.insert(user, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
	    System.out.println("插入成功，插入数据返回的_id为："+id);
	} catch (Exception e) {
	    e.printStackTrace();
	}
批量插入数据

	UserInfo user2 = new UserInfo();
    user2.setAccount("root2");
    user2.setCreateDate(new Date());
    user2.setPassword("123456");
    user2.setAge(200);
    UserInfo user3 = new UserInfo();
    user3.setAccount("root3");
    user3.setCreateDate(new Date());
    user3.setPassword("123456");
    user3.setAge(300);
    List<UserInfo> list = new ArrayList<>();
    list.add(user2);
    list.add(user3);

	try {
		MongoResBean res = MongoService.insertM(list, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
		System.out.println(res.getJSONString());
	} catch (Exception e) {
		e.printStackTrace();
	}
### 3.2 删除数据
根据_id删除数据

	String id = "5bb07b4b5be0761eecebcd14";
    try {
        MongoResBean resBean = MongoService.delById(id, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
        System.out.println(resBean.getJSONString());
    } catch (Exception e) {
        e.printStackTrace();
    }

根据组装好的Query条件删除数据

	try {
        Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
        Query<UserInfo> query = datastore.createQuery(UserInfo.class);
        query.criteria("account").equal("root2");
        MongoResBean resBean = MongoService.delByQuery(query, datastore);
        System.out.println(resBean.getJSONString());
    } catch (Exception e) {
        e.printStackTrace();
    }
根据json格式的查询条件删除数据

    try {
        Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
        JSONObject jsonSql = new JSONObject();
        jsonSql.put("account", "root");
        MongoResBean resBean = MongoService.delByJsonSql(jsonSql.toJSONString(), false, UserInfo.class, datastore);
        System.out.println(resBean.toJSONString());
    } catch (Exception e) {
        e.printStackTrace();
    }

>注意：若需要判断是否正真的删除成功，那么你需要判断返回结果中count不为0，因为有可能数据库中没有符合条件的数据。（status为true仅仅只是表示sql语句执行成功了） 
  
### 3.3 查询数据

* 根据 _id主键查询数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            String id = "5bcec9d05be07628a8ddcc78";
            UserInfo userInfo = MongoService.queryById(id, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }

* 根据Query条件查询，返回单条数据
    
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            UserInfo userInfo = MongoService.queryOne(query);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
* 根据Query条件查询，返回所有数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            List<UserInfo> list = MongoService.queryAll(query);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {

            e.printStackTrace();
        }
        
* 根据query条件查询, 返回分页数据
    
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            MongoResPageBean resPageBean = MongoService.queryPage(query, 2, 2);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    
>提示：工具类中分页页码 ‘pageIndex’ 由于在工具类中已经做了减一操作，因此必须大于0。

    
* 根据单个条件查询，返回单条数据 

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            UserInfo userInfo = MongoService.queryOneByKeyValue("password", "123456", UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }   

* 根据单个条件查询，返回所有数据
    
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            List<UserInfo> list = MongoService.queryAllByKeyValue("password", "123456", UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

* 根据单个条件查询，返回分页数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            MongoResPageBean resPageBean = MongoService.queryPageByKeyValue("password", "123456"
                    , 1, 2, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
           
* 根据条件Map条件查询，返回单条数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            UserInfo userInfo = MongoService.queryOneByMap(paramMap, false, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    
* 根据Map条件查询，返回所有数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            List<UserInfo> list = MongoService.queryAllByMap(paramMap, false, UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

* 根据Map条件查询，返回分页数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            MongoResPageBean resPageBean = MongoService.queryPageByMap(paramMap, 2, 2,false, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } 
               
* 根据json格式条件查询, 返回单条数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            UserInfo userInfo = MongoService.queryOneByJsonStr(paramJson.toJSONString(), false, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }

* 根据json格式条件查询, 返回所有数据

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            List<UserInfo> list = MongoService.queryAllByJsonStr(paramJson.toJSONString(),false, UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    
* 根据json格式条件查询, 返回分页数据  

        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            MongoResPageBean resPageBean = MongoService.queryPageByJsonStr(paramJson.toJSONString(), false,2, 2, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }       
 
##### 额外说明
在使用map或json格式的条件时，可以使用现有已支持的标签，相关标签在```top.vchar.db.mongo.morphia.MongoSqlTag```中；具体说明见该类的注释

 
## 四、其他说明

