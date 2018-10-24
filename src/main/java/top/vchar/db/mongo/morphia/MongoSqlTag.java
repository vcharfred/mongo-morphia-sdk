package top.vchar.db.mongo.morphia;

/**
 * <p> include some mongodb sql tags </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/30 17:21
 */
public final class MongoSqlTag {

    /**传入值 < 库中字段值*/
    public final static String GREATER_THAN_TAG = "#V#0<000#";

    /**传入值 <= 库中字段值*/
    public final static String GREATER_THAN_OR_EQ_TAG = "#V#0<=00#";

    /**传入值 > 库中字段值*/
    public final static String LESS_THAN_TAG = "#V#000>0#";

    /**传入值 >= 库中字段值*/
    public final static String LESS_THAN_OR_EQ_TAG = "#V#00>=0#";

    /**传入值 < 库中字段值 < 传入值*/
    public final static String GREATER_LESS_THAN_INTERVAL_TAG = "#V#L<0>R#";

    /**传入值 <= 库中字段值 < 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_L_TAG = "#V#L<=>0#";

    /**传入值 < 库中字段值 <= 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_R_TAG = "#V#0<=>R#";

    /**传入值 <= 库中字段值 <= 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_LR_TAG = "#V#L<=>R#";

    /**传入值 < 库中时间类型字段值*/
    public final static String GREATER_THAN_DATA_TAG = "#T#0<000#";

    /**传入值 <= 库中时间类型字段值*/
    public final static String GREATER_THAN_OR_EQ_DATA_TAG = "#T#0<=00#";

    /**传入值 > 库中时间类型字段值*/
    public final static String LESS_THAN_DATA_TAG = "#T#000>0#";

    /**传入值 >= 库中时间类型字段值*/
    public final static String LESS_THAN_OR_EQ_DATA_TAG = "#T#00>=0#";

    /**传入值 < 库中时间类型字段值 < 传入值*/
    public final static String GREATER_LESS_THAN_INTERVAL_DATA_TAG = "#T#L<0>R#";

    /**传入值 <= 库中时间类型字段值 < 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_L_DATA_TAG = "#T#L<=>0#";

    /**传入值 < 库中时间类型字段值 <= 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_R_DATA_TAG = "#T#0<=>R#";

    /**传入值 <= 库中时间类型字段值 <= 传入值*/
    public final static String GREATER_LESS_THAN_OR_EQ_INTERVAL_LR_DATA_TAG = "#T#L<=>R#";

    /**多个传入值任意一个符合条件*/
    public final static String LIST_OR_TAG = "#V#LIOR00#";
    /**多个传入值都符合条件*/
    public final static String LIST_AND_TAG = "#V#LIAND0#";
}
