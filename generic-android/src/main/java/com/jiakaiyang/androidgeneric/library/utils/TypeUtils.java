package com.jiakaiyang.androidgeneric.library.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaiyangjia on 2016/1/29.
 */
public class TypeUtils {

    /**
     * 把一个bean对象转换成一个Map对象，返回的Map中key为Bean对象的成员名称，value为bean的该成员对应的值
     * @param bean
     * @return
     */
    public static Map getMapFromBean(Object bean){
        Map result = new HashMap();

        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field entity : fields){
            String name = entity.getName();
            boolean access = entity.isAccessible();
            if(!access){
                entity.setAccessible(true);
            }

            try {
                Object o = entity.get(bean);
                result.put(name, o);
                if(!access){
                    entity.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                continue;
            }
        }
        return result;
    }
}
