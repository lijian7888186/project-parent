package com.project.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;

/**
 * describe:
 *
 * @author
 */
public class BeansManager implements ApplicationContextAware{
    private static ApplicationContext applicationContext;
    /**
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }
    public static  <T> T getBean(Class<T> tClass) {
        T bean = applicationContext.getBean(tClass);
        return bean;
    }
    public static Object getBean(String name) {
        Object bean = applicationContext.getBean(name);
        return bean;
    }
    public static <T> T getBean(Class<T> tClass, String name) {
        T bean = applicationContext.getBean(name, tClass);
        return bean;
    }
 }
