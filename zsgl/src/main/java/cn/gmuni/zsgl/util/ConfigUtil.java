package cn.gmuni.zsgl.util;

/**
 * @Author:ZhuXin
 * @Description: 配置信息工具类
 * @Date:Create in 11:50 2018/7/6
 * @Modified By:
 **/

public class ConfigUtil {

    private String tel; //电话
    private String email; //邮箱
    private String address; //地址
    private String post; //邮编
    private String fax; //传真
    private String netAddress; //网站地址
    private String weibo; //微博
    private String wechat; //微信

    public ConfigUtil() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNetAddress() {
        return netAddress;
    }

    public void setNetAddress(String netAddress) {
        this.netAddress = netAddress;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String toString() {
        return "ConfigUtil{" +
                "tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", post='" + post + '\'' +
                ", fax='" + fax + '\'' +
                ", netAddress='" + netAddress + '\'' +
                ", weibo='" + weibo + '\'' +
                ", wechat='" + wechat + '\'' +
                '}';
    }
}
