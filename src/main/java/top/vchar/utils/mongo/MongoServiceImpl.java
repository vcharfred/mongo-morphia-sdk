package top.vchar.utils.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * <p> mongo常用业务方法接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:58
 */
public class MongoServiceImpl implements MongoService{

    @Override
    public <E> E queryById(String id, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> E queryOneByKeyValue(String key, Object value, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryManyByKeyValue(String key, Object value, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryOne(Query<E> query, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryAll(Query<E> query, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryPage(Query<E> query, int pageIndex, int pageSize, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> E queryOneByJsonStr(String param, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryAllByJsonStr(String param, Class<E> classz, Datastore datastore) {
        return null;
    }

    @Override
    public <E> MongoResBean queryPageByJsonStr(String param, int pageIndex, int pageSize, Class<E> classz, Datastore datastore) {
        return null;
    }
}
