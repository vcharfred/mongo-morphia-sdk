package top.vchar.utils.mongo;

/**
 * <p> mongo常用业务方法接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:58
 */
public class MongodbServiceImpl implements MongodbService{
    @Override
    public <E> E queryById(String id, Class<E> classz) {
        return null;
    }

    @Override
    public <E> E queryOneByKeyValue(String key, Object value, Class<E> classz) {
        return null;
    }

    @Override
    public <E> MongoResBean queryManyByKeyValue(String key, Object value, Class<E> classz) {
        return null;
    }

    @Override
    public <E> E queryOneByKeyValue(String param, Class<E> classz) {
        return null;
    }
}
