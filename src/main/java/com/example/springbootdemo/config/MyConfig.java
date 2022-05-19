package com.example.springbootdemo.config;


import com.example.springbootdemo.pojo.Video;
import com.example.springbootdemo.util.MyFilter;
import com.example.springbootdemo.util.MySelector;
import org.springframework.context.annotation.*;

/**
 *  1、配置类中使用@Bean向容器中注册组件，注册的组件默认是单实例的
 *  2、配置类本身也是组件
 *  3、proxyBeanMethods：是否代理bean的方法：
 *      Full:如果是true。拿到的MyConfig对象是代理对象类型，其方法会被代理，如user1方法，会去IOC容器中获取Uer对象
 *      Lite:如果是false。拿到的Myconfig对象是MyConfig类型的对象。调用其方法会直接调用
 *
 *      实践怎么使用：
 *          lite：如果组件之间没有依赖关系，如这个user对象随便注入一个pet对象就可以、
 *          full：组件之间有依赖关系，方法会被调用得到之前的单例组件，比如user需要获得IOC中的pet对象
 *
 */
@Configuration(proxyBeanMethods = true)
//在@ComponentScan中配置过滤器
@ComponentScan(value = "com.example.springbootdemo",includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyFilter.class})
})
@Scope //设置单例、多例、
@Lazy //单实例，懒加载。第一次用，去创建对象，之后不再创建
@Import({Video.class, MySelector.class}) //用法1：将组件导入，id是全类名
//用法2：ImportSelector
public class MyConfig {



}
