package top.vchar.db.mongo.morphia.bean;

import com.alibaba.fastjson.JSONObject;
import top.vchar.db.mongo.morphia.Constant;

import java.io.Serializable;

/**
 * <p> mongo return result bean </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/30 14:58
 */
public class MongoResBean implements Serializable {

    //success is true，fail is false
    private boolean status = true;
    //remind code
    private String code = Constant.SUCCESS_CODE;
    //remind msg
    private String msg = "success";
    //opera success num
    private long count = 0;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("code", code);
        json.put("msg", msg);
        json.put("count", count);
        return json;
    }

    public String toJSONString(){
        return toJSON().toJSONString();
    }

    @Override
    public String toString() {
        return "MongoResBean{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                '}';
    }
}
