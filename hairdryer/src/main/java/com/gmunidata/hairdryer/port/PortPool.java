package com.gmunidata.hairdryer.port;

import gnu.io.SerialPort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class PortPool {
    //TODO 这里到时候换成 map  映射去获取
    private static Map<String,SerialPort> comCache = new HashMap<>();

    @PostConstruct
    private void initPort(){
        //TODO   demo只初始化一个串口，到时候根据 数据库去初始化
        try{
            SerialPort com = SerialPortManager.openPort("COM3", 9600);
           new Thread(){
               @Override
               public void run() {
                   while (true){
                       try{
                           Thread.sleep(2000);
                           SerialPortManager.sendToPort(com, new byte[]{0x24 ,0x30, 0x31,0x36,13});
                       }catch (Exception e){

                       }
                   }
               }
           }.start();
            comCache.put("COM3", com);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static SerialPort getPort(String port){
        port = port.toUpperCase();
        if(comCache.containsKey(port) && null != comCache.get(port)){
            return comCache.get(port);
        }
        try{
            SerialPort com = SerialPortManager.openPort(port, 9600);
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        try{
                            Thread.sleep(2000);
                            SerialPortManager.sendToPort(com, new byte[]{0x24 ,0x30, 0x31,0x36,13});
                        }catch (Exception e){

                        }
                    }
                }
            }.start();
            comCache.put(port, com);
            return com;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
