package com.gmunidata.utils;

import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Map;

public class MapToBeanUtil {

    public static Object mapToObject(Map<String, Object> map, Class<?> bean) throws Exception {
            if (map == null)
                return null;

        Object obj = bean.newInstance();
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }
            field.setAccessible(true);
            Class type = field.getType();
            if(type == Date.class){
                field.set(obj, transDate(map.get(field.getName())));
            }else if(type == String.class){
                if(map.get(field.getName())!=null){
                    field.set(obj,String.valueOf(map.get(field.getName())));
                }else{
                    field.set(obj,null);
                }
            }else{
                field.set(obj,map.get(field.getName()));
            }
        }

        return obj;
    }

    /**
     * 将非日期类型转换为日期类型
     * @param value
     * @return
     */
    private static Date transDate(Object value){
        if(value instanceof String){
            String str = value.toString();
            if(str.length()>0&&str.length()<11){
                return DateUtils.string2Date(str,DateUtils.LONG_DATE);
            }else if(str.length()>=11){
                return DateUtils.string2Date(str,DateUtils.COMMON_DATETIME);
            }
        }else if(value instanceof  Long){
            long val = (long) value;
            return DateUtils.long2Date(val);
        }else if(value instanceof  Date){
            return (Date) value;
        }
        return null;
    }
}

