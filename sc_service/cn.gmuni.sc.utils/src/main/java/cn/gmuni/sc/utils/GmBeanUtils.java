package cn.gmuni.sc.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class GmBeanUtils {


    /**
     * 将source合并到target中，忽略source中的空值
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object source,Object target){
        BeanUtils.copyProperties(source,target,getNullPropertyNames(source));
    }

    /**
     * 将source合并到target中
     * @param source
     * @param target
     */
    public static void copyProperties(Object source,Object target){
        BeanUtils.copyProperties(source,target);
    }


    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
