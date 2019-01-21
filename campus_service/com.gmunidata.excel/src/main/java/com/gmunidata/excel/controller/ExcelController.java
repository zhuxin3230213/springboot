package com.gmunidata.excel.controller;

import com.gmunidata.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "/excel",description ="excel管理控制类" )
@RestController
@RequestMapping(value = "excel" )
public class ExcelController {
    @RequestMapping(value = "resExcel", method = RequestMethod.POST)
    @ApiOperation(value = "校验Excel,返回nonfile：无可以导入的sheet页，返回error：IO异常，返回fileName;正常")
    public Map<String,Object> resExcel(HttpServletRequest request){
        Map<String, Object> res = new HashMap<>();
        try{
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("file");
        List<String> nonfile =new ArrayList<>();
        Workbook workbook = null;
        String lastName ="";
        String fileName =file.getOriginalFilename();
                List<String> xlsName = new ArrayList<>();
                if(fileName.substring(fileName.indexOf(".")).equals(".xls")){
                        workbook =new HSSFWorkbook(file.getInputStream());
                        for (int i = 0; i < workbook.getNumberOfSheets() ; i++) {
                            String sheetName=workbook.getSheetAt(i).getSheetName();
                            xlsName.add(sheetName);
                        }
                        res.put("fileName",fileName);
                        res.put("sheetName",xlsName);
                        lastName = ".xls";
                }else if (fileName.substring(fileName.indexOf(".")).equals(".xlsx")){

                        workbook =new XSSFWorkbook(file.getInputStream());
                        for (int i = 0; i < workbook.getNumberOfSheets() ; i++) {
                            String sheetName=workbook.getSheetAt(i).getSheetName();
                            xlsName.add(sheetName);
                        }
                        res.put("fileName",fileName);
                        res.put("sheetName",xlsName);
                        lastName = ".xlsx";

                }else{
                    nonfile.add(fileName);
                }

            if (nonfile.size()!=0){
                res.put("nonfile",nonfile);
            }
           String uuid = ExcelUtil.saveFile(file,lastName);
                res.put("uuid",uuid);
        }catch (Exception e){
            e.printStackTrace();
            res.put("error","IOException");
            return res;
        }

        return res;

    }

    @RequestMapping(value = "delExcel",method = RequestMethod.POST)
    @ApiOperation(value = "删除excel ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid",value = "uuid",required = true),
    })
    public void delExcel(@RequestBody @ApiIgnore Map<String,Object> params){
        ExcelUtil.deleteFile((String) params.get("uuid"));
    }


}
