package work.codehub.library.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * SpringIOC容器工具类 .<br>
 *
 * @author andy.sher
 * @date 2018/7/11 9:58
 */
@Slf4j
@Service("springContextUtil")
public final class SpringContextUtils implements ApplicationContextAware {

    private SpringContextUtils() {
        log.info("成功初始化[IOC容器工具类]。");
    }

    /**
     * Spring上下文对象
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @param
     * @return org.springframework.context.ApplicationContext
     * @author zhuang.shao
     * @date 2018/7/13 0013 下午 2:15
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 按类型获取托管对象 .<br>
     *
     * @param clazz 类型
     * @return T 对象
     * @author andy.sher
     * @date 2018/7/12 9:41
     */
    public static <T> T getBean(Class<T> clazz) {
        return SpringContextUtils.applicationContext.getBean(clazz);
    }

    /**
     * 按bean的注册名获取托管对象 .<br>
     *
     * @param beanName bean名称
     * @return java.lang.Object 对象
     * @author andy.sher
     * @date 2018/7/12 9:41
     */
    public static Object getBean(String beanName) {
        return SpringContextUtils.applicationContext.getBean(beanName);
    }

    /**
     * 按bean的注册名和类型获取托管对象 .<br>
     *
     * @param beanName bean名称
     * @param clazz    类型
     * @return T 对象
     * @author andy.sher
     * @date 2018/7/12 9:41
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return SpringContextUtils.applicationContext.getBean(beanName, clazz);
    }

    /**
     * 按类型获取Bean .<br>
     *
     * @param clazz 类型
     * @return java.util.Map<java.lang.String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               T> Bean
     * @author andy.sher
     * @date 2018/7/12 9:40
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return SpringContextUtils.applicationContext.getBeansOfType(clazz);
    }

}
