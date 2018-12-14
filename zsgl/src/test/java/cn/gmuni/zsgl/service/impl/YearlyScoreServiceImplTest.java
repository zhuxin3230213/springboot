package cn.gmuni.zsgl.service.impl;

import cn.gmuni.zsgl.service.YearlyScoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


/**
 * @Author:ZhuXin
 * @Description:
 *YearlyScoreServiceImplTest
 * @Date:Create in 15:52 2018/6/11
 * @Modified By:
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class YearlyScoreServiceImplTest {

    @Autowired
    YearlyScoreService yearlyScoreService;

    /**
     * var arr = ['a-b-c','a-b-d','e-f','e-g','h'];
     * var obj = {};
     * function test(obj,str){
     *         const keys = str.split('-');
     *         const key = keys.shift();
     *         if(!obj[key]){
     *            if(keys.length>0){
     *                 obj[key] = {};
     *                 test(obj[key],keys.join("-"));
     *            }else{
     *                obj[key] = '';
     *            }
     *     }else{
     *             if(keys.length>0){
     *                 test(obj[key],keys.join("-"));
     *             }
     *         }
     * }
     *
     * for(var i=0;i<arr.length;i++){
     *     test(obj,arr[i]);
     * }
     */


    /**
     * 根据年份和省份获取历年分数
     */
    @Test
    public void listByYearAndProvinces() {
        List<Map<String,Object>> res = new ArrayList<>();
        List<Map> maps = yearlyScoreService.listByYearAndProvinces(2017, "610000");
        Map<String,Object> map = new HashMap<>();

        for(Map<String ,Object> mp : maps){
            for (Map.Entry<String ,Object> temp:mp.entrySet()){
                if ("familyText".equals(temp.getKey())){
                    test(map,mp.get("familyText").toString());
                    temp.setValue(map);
                    System.out.println(temp.getKey()+"_______"+temp.getValue());
                }

            }
        }

        List<Map<String,Object>> lm = buildChildren(map);
        for (Map<String ,Object> mp :lm){
             for (Map.Entry<String ,Object> temp:mp.entrySet()){
                 System.out.println(temp.getKey()+"____________"+temp.getValue());
             }
        }

    }


    /**
     * 递归封装
     * @param map
     * @param str
     */
    private void test(Map<String, Object> map, String str) {
        List<String> keys = Arrays.asList(str.split("-"));
        String key = keys.get(0);
        List<String> st = new ArrayList<>(keys);
        st.remove(0);
        if (map.get(key) == null) {
            if (st.size() > 0) {
                map.put(key, new HashMap<String, Object>());
                test((Map<String, Object>) map.get(key), org.apache.commons.lang3.StringUtils.join(st, "-"));
            } else {
                map.put(key, "");
            }
        } else {
            if (keys.size() > 0) {
                test((Map<String, Object>) map.get(key), org.apache.commons.lang3.StringUtils.join(st, "-"));
            }
        }
    }


    /**
     * 构建子集
     * @param mp
     * @return
     */
    private List<Map<String, Object>> buildChildren(Map<String, Object> mp) {
        List<Map<String, Object>> lmp = new ArrayList<>();
        for (String key : mp.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", key);
            if (mp.get(key) instanceof Map) {
                map.put("children", buildChildren((Map<String, Object>) mp.get(key)));
            }
            lmp.add(map);
        }
        return lmp;
    }



/*

    $(data).each(function(index,item){
        var familyTextArr = item.familyText.split("-");
        if(index>0){
            var familyTextPrevArr = $(data)[index-1].familyText.split("-");
        }
        if(familyTextArr.length == 1){//长度等于1代表不包含子集，本条数据正常显示
            trs +='<tr>'+
                    '<td>'+item.batchText+'</td>'+
                    '<td>'+item.familyText+'</td>'+
                    '<td>'+item.scoreline+'</td>'+
                    '<td>'+item.max_score+'</td>'+
                    '<td>'+item.min_score+'</td>'+
                    '<td>'+item.avg_score+'</td>'+
                    '</tr>';
        }else if(familyTextArr.length > 1){
            if(index==0 || (index>0 && familyTextArr[0] != familyTextPrevArr[0])){//当前数据与前一条数 据首元素相等时，则前面的元素合并
                $(familyTextArr).each(function(indexII,itemII){
                    if(indexII<familyTextArr.length-1){
                        trs += '<tr><td>'+item.batchText+'</td><td style="padding-left:'+35*indexII+'px"><img src="../../static/images/fileIcon.png" height="15" style="margin-left:10px;">&nbsp;'+itemII+'</td>' +
                                '<td>-</td><td>-</td><td>-</td><td>-</td></tr>';
                    }else if(indexII==familyTextArr.length-1){
                        trs += '<tr><td>'+item.batchText+'</td><td style="padding-left:'+35*indexII+'px">'+itemII+'</td><td>'+item.scoreline+'</td>'+
                                '<td>'+item.max_score+'</td><td>'+item.min_score+'</td><td>'+item.avg_score+'</td></tr>';
                    }
                });
            }else{
                trs += '<tr><td>'+item.batchText+'</td><td style="padding-left:'+35*(familyTextArr.length-1)+'px">'+familyTextArr[familyTextArr.length-1]+'</td><td>'+item.scoreline+'</td>'+
                        '<td>'+item.max_score+'</td><td>'+item.min_score+'</td><td>'+item.avg_score+'</td></tr>';
            }
        }
    })

*/



    /**
     * 获取历年分数
     */
    @Test
    public  void test1(){
        List<Map> maps = yearlyScoreService.listByYearAndProvinces(2017, "610000");
        for (int i =0 ; i<maps.size() ;i++){
            Map mp = new HashMap();
            Map map = maps.get(i);
            Object familyText = map.get("familyText");
            String[] split = familyText.toString().split("-");
            String[] split1 =null;
            if (i>0){
                Map map1 = maps.get(i - 1);
                Object familyText1 = map.get("familyText");
                split1 = familyText1.toString().split("-");
            }
            if (split.length==1){
                 mp.put("familyText",map.get("familyText"));
            }else if (split.length>1){
                   if (i==0 || (i > 0 && split[0] != split1[0] )){
                            for (int j = 0 ;j<split.length ; j++ ){
                                if (j<split.length-1){
                                    mp.put("familyText",map.get("familyText"));
                                }
                                if (j == split.length-1){
                                    mp.put("familyText",map.get("familyText"));
                                }
                            }
                   }else {

                   }
            }else{
                   String st= split[split.length-1];
                   mp.put("familyText",st);
            }

            System.out.println(mp.entrySet().toString());

        }

    }





}