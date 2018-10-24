import com.alibaba.fastjson.JSONObject;
import top.vchar.db.mongo.morphia.MongoConnectUtil;
import top.vchar.db.mongo.morphia.MongoService;
import top.vchar.db.mongo.morphia.bean.MongoResBean;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * <p>  删除测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/22 15:46
 */
public class DelTest {

    //根据_id删除数据
    @Test
    public void delById(){
        String id = "5bb07b4b5be0761eecebcd13";
        try {
            MongoResBean resBean = MongoService.delById(id, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
            System.out.println(resBean.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据Query条件删除数据
    @Test
    public void delByQuery(){
        String id = "5bb07b4b5be0761eecebcd14";
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            Query<UserInfo> query = datastore.createQuery(UserInfo.class);
            query.criteria("account").equal("root2");
            MongoResBean resBean = MongoService.delByQuery(query, datastore);
            System.out.println(resBean.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delByJsonSql(){
        try {
            Datastore datastore = MongoConnectUtil.getInstance().getDatastore();
            JSONObject jsonSql = new JSONObject();
            jsonSql.put("account", "root");
            MongoResBean resBean = MongoService.delByJsonSql(jsonSql.toJSONString(), false, UserInfo.class, datastore);
            System.out.println(resBean.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
