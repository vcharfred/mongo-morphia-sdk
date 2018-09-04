package top.vchar.utils.mongo;

import java.io.Serializable;

/**
 * <p> mongo返回结果bean </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/9/4 18:12
 */
public class MongoResBean implements Serializable{

    //成功 code
    private final static String SUCCESS_CODE = "DB2000";
    //业务处理失败 code
    private final static String ERROR_CODE = "DB5000";
    //发生异常 code
    private final static String ERROR_EXCEPTION_CODE = "DB5001";

    //true为成功，false为失败
    private boolean status = true;
    //提示代码
    private String code = SUCCESS_CODE;
    //提示信息
    private String msg;
    //数据
    private Object data;

    //分页 当前页数
    private int pageIndex = 1;
    //分页 每页条数
    private int pageSize = 10;
    //分页 总条数
    private int totalNum = 0;
    //分页 总页数
    private int totalPage = 0;


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

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
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

    public int getTotalPage() {
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
                ", totalNum=" + totalNum +
                ", totalPage=" + totalPage +
                '}';
    }
}
