package top.vchar.utils.mongo;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 * <p> mongo 常用业务方法接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:57
 */
public interface MongoService {

    /**
     * 根据 _id 主键查询
     * @param id _id
     * @param classz  映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryById(String id, Class<E> classz, Datastore datastore);

    /**
     * 根据单条件查询，返回单条数据
     * @param key 数据库中的字段名
     * @param value 对比的值
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryOneByKeyValue(String key, Object value, Class<E> classz, Datastore datastore);

    /**
     * 根据单条件查询，返回所有数据
     * @param key 数据库中的字段名
     * @param value 等于的值
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryManyByKeyValue(String key, Object value, Class<E> classz, Datastore datastore);

    /**
     * 根据条件查询单个
     * @param query 组装好的query
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> MongoResBean queryOne(Query<E> query, Class<E> classz, Datastore datastore);

    /**
     * 根据条件查询所有
     * @param query 组装好的query
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryAll(Query<E> query, Class<E> classz, Datastore datastore);

    /**
     * 根据条件分页查询查询
     * @param query 组装好的query
     * @param pageIndex 页码
     * @param pageSize 每页条数
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryPage(Query<E> query, int pageIndex, int pageSize, Class<E> classz, Datastore datastore);

    /**
     * 根据条件（json格式）查询单个
     * @param param 条件，json格式
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryOneByJsonStr(String param, Class<E> classz, Datastore datastore);

    /**
     * 根据条件（json格式）查询所有
     * @param param 条件，json格式
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryAllByJsonStr(String param, Class<E> classz, Datastore datastore);

    //根据条件查询（json字符串，需要解析的 [分页查询]）

    /**
     * 根据条件（json格式）查询分页
     * @param param 条件，json格式
     * @param pageIndex 页码
     * @param pageSize 每页条数
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryPageByJsonStr(String param, int pageIndex, int pageSize, Class<E> classz, Datastore datastore);


}
