package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`video`")
public class Video implements Serializable {
    @Column(name = "`videoName`")
    private String videoName;

    @Column(name = "`path`")
    private String path;

    @Column(name = "`describeStatic`")
    private String describestatic;

    @Column(name = "`createTime`")
    private String createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return videoName
     */
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return describeStatic
     */
    public String getDescribestatic() {
        return describestatic;
    }

    /**
     * @param describestatic
     */
    public void setDescribestatic(String describestatic) {
        this.describestatic = describestatic;
    }

    /**
     * @return createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}