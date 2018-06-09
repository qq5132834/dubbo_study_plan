package com.xxx.rpc.common.util;
 

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author huangyong
 * @since 1.0.0
 */
public class CollectionUtil {

    /**
     * 判断 Collection 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
    	if(collection!=null && collection.size()>0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    /**
     * 判断 Collection 是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断 Map 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map==null?true:false;
    }

    /**
     * 判断 Map 是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}