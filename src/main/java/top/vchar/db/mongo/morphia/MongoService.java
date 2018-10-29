package top.vchar.db.mongo.morphia;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.mongodb.morphia.query.*;
import top.vchar.db.mongo.morphia.bean.MongoResBean;
import top.vchar.db.mongo.morphia.bean.MongoResPageBean;
import top.vchar.db.util.StringUtil;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.logging.Logger;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p> mongodb 常用业务方法; 使用此类的方法，请务必进行异常捕获，以免影响业务处理</p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 17:57
 */
public class MongoService {

    private static Logger logger = MorphiaLoggerFactory.get(MongoService.class);

    private MongoService() {

    }

    //--------------------------插入 start---------------------------------

    /**
     * 插入单条数据
     * @param pojo 插入的数据
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 插入成功返回_id; 否则插入失败或抛出异常
     */
    public static <E> String insert(E pojo, Class<E> classz, Datastore datastore){
        //创建索引
        datastore.ensureIndexes(classz);
        //创建集合
        datastore.ensureCaps();
        Key<E> key = datastore.save(pojo);
        return (String) key.getId();
    }

    /**
     * 批量插入数据
     * @param list 插入的数据列表
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 插入成功在msg中会返回插入数据的_id; 否则插入失败在msg中会返回提醒信息
     */
    public static <E> MongoResBean insertM(List<E> list, Class<E> classz, Datastore datastore){
        //创建索引
        datastore.ensureIndexes(classz);
        //创建集合
        datastore.ensureCaps();
        Iterable<Key<E>> keyIterable = datastore.save(list);
        Iterator<Key<E>> iterator = keyIterable.iterator();
        List<String> ids = new ArrayList<String>();
        while(iterator.hasNext()){
            Key<E> key = iterator.next();
            ids.add((String) key.getId());
        }
        MongoResBean resBean = new MongoResBean();
        resBean.setCount(ids.size());
        if(ids.size()!=list.size()){
            resBean.setMsg("insert fail. because "+list.size()+" records should be inserted, but only "+ids.size()+" records be inserted.");
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setCount(list.size());
        }else{
            resBean.setMsg(JSON.toJSONString(ids));
        }
        return resBean;
    }
    //--------------------------插入 end---------------------------------

    //--------------------------删除 start---------------------------------

    /**
     * 根据_id删除数据
     * @param id _id
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回删除结果
     */
    public static <E> MongoResBean delById(String id, Class<E> classz, Datastore datastore){
        MongoResBean resBean = new MongoResBean();
        if(null==id || id.length()==0 || id.trim().length()==0){
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setCount(0);
            resBean.setMsg("the 'id' can not empty.");
        }else{
            Query<E> query = datastore.createQuery(classz);
            query.criteria("_id").equal(new ObjectId(id));
            WriteResult writeResult = datastore.delete(query);
            resBean.setCount(writeResult.getN());
            if(writeResult.getN()<=0){
                resBean.setStatus(false);
            }
            logger.debug(JSONObject.toJSONString(writeResult));
            resBean.setMsg(JSONObject.toJSONString(writeResult));
        }
        return resBean;
    }

    /**
     * 根据Query条件删除数据
     * @param query Query条件
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回删除结果
     */
    public static <E> MongoResBean delByQuery(Query<E> query, Datastore datastore){
        WriteResult writeResult = datastore.delete(query);
        MongoResBean resBean = new MongoResBean();
        resBean.setCount(writeResult.getN());
        resBean.setMsg(JSONObject.toJSONString(writeResult));
        logger.debug(JSONObject.toJSONString(writeResult));
        return resBean;
    }

    /**
     * 根据jsonString条件删除数据
     * @param sql 删除条件
     * @param allowEmpty 是否允许空
     * @param classz Class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回删除结果
     */
    public static <E> MongoResBean delByJsonSql(String sql, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery((Map) JSON.parse(sql), query, allowEmpty);
        WriteResult writeResult = datastore.delete(query);

        MongoResBean resBean = new MongoResBean();
        resBean.setCount(writeResult.getN());
        resBean.setMsg(JSONObject.toJSONString(writeResult));
        logger.debug(JSONObject.toJSONString(writeResult));
        return resBean;
    }

    //--------------------------删除 end---------------------------------

