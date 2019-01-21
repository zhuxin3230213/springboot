package com.gmunidata.security.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacUtils {
    public static String getMac(){
        try{
            InetAddress ia = InetAddress.getLocalHost();
            System.out.println(ia);
            //获取网卡，获取地址
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for(int i=0; i<mac.length; i++) {
                if(i!=0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i]&0xff;
                String str = Integer.toHexString(temp);
                if(str.length()==1) {
                    sb.append("0"+str);
                }else {
                    sb.append(str);
                }
            }
            return sb.toString().replaceAll("-","");
        }catch (Exception e){
            e.printStackTrace();
            return "000000000000";
        }
    }
}
