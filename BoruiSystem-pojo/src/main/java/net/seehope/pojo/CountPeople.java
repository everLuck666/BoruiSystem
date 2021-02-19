package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`countPeople`")
public class CountPeople implements Serializable {
    @Column(name = "`time`")
    private String time;

    @Column(name = "`count`")
    private String count;

    private static final long serialVersionUID = 1L;

    /**
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count
     */
    public void setCount(String count) {
        this.count = count;
    }
}