    //--------------------------查询 start-------------------------------
    /**
     * 根据 _id 主键查询
     * @param id _id
     * @param classz  映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> E queryById(String id, Class<E> classz, Datastore datastore){
        Query<E> query = datastore.createQuery(classz);
        query.field("_id").equal(new ObjectId(id));
        List<E> list = query.asList();
        if(null!=list && list.size()>0){
            if(list.size()!=1){
                logger.warning("When use '_id='"+id+"' find data, it's should return 1, but return "+list.size()+".");
            }
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据Query条件查询，返回单条数据
     * @param query 组装好的query
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> E queryOne(Query<E> query){
        List<E> list = queryAll(query);
        if(null!=list && list.size()>0){
            if(list.size()>1)
                logger.info("There are "+list.size()+" qualified data");
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据Query条件查询，返回所有数据
     * @param query 组装好的query
     * @return 返回查询结果
     */
    public static <E> List<E> queryAll(Query<E> query){
        List<E> list = query.asList();
        if(null!=list && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * 根据query条件查询, 返回分页数据
     * @param query 组装好的query
     * @param pageIndex 页码
     * @param pageSize 每页条数
     * @return 返回查询结果
     */
    public static <E> MongoResPageBean queryPage(Query<E> query, int pageIndex, int pageSize){
        return queryPage(query, new MongoResPageBean(pageIndex, pageSize));
    }


    /**
     * 根据单个条件查询，返回单条数据
     * @param key 数据库中的字段名
     * @param value 对比的值
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> E queryOneByKeyValue(String key, Object value, Class<E> classz, Datastore datastore){
        List<E> list = queryAllByKeyValue(key, value, classz, datastore);
        if(null!=list && list.size()>0){
            if(list.size()>1)
                logger.info("There are "+list.size()+" qualified data");
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据单个条件查询，返回所有数据
     * @param key 数据库中的字段名
     * @param value 对比的值
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> List<E> queryAllByKeyValue(String key, Object value, Class<E> classz, Datastore datastore){
        if(StringUtil.isEmpty(key)){
            logger.warning("The 'key' can not be empty.");
            return null;
        }
        Query<E> query = datastore.createQuery(classz);
        query.field(key).equal(value);
        List<E> list = query.asList();
        if(null!=list && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * 根据单个条件查询，返回分页数据
     * @param key 数据库中的字段名
     * @param value 等于的值
     * @param pageIndex 页数
     * @param pageSize 每页条数
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    public static <E> MongoResPageBean queryPageByKeyValue(String key, Object value, int pageIndex, int pageSize, Class<E> classz, Datastore datastore){

        MongoResPageBean resPageBean = new MongoResPageBean(pageIndex, pageSize);
        if(StringUtil.isEmpty(key)){
            logger.warning("The 'key' can not be empty.");
            resPageBean.setStatus(false);
            resPageBean.setCode(Constant.ERROR_CODE);
            resPageBean.setMsg("The 'key' can not be empty.");
            resPageBean.setTotalNum(0);
        }else if(pageSize<1){
            logger.warning("The 'pageSize' can not be zero");
            resPageBean.setStatus(false);
            resPageBean.setCode(Constant.ERROR_CODE);
            resPageBean.setMsg("The 'pageSize' can not be zero");
            resPageBean.setTotalNum(0);
        }else {
            Query<E> query = datastore.createQuery(classz);
            query.field(key).equal(value);//查询条件
            queryPage(query, resPageBean);
        }
        return resPageBean;
    }


    /**
     * 根据Map条件查询，返回单条数据
     * @param paramMap 条件Map
     * @param allowEmpty 是否允许null的数据
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> E queryOneByMap(Map<String, Object> paramMap, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        List<E> list = queryAllByMap(paramMap, allowEmpty, classz, datastore);
        if(null!=list && list.size()>0){
            if(list.size()>1)
                logger.info("There are "+list.size()+" qualified data");
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据Map条件查询，返回所有数据
     * @param paramMap 条件Map
     * @param allowEmpty 是否允许null的数据
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> List<E> queryAllByMap(Map<String, Object> paramMap, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery(paramMap, query, allowEmpty);//组装sql
        List<E> list = query.asList();
        if(null!=list && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * 根据Map条件查询，返回分页数据
     * @param paramMap 条件Map
     * @param pageIndex 页数
     * @param pageSize 每页条数
     * @param allowEmpty 是否允许null的数据
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> MongoResPageBean queryPageByMap(Map<String, Object> paramMap, int pageIndex, int pageSize, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery(paramMap, query, allowEmpty);//组装sql
        return queryPage(query, new MongoResPageBean(pageIndex, pageSize));
    }


    /**
     * 根据json格式条件查询, 返回单条数据
     * 根据条件（json格式）条件查询，返回分页数据
     * @param param 条件，json格式
     * @param classz 映射类Class
     * @return 查询成功返回数据，否则返回null
     */
    public static <E> E queryOneByJsonStr(String param, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        List<E> list = queryAllByJsonStr(param, allowEmpty, classz, datastore);
        if(null!=list && list.size()>0){
            if(list.size()>1)
                logger.info("There are "+list.size()+" qualified data");
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据json格式条件查询, 返回所有数据
     * @param param 条件，json格式
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    public static <E> List<E> queryAllByJsonStr(String param, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery((Map) JSON.parse(param), query, allowEmpty);
        List<E> list = query.asList();
        if(null!=list && list.size()>0){
            return list;
        }
        return null;
    }

    /**
     * 根据json格式条件查询, 返回分页数据
     * @param param 条件，json格式
     * @param pageIndex 页码
     * @param pageSize 每页条数
     * @param classz 映射类Class
     * @return 返回查询结果
     */
    public static <E> MongoResPageBean queryPageByJsonStr(String param, boolean allowEmpty, int pageIndex, int pageSize, Class<E> classz, Datastore datastore){
        Query<E> query = datastore.createQuery(classz);
        makeQuery((Map) JSON.parse(param), query, allowEmpty);
        return queryPage(query, new MongoResPageBean(pageIndex, pageSize));
    }

    /**
     * 执行分页查询
     * @param query 组装好的query
     * @param resPageBean 分页信息
     * @return 返回查询结果
     */
    private static <E> MongoResPageBean queryPage(Query<E> query, MongoResPageBean resPageBean){
        if(resPageBean.getPageSize()<1) {
            logger.warning("The 'pageSize' can not be 0.");
            resPageBean.setStatus(false);
            resPageBean.setCode(Constant.ERROR_CODE);
            resPageBean.setMsg("The 'pageSize' can not be 0.");
            resPageBean.setTotalNum(0);
        }else if(resPageBean.getPageIndex()<1) {
            logger.warning("The 'pageIndex' must be greater than 0");
            resPageBean.setStatus(false);
            resPageBean.setCode(Constant.ERROR_CODE);
            resPageBean.setMsg("The 'pageIndex' must be greater than 0");
            resPageBean.setTotalNum(0);
        }else {
            long totalCount = query.count();//获取符合条件的总条数
            resPageBean.setTotalNum(totalCount);

            if(resPageBean.getPageIndex()>resPageBean.getTotalPage()){
                String msg = "There are only "+resPageBean.getTotalPage()+" pages, but the 'pageIndex' is "+resPageBean.getPageIndex();
                logger.warning(msg);
                resPageBean.setStatus(false);
                resPageBean.setCode(Constant.ERROR_CODE);
                resPageBean.setMsg(msg);
            }else{
                FindOptions findOptions = new FindOptions();
                findOptions.skip((resPageBean.getPageIndex()-1)*resPageBean.getPageSize());//起始位置
                findOptions.limit(resPageBean.getPageSize());//每页条数
                List<E> list = query.asList(findOptions);
                if(null!=list && list.size()>0){
                    resPageBean.setData(list);
                    resPageBean.setCount(list.size());
                }
            }
        }
        return resPageBean;
    }

    /**
     * 查询符合条件的总条数
     * @param query Query
     * @return 返回结果
     */
    public static <E> long queryCount(Query<E> query){
        return query.count();
    }

    /**
     * 根据Map条件查询符合条件的总条数
     * @param param 条件
     * @param allowEmpty 是否允许空字符
     * @param classz Class
     * @param datastore Datastore
     * @return 返回结果
     */
    public static <E> long queryCountByJsonStr(String param, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery((Map) JSON.parse(param), query, allowEmpty);
        return queryCount(query);
    }

    /**
     * 根据Map条件查询符合条件的总条数
     * @param paramMap 条件
     * @param allowEmpty 是否允许空字符
     * @param classz Class
     * @param datastore Datastore
     * @return 返回结果
     */
    public static <E> long queryCountByMap(Map<String, Object> paramMap, boolean allowEmpty, Class<E> classz, Datastore datastore) {
        Query<E> query = datastore.createQuery(classz);
        makeQuery(paramMap, query, allowEmpty);//组装sql
        return queryCount(query);
    }


    /**
     * 根据json字符串组装查询条件
     * @param sqlMap 查询条件
     * @param query  Query
     * @param allowEmpty 是否允许空
     * @param <E> E
     */
    private static <E> void makeQuery(Map sqlMap, Query<E> query, boolean allowEmpty) {
        if(!sqlMap.isEmpty()){
            if(null!=sqlMap.get("id") || null!=sqlMap.get("_id")){
                String id = (String) sqlMap.get("id");
                if(StringUtil.isEmpty(id)){
                    id = (String) sqlMap.get("_id");
                }
                if(StringUtil.isNotEmpty(id)){
                    query.field("_id").equal(new ObjectId(id));
                    return;
                }
            }
            for (Object keyObj : sqlMap.keySet()){
                if(null!=keyObj){
                    String key = (String) keyObj;
                    //是否查询为空的
                    if(null==sqlMap.get(key) && !allowEmpty){
                        continue;
                    }
                    if(sqlMap.get(key) instanceof String
                            && sqlMap.get(key).toString().trim().length()>0 && !allowEmpty){
                        continue;
                    }

                    String prfitStr = "", relKey = key;
                    if(key.length()>10){
                        prfitStr = key.substring(0, 10);
                        relKey = key.substring(10);
                    }
                    switch (prfitStr){
                        //多个传入值任意一个符合条件
                        case MongoSqlTag.LIST_OR_TAG:
                            String value = (String) sqlMap.get(key);
                            if(null!=value && value.length()>0){
                                String[] values = value.split(",");
                                Criteria[] criteria = new Criteria[values.length];
                                for(int i=0; i<values.length; i++){
                                    criteria[i] = query.criteria(relKey).equal(values[i]);
                                }
                                query.or(criteria);
                            }
                            break;
                        //多个传入值都符合条件
                        case MongoSqlTag.LIST_AND_TAG:
                            value = (String) sqlMap.get(key);
                            if(null!=value && value.length()>0){
                                String[] values = value.split(",");
                                Criteria[] criteria = new Criteria[values.length];
                                for(int i=0; i<values.length; i++){
                                    criteria[i] = query.criteria(relKey).equal(values[i]);
                                }
                                query.and(criteria);
                            }
                            break;
                        default:
                            //普通的 key --- value
                            query.field((String) key).equal(sqlMap.get(key));
                    }
                }
            }
        }
    }

    //--------------------------查询 end-------------------------------

    //--------------------------更新 start-----------------------------

    /**
     * 执行更新操作
     * @param query 更新条件Query
     * @param updateOperations 更新信息UpdateOperations
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回更新结果
     */
    public static <E> MongoResBean update(Query<E> query, UpdateOperations<E> updateOperations, Datastore datastore) {
        MongoResBean resBean = new MongoResBean();
        UpdateResults updateResults = datastore.update(query, updateOperations);
        logger.info(updateResults.toString());
        int count = updateResults.getUpdatedCount()+updateResults.getInsertedCount();
        resBean.setCount(count);
        if(count<1){
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setMsg("There are no qualified data.");
        }
        return resBean;
    }

    /**
     * 根据 _id 更新数据
     * @param id _id
     * @param updateOperations 更新信息UpdateOperations
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回更新结果
     */
    public static <E> MongoResBean updateById(String id, UpdateOperations<E> updateOperations, Class<E> classz, Datastore datastore) {
        if(StringUtil.isEmpty(id)){
            MongoResBean resBean = new MongoResBean();
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setMsg("The 'id' can not be empty.");
            resBean.setCount(0);
            return resBean;
        } else {
            Query<E> query = datastore.createQuery(classz);
            query.field("_id").equal(new ObjectId(id));
            return update(query, updateOperations, datastore);
        }
    }

    /**
     * 根据 _id 更新数据
     * @param id _id
     * @param updateDateMap 更新信息map
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回更新结果
     */
    public static <E> MongoResBean updateById(String id, Map<String, Object> updateDateMap, Class<E> classz, Datastore datastore) {
        MongoResBean resBean = new MongoResBean();
        if(StringUtil.isEmpty(id)){
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setMsg("The 'id' can not be empty.");
            resBean.setCount(0);
        }else if(null==updateDateMap || updateDateMap.isEmpty()){
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setMsg("Don't find update info, please set update info.");
            resBean.setCount(0);
        }else {
            UpdateOperations<E> updateOperations = datastore.createUpdateOperations(classz);
            for(String key:updateDateMap.keySet()) {
                updateOperations.set(key, updateDateMap.get(key));
            }
            resBean = updateById(id, updateOperations, classz, datastore);
        }
        return resBean;
    }

    /**
     * 根据 map查询条件更新数据
     * @param queryMap 查询条件
     * @param updateDateMap 更新信息map
     * @param classz 对象的class
     * @param datastore 数据库连接Datastore
     * @param <E> E
     * @return 返回更新结果
     */
    public static <E> MongoResBean updateByMap( Map<String, Object> queryMap, Map<String, Object> updateDateMap, Class<E> classz, Datastore datastore) {
        MongoResBean resBean = new MongoResBean();
        if(null==updateDateMap || updateDateMap.isEmpty()){
            resBean.setStatus(false);
            resBean.setCode(Constant.ERROR_CODE);
            resBean.setMsg("Don't find update info, please set update info.");
            resBean.setCount(0);
        }else {
            Query<E> query = datastore.createQuery(classz);
            makeQuery(queryMap, query, true);
            UpdateOperations<E> updateOperations = datastore.createUpdateOperations(classz);
            for(String key:updateDateMap.keySet()) {
                updateOperations.set(key, updateDateMap.get(key));
            }
            resBean = update(query, updateOperations, datastore);
        }
        return resBean;
    }


    //--------------------------更新 end-------------------------------
}
