import top.vchar.db.mongo.morphia.MongoConnectUtil;
import top.vchar.db.mongo.morphia.MongoService;
import top.vchar.db.mongo.morphia.bean.MongoResBean;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p> 添加测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/22 15:45
 */
public class InsertTest {

    //添加
    @Test
    public void insert(){
        UserInfo user = new UserInfo();
        user.setAccount("root1");
        try {
            String id = MongoService.insert(user, UserInfo.class, MongoConnectUtil.getInstance().getDatastore());
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //批量添加
    @Test
    public void insertM(){
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
            System.out.println(res.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
