package com.vsj.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName: AbstractObjectConverter
 * @Description: 对象转换,E2T or T2E
 * @author: mario 
 * @date: 2019年7月31日 下午5:56:09
 * @copyright: 青岛微视角文化传媒有限公司
 * @param <E>
 * @param <T>
 */
public abstract class AbstractObjectConverter <E,T>{
	private static final Logger logger = LoggerFactory.getLogger(AbstractObjectConverter.class);
	public  T convert(E source, Class<T> targetClazz)  {
        if (source == null) {
            return null;
        }
        T to = null;
        try {
			to = targetClazz.newInstance();
		} catch (Exception e) {
			logger.error("实例化对象{}失败...",targetClazz.getName());
		} 
        if (to!=null) {
        	convertImpl(source, to);
		}
        return to;
    }
	
	public  E reConvert(T source, Class<E> targetClazz)  {
        if (source == null) {
            return null;
        }
        E to = null;
        try {
			to = targetClazz.newInstance();
		} catch (Exception e) {
			logger.error("实例化对象{}失败...",targetClazz.getName());
		} 
        if (to!=null) {
        	reConvertImpl(source, to);
		}
        return to;
    }
	/**
	 * 
	 * @Title: convertImpl
	 * @Description: E2T
	 * @param source
	 * @param target
	 * @author mario
	 * @return: void
	 */
	protected abstract void convertImpl(E source, T target);
	
	/**
	 * 
	 * @Title: reConvertImpl
	 * @Description: T2E
	 * @param source
	 * @param target
	 * @author mario
	 * @return: void
	 */
	protected abstract void reConvertImpl(T source, E target);
}
