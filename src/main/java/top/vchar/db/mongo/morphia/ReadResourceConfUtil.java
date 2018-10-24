package top.vchar.db.mongo.morphia;

import java.io.IOException;
import java.util.Properties;

/**
 * <p> Read properties file tools</p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/6 10:20
 */
public final class ReadResourceConfUtil {

    private ReadResourceConfUtil(){}

    /**
     * find value by key from properties file
     * @param propertyKey key
     * @param defaultValue default value
     * @param pathAndFileName properties file path
     * @return if it found return value, otherwise return default value
     */
    public static String readProp(String propertyKey, String defaultValue, String pathAndFileName) {
        Properties prop = new Properties();
        String propertyValue = "";
        try {
            prop.load(ReadResourceConfUtil.class.getResourceAsStream(pathAndFileName));
            propertyValue = prop.getProperty(propertyKey);
            if (propertyValue == null || propertyValue.equals("")) {
                propertyValue = defaultValue;
            }
            return propertyValue;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * load all data from properties file
     * @param pathAndFileName properties file path
     * @return if found return Properties
     */
    public static Properties readAllProp(String pathAndFileName) {
        Properties props = new Properties();
        try {
            props.load(ReadResourceConfUtil.class.getResourceAsStream(pathAndFileName));
        } catch (IOException e) {
            e.printStackTrace();
            props = null;
        }
        return props;
    }


}
