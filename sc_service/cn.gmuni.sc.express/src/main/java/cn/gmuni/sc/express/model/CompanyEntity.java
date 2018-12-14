package cn.gmuni.sc.express.model;

import java.io.Serializable;

/**
 * @Author: zhuxin
 * @Date: 2018/9/17 18:08
 * @Description:
 */
public class CompanyEntity implements Serializable {

    private String shipperName;

    private String shipperCode;

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "shipperName='" + shipperName + '\'' +
                ", shipperCode='" + shipperCode + '\'' +
                '}';
    }
}
