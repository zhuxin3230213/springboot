package cn.gmuni.zsgl.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author:ZhuXin
 * @Description: 页面访问统计
 * @Date:Create in 16:37 2018/6/12
 * @Modified By:
 **/

@Entity
@Table(name = "zs_gmuni_visit_statistics")
public class VisitStatistics implements Serializable {

    @Id
    @GenericGenerator(name = "visitStatistics_uuid", strategy = "uuid")
    @GeneratedValue(generator = "visitStatistics_uuid")
    private String id; //主键

    @Column(name = "url")
    private String pageUrl; //页面url
    @Column(name = "name")
    private String pageName; //页面名称
    private String module; //所属模块
    @Column(name = "hostip")
    private String hostIp; //用户ip
    private String time; //访问时间

    public VisitStatistics() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "VisitStatistics{" +
                "id='" + id + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", pageName='" + pageName + '\'' +
                ", module='" + module + '\'' +
                ", hostIp='" + hostIp + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
