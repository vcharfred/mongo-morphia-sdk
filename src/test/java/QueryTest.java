import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.vchar.db.mongo.morphia.MongoConnectUtil;
import top.vchar.db.mongo.morphia.MongoService;
import top.vchar.db.mongo.morphia.bean.MongoResPageBean;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> 查询测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/23 15:18
 */
public class QueryTest {

    //根据id主键查询
    @Test
    public void queryById(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            String id = "5bcec9d05be07628a8ddcc78";
            UserInfo userInfo = MongoService.queryById(id, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据单条件查询，返回单条数据
    @Test
    public void queryOneByKeyValue(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            UserInfo userInfo = MongoService.queryOneByKeyValue("password", "123456", UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据单个条件查询，返回所有数据
    @Test
    public void queryAllByKeyValue(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            List<UserInfo> list = MongoService.queryAllByKeyValue("password", "123456", UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据单个条件查询，返回分页数据
    @Test
    public void queryPageByKeyValue(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            MongoResPageBean resPageBean = MongoService.queryPageByKeyValue("password", "123456"
                    , 0 , 2, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Query条件查询，返回单条数据
    @Test
    public void queryOne(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            UserInfo userInfo = MongoService.queryOne(query);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Query条件查询，返回所有数据
    @Test
    public void queryAll(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            List<UserInfo> list = MongoService.queryAll(query);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //根据query条件查询, 返回分页数据
    @Test
    public void queryPage(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("password").equal("123456");
            MongoResPageBean resPageBean = MongoService.queryPage(query, 2, 2);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    //根据条件（Map）条件查询，返回单条数据
    @Test
    public void queryOneByMap(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            UserInfo userInfo = MongoService.queryOneByMap(paramMap, false, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据条件（Map）条件查询，返回所有数据
    @Test
    public void queryAllByMap(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            List<UserInfo> list = MongoService.queryAllByMap(paramMap, false, UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据条件（Map）条件查询，返回分页数据
    @Test
    public void queryPageByMap(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("password", "123456");
            MongoResPageBean resPageBean = MongoService.queryPageByMap(paramMap, 2, 2,false, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据json格式条件查询, 返回单条数据
    @Test
    public void queryOneByJsonStr(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            UserInfo userInfo = MongoService.queryOneByJsonStr(paramJson.toJSONString(), false, UserInfo.class, datastore);
            System.out.println(JSONObject.toJSONString(userInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //根据json格式条件查询, 返回所有数据
    @Test
    public void queryAllByJsonStr(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            List<UserInfo> list = MongoService.queryAllByJsonStr(paramJson.toJSONString(),false, UserInfo.class, datastore);
            System.out.println(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //根据json格式条件查询, 返回分页数据
    @Test
    public void queryPageByJsonStr(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject paramJson = new JSONObject();
            paramJson.put("password", "123456");
            MongoResPageBean resPageBean = MongoService.queryPageByJsonStr(paramJson.toJSONString(), false,2, 2, UserInfo.class, datastore);
            System.out.println(resPageBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

