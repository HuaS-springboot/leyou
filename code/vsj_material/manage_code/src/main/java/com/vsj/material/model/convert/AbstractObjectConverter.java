package com.vsj.material.model.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Classname AbstractObjectConverter
 * @Description 对象转换抽象类
 * @Date 2019/8/13 14:55
 * @Created by wangzx
 */
public abstract class AbstractObjectConverter <E,T> implements Serializable {

    private static final long serialVersionUID = 6632194542788974909L;

    private static final Logger logger = LoggerFactory.getLogger(com.vsj.common.AbstractObjectConverter.class);

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
