package work.codehub.library.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 对象工具类 .<br>
 *
 * @author andy.sher
 * @date 2019/3/5 17:24
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 将原对象的信息复制到目标对象（两个对象可以是不同类型，待复制的字段必须相同） .
     *
     * @param source 原对象
     * @param target 目标对象
     * @return void
     * @author andy.sher
     * @date 2019/3/5 11:21
     */
    public static <R extends Serializable, T extends Serializable> void copySourceToTarget(R source, T target, String... ignore) {
        // 获取所有的字段
        Field[] fields = FieldUtils.getAllFields(source.getClass());
        if (ArrayUtils.isNotEmpty(fields)) {
            BeanWrapper sourceBeanWrapper = new BeanWrapperImpl(source);
            BeanWrapper targetBeanWrapper = new BeanWrapperImpl(target);
            List<String> ignoreList = new ArrayList<>();
            if (ArrayUtils.isNotEmpty(ignore)) {
                List<String> fixedList = Arrays.asList(ignore);
                ignoreList.addAll(fixedList);
            }
            // 该字段无需复制也无法复制
            ignoreList.add("serialVersionUID");
            // 将原对象的字段的值复制到目标对象的字段
            Object value;
            for (Field field : fields) {
                if (CollectionUtils.isEmpty(ignoreList) || !ignoreList.contains(field.getName())) {
                    value = sourceBeanWrapper.getPropertyValue(field.getName());
                    try {
                        targetBeanWrapper.setPropertyValue(field.getName(), value);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 将原对象的信息复制到目标对象（两个对象可以是不同类型，待复制的字段必须相同） .
     *
     * @param source
     * @param target
     * @author zhuang.shao
     * @date 2019年4月17日 下午5:13:22
     */
    public static <R extends Serializable, T extends Serializable> void copySourceToTarget(R source, T target) {
        copySourceToTarget(source, target, getNullPropertyNames(source));
    }
    
    /**
     * 将原对象的信息复制到目标对象
     * @param source
     * @param target
     * @return
     */
    public static <R extends Serializable, T extends Serializable> T copy(R source, Class<T> targetClass) {
        T target = null;
        try {
            if (null != source) {
                target = targetClass.newInstance();
                copySourceToTarget(source, target);
            }
        } catch (Exception e) {
        }
        return target;
    }
    
    /**
     * 将原集合对象的信息复制到目标集合对象
     * @param source
     * @param target
     * @return
     */
    public static <R extends Serializable, T extends Serializable> List<T> copyList(List<R> sourceList, Class<T> targetClass) {
        List<T> list = new ArrayList<T>();
        if (null == sourceList || sourceList.size() <= 0) {
            return list;
        }
        try {
            for (R source : sourceList) {
                T target = targetClass.newInstance();
                copySourceToTarget(source, target);
                list.add(target);
            }
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 获取为空的字段的名称数组
     *
     * @param source
     * @return
     * @author zhuang.shao
     * @date 2019年4月10日 下午6:04:32
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (null == srcValue) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
