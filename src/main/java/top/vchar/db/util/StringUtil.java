package top.vchar.db.util;

/**
 * <p> 数据库相关操作的字符串工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/11 9:40
 */
public class StringUtil {

    /**
     * deal date key
     * @param key key
     * @return return array, and index 0 is key, index 1 is date format string
     */
    public static String[] dealDateStr(Object key){
        String dateKey = (String) key;
        int index = dateKey.indexOf("#");
        String[] arr = new String[2];
        if(index>0){
            arr[0] = dateKey.substring(index+1);
            arr[1] = dateKey.substring(0, index);
        }else{
            arr[0] = dateKey;
            arr[1] = null;
        }
        return arr;
    }

    public static boolean isEmpty(String str){
        if(null==str){
            return true;
        }
        return str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }


}
