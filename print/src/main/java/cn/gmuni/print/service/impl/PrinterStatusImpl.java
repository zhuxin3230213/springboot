package cn.gmuni.print.service.impl;

import cn.gmuni.print.model.Result;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author: zhuxin
 * @Date: 2018/11/12 09:36
 * @Description:
 */
@Service
public class PrinterStatusImpl {

   // 通过指令获取tsc标签打印机的状态
    public Result status(String ipaddress, int portnumber) throws Exception {
        Result result = new Result();
        InputStream inputStream;
        OutputStream outputStream;
        Socket socket;
        byte[] readBuf = new byte[1024];

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ipaddress, portnumber), 20);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMessage("打印机连接失败!");
            return result;
        }
        byte[] message = new byte[]{27, 33, 63};

        String query = "";

        try {
            outputStream.write(message);
        } catch (IOException e) {
            result.setCode(0);
            result.setMessage("获取打印机状态失败!");
            return result;
        }


        try {
            int i = inputStream.available();
            while (i == 0) {
                i = inputStream.available();
            }
            inputStream.read(readBuf);
        } catch (IOException var7) {
            result.setCode(0);
            result.setMessage("读取打印机状态失败!");
            return result;
        }

        if (readBuf[0] == 0) {
            result.setCode(1);
            result.setMessage("打印机准备就绪");
        } else if (readBuf[0] == 1) {
            result.setCode(0);
            result.setMessage("打印头开启!");
        } else if (readBuf[0] == 2) {
            result.setCode(0);
            result.setMessage("纸张卡纸!");
        } else if (readBuf[0] == 3) {
            result.setCode(0);
            result.setMessage("打印头开启并且纸张卡纸!");
        } else if (readBuf[0] == 4) {
            result.setCode(0);
            result.setMessage("纸张缺纸!");
        } else if (readBuf[0] == 5) {
            result.setCode(0);
            result.setMessage("打印头开启并且纸张缺纸!");
        } else if (readBuf[0] == 8) {
            result.setCode(0);
            result.setMessage("无碳带!");
        } else if (readBuf[0] == 9) {
            result.setCode(0);
            result.setMessage("打印头开启并且无碳带!");
        } else if (readBuf[0] == 10) {
            result.setCode(0);
            result.setMessage("纸张卡纸并且无碳带!");
        } else if (readBuf[0] == 11) {
            result.setCode(0);
            result.setMessage("打印头开启、纸张卡纸并且无碳带!");
        } else if (readBuf[0] == 12) {
            result.setCode(0);
            result.setMessage("纸张缺纸并且无碳带!");
        } else if (readBuf[0] == 13) {
            result.setCode(0);
            result.setMessage("打印头开启、纸张缺纸并且无碳带!");
        } else if (readBuf[0] == 16) {
            result.setCode(0);
            result.setMessage("打印机暂停!");
        } else if (readBuf[0] == 32) {
            result.setCode(1);
            result.setMessage("打印中!");
        } else if (readBuf[0] == 128) {
            result.setCode(0);
            result.setMessage("打印机发生错误!");
        }
        socket.close();
        return result;

    }



    //打印
    public void  print(String pathName){
        JFileChooser fileChooser = new JFileChooser(); //创建打印作业
        int state = fileChooser.showOpenDialog(null);
        if(state == fileChooser.APPROVE_OPTION){
            File file = new File(pathName); //获取选择的文件
            //构建打印请求属性集
            HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            //设置打印格式，因为未确定类型，所以选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            //查找所有的可用的打印服务
            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            //定位默认的打印服务
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
            //显示打印对话框
            PrintService service = ServiceUI.printDialog(null, 200, 200, printService,
                    defaultService, flavor, pras);
            if(service != null){
                try {
                    DocPrintJob job = service.createPrintJob(); //创建打印作业
                    FileInputStream fis = new FileInputStream(file); //构造待打印的文件流
                    DocAttributeSet das = new HashDocAttributeSet();
                    Doc doc = new SimpleDoc(fis, flavor, das);
                    job.print(doc, pras);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

   // JAVA程序调用惠普打印机，并且获取打印机的状态
    public void PDFPrint(File file) throws PrintException {
        if (file == null) {
            System.err.println("缺少打印文件");
        }
        InputStream fis = null;
        try {
            // 设置打印格式，因为未确定类型，所以选择autosense
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            // 打印参数
            DocAttributeSet aset = new HashDocAttributeSet();
            // aset.add(new Copies(1)); //份数
            // aset.add(MediaSize.ISO.A4); //纸张
            // aset.add(Finishings.STAPLE);//装订
            // 定位默认的打印服务
            PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

            fis = new FileInputStream(file); // 构造待打印的文件流

            Doc doc = new SimpleDoc(fis, flavor, aset);
            DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
            job.addPrintJobListener(new PrintJobListener() {

                @Override
                public void printDataTransferCompleted(PrintJobEvent arg0) {
                    System.out.println("printDataTransferCompleted");

                }

                @Override
                public void printJobCanceled(PrintJobEvent arg0) {
                    System.out.println("printJobCanceled");

                }

                @Override
                public void printJobCompleted(PrintJobEvent arg0) {
                    System.out.println("printJobCompleted");

                }

                @Override
                public void printJobFailed(PrintJobEvent arg0) {
                    System.out.println("printJobFailed");

                }

                @Override
                public void printJobNoMoreEvents(PrintJobEvent arg0) {
                    System.out.println("printJobNoMoreEvents");

                }

                @Override
                public void printJobRequiresAttention(PrintJobEvent arg0) {
                    System.out.println("printJobRequiresAttention");

                }

            });
            job.print(doc, null);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
