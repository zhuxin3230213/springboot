package cn.gmuni.print.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @Author: zhuxin
 * @Date: 2018/11/12 09:51
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrinterStatusImplTest {

    @Autowired
    PrinterStatusImpl printerStatusImpl;

    @Test
    public void test() {
        try {
            System.out.println("_____________");
            System.out.println(new PrinterStatusImpl().status("192.168.2.202", 515).getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {

        File file = new File("D:\\打印测试.docx");
        try {
            printerStatusImpl.PDFPrint(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2(){
        printerStatusImpl.print("D:\\打印测试.docx");
    }

}