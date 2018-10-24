package top.vchar.db.mongo.morphia.bean;

import top.vchar.db.mongo.morphia.Constant;

import java.io.Serializable;

/**
 * <p> mongo return result page bean </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 18:12
 */
public class MongoResPageBean implements Serializable{

    //success is true，fail is false
    private boolean status = true;
    //remind code
    private String code = Constant.SUCCESS_CODE;
    //remind msg
    private String msg = "success";
    //data
    private Object data;

    //page: page number
    private int pageIndex = 1;
    //page: Number each page
    private int pageSize = 10;
    //page: return num
    private int count = 0;
    //page： total num
    private long totalNum = 0;
    //page total page num
    private long totalPage = 0;

    public MongoResPageBean(){

    }

    public MongoResPageBean(int pageIndex, int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
        //计算总页数
        if(this.totalNum<1 || pageSize==0){
            totalPage = 0;
        }else {
            totalPage = this.totalNum/pageSize;
            if(this.totalNum%pageSize!=0){
                totalPage++;
            }
        }
    }

    public long getTotalPage() {
        return totalPage;
    }

    @Override
    public String toString() {
        return "MongoResBean{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", count="+count+
                ", totalNum=" + totalNum +
                ", totalPage=" + totalPage +
                '}';
    }
}

