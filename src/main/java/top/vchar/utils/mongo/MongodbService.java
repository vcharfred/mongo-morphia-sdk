package top.vchar.utils.mongo;

/**
 * <p> mongo 常用业务方法接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:57
 */
public interface MongodbService {

    /**
     * 根据 _id 主键查询
     * @param id _id
     * @param classz  映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryById(String id, Class<E> classz);

    /**
     * 根据单条件查询，返回单条数据
     * @param key 数据库中的字段名
     * @param value 对比的值
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryOneByKeyValue(String key, Object value, Class<E> classz);

    /**
     * 根据单条件查询，返回所有数据
     * @param key 数据库中的字段名
     * @param value 等于的值
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    <E> MongoResBean queryManyByKeyValue(String key, Object value, Class<E> classz);

    //根据条件查询（组装好的query）

    /**
     *
     * @param param 条件
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    <E> E queryOneByKeyValue(String param, Class<E> classz);

    //根据条件查询（组装好的query [分页查询]）

    //根据条件查询（json，需要解析的）
    //根据条件查询（json，需要解析的 [分页查询]）



}
