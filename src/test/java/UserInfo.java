import com.alibaba.fastjson.annotation.JSONField;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.utils.IndexDirection;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2018/10/22 15:44
 */
@Entity(noClassnameStored = true, value = "user_info")
public class UserInfo implements Serializable {

    @Id
    @Property("_id")
    @JSONField(name = "id")
    private String id;

    @Property("account")
    @Indexed(value = IndexDirection.ASC, unique = true)
    private String account;

    private String password;

    private int age;

    @Property("create_date")
    @JSONField(name = "createDate")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                '}';
    }
}
