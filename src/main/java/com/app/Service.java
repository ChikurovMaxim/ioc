package com.app;

import com.inter.Inter;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import org.reflections.Reflections;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Maksym on 09.03.2017.
 */

public class Service {

    private String packageToLook;
    private Reflections reflections;
    private Set<Class<?>> annotated;
    private Set<Field> injected;
    private Map<Class, Object> instanceConteiner;

    public Service(String packageToLook) {
        reflections = new Reflections(packageToLook);
        annotated = reflections.getTypesAnnotatedWith(Named.class);
        getClassRelevation();
    }

    public <T> T getBean(Class<T> beanType) {
        return null;
    }

    private void getClassRelevation()  {
        Class toWorkWith;
        String implName = null;
        for (Class cls : annotated) {
            if (cls.getDeclaredFields().length != 0) {
                List<Field> fields = Arrays.asList(cls.getDeclaredFields());
                for (Field f : fields) {
                    if (f.isAnnotationPresent(Inject.class)) {
                        toWorkWith = f.getType();
                        if (f.isAnnotationPresent(Named.class)) {
                            implName = f.getAnnotation(Named.class).value();
                        }
                        try {
                            Object o2 = cls.getConstructors()[0].newInstance();
                            for (Map.Entry<Class<?>, Map<String, Class<?>>> entry : getMap().entrySet()) {
                                if (entry.getKey().equals(toWorkWith)) {
                                    for (Map.Entry<String, Class<?>> classEntry : entry.getValue().entrySet()) {
                                        if (implName != null) {
                                            if (implName.equals(classEntry.getKey())) {
                                                f.set(o2, classEntry.getValue().getConstructors()[0].newInstance());
                                            }
                                        } else {
                                            try {
                                                f.set(entry.getKey(), classEntry.getValue());
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }catch (IllegalAccessException| InvocationTargetException| InstantiationException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }

    private Map<Class<?>, Map<String, Class<?>>> getMap() {
        Map<Class<?>, Map<String, Class<?>>> map = new HashMap<>();
        for (Class aClass : annotated) {
            if (aClass.isInterface()) {
                Set relatedClasses = reflections.getSubTypesOf(aClass);
                Map<String, Class<?>> classMap = new HashMap<>();
                for (Object o : relatedClasses) {
                    Class cl = (Class) o;
                    for (Annotation a : cl.getDeclaredAnnotations()) {
                        if (a instanceof Named) {
                            classMap.put(((Named) a).value(), cl);
                        }
                    }
                }
                map.put(aClass, classMap);
            }
        }
        return map;
    }

}
