import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import top.vchar.db.mongo.morphia.MongoConnectUtil;
import top.vchar.db.mongo.morphia.MongoService;
import top.vchar.db.mongo.morphia.bean.MongoResBean;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 更新测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/24 11:11
 */
public class UpdateTest {

    //更新
    @Test
    public void update(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.field("account").equal("root");
            UpdateOperations<UserInfo> updateOperations = datastore.createUpdateOperations(UserInfo.class);
            updateOperations.set("age", 100);
            MongoResBean resBean = MongoService.update(query, updateOperations, datastore);
            System.out.println(resBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新
    @Test
    public void updateByIdOptions(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            String id = "5bcec9d05be07628a8ddcc78";
            UpdateOperations<UserInfo> updateOperations = datastore.createUpdateOperations(UserInfo.class);
            updateOperations.set("account", "root1");
            MongoResBean resBean = MongoService.updateById(id, updateOperations, UserInfo.class, datastore);
            System.out.println(resBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新
    @Test
    public void updateByIdMap(){
        try {
            String id = "5bcec9d05be07628a8ddcc78";
            Map<String, Object> updateDateMap = new HashMap<>();
            updateDateMap.put("account", "root");
            MongoResBean resBean = MongoService.updateById(id, updateDateMap, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
            System.out.println(resBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新
    @Test
    public void updateByMap(){
        try {
            Map<String, Object> queryMap = new HashMap<>();
            queryMap.put("_id", "5bcec9d05be07628a8ddcc78");
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("account", "r");
            updateMap.put("age", 1000);
            MongoResBean resBean = MongoService.updateByMap(queryMap, updateMap, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
            System.out.println(resBean.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
