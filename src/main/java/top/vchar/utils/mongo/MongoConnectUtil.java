package top.vchar.utils.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p> mongo配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:23
 */
public final class MongoConnectUtil {

    private static String DEFAULT_DB_KEY = "DEFAULT_DB_KEY_THIS_IS_DEFAULT_KEY_DO_NOT_USE_IT";

    private Morphia morphia;

    private MongoConnectUtil(){
    }

    /**
     * 枚举的单列模式
     */
    public static MongoConnectUtil getInstance(){
        return SingletonEnum.INSTANCE.getInstance();
    }
    private enum SingletonEnum{
        INSTANCE;
        private MongoConnectUtil mongoConnectUtil;
        SingletonEnum(){
            mongoConnectUtil = new MongoConnectUtil();
            mongoConnectUtil.morphia = new Morphia();
        }
        public MongoConnectUtil getInstance(){
            return mongoConnectUtil;
        }
    }

    //存储所有连接 URI.Key-value
    private static Map<String, MongoClient> clientMap = new HashMap<>();
    //存储配置文件 key-value
    private static Map<String, String> confMap = new HashMap<>();


    /**
     * get Datastore by key
     * @param key key
     * @return Datastore
     * @throws Exception
     */
    public Datastore getDatastore(String key) throws Exception {
        //1.get db name
        String dbName = confMap.get("DB."+key);
        if(isEmpty(dbName)){
            //load config
            synchronized(this){
                Thread.sleep(300);
                dbName = confMap.get("DB."+key);
                if(isEmpty(dbName)){
                    confMap = loadConf();
                }
            }
            dbName = confMap.get("DB."+key);
            if(isEmpty(dbName) || isEmpty(confMap.get("URI."+key))){
                throw new Exception("Don't find db name or connection uri, please check the 'mongo.properties' file info");
            }
        }
        //2.get MongoClient
        MongoClient client = clientMap.get("URI."+key);
        if(null==client){
            //create MongoClient
            synchronized(this){
                Thread.sleep(500);
                client = clientMap.get("URI."+key);
                if(null==client){
                    clientMap.put("URI."+key, updateClientMap(confMap.get("URI."+key)));
                }
            }
            client = clientMap.get("URI."+key);
            if(null==client){
                throw new Exception("Get MongoClient fail, maybe the 'mongo.properties' file has error");
            }
        }
        //create Datastore
        return morphia.createDatastore(client, dbName);
    }


    /**
     * get a default Datastore
     * @return Datastore
     */
    public Datastore getDatastore() throws Exception {
        return getDatastore(DEFAULT_DB_KEY);
    }


    /**
     * create MongoClient
     * @param uriStr mongo connection uri
     * @return MongoClient
     */
    private MongoClient updateClientMap(String uriStr){
        MongoClientURI uri = new MongoClientURI(uriStr);
        return new MongoClient(uri);
    }

    /**
     * Load mongo.properties info, and return confMap
     * @return return properties info
     * @throws Exception
     */
    private static Map<String, String> loadConf() throws Exception{
        Map<String, String> dbMap = new HashMap<>();
        //load properties file data
        Properties properties = ReadResourceConfUtil.readAllProp("/mongo.properties");
        if(null!=properties){
            //deal with properties data
            String defaultKey = properties.getProperty("DEFAULT_DB_KEY");
            properties.remove("DEFAULT_DB_KEY");
            Set<Map.Entry<Object, Object>> keys = properties.entrySet();
            for(Map.Entry<Object, Object> entry:keys){
                if(isEmpty(entry.getKey()) || isEmpty(entry.getValue())){
                    continue;
                }
                dbMap.put((String) entry.getKey(), (String) entry.getValue());
                //check default db value
                if(isEmpty(defaultKey)){
                    defaultKey = (String) entry.getKey();
                    if(defaultKey.contains(".")){
                        defaultKey = defaultKey.substring(defaultKey.indexOf("."));
                    }
                }
            }
            //add default db info
            if(!isEmpty(defaultKey) && !dbMap.isEmpty()){
                dbMap.put("URI."+DEFAULT_DB_KEY, dbMap.get("URI."+defaultKey));
                dbMap.put("DB."+DEFAULT_DB_KEY, dbMap.get("DB."+defaultKey));
            }

        }else{
            throw new Exception("[ERROR]-----Load mongo.properties fail.");
        }
        return dbMap;
    }

    /**
     * check the value is null or ""
     * @param obj check data value
     * @return if is null return true. if it's String and the length is 0 return false.
     *         otherwise return true
     */
    private static boolean isEmpty(Object obj) {
        return null == obj || obj instanceof String && ((String) obj).length() == 0;
    }

}
