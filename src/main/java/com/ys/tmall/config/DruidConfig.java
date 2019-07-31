package com.ys.tmall.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * druid数据源配置
 * 1、引入druid依赖
 * 2、在yml文件中配置spring.datasource.type
 * 3、配置类
 *
 * @author yaosh
 * @data 2019/7/4
 */
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties("spring.datasource") //利用配置文件赋值。
    public DataSource druid() {
        return new DruidDataSource();
    }

    // 设置管理后台的servlet
    // 将druid的StatViewServlet，注册到容器的ServletRegistrationBean。
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //添加路径映射
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        //在bean中添加初始化参数
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername","root");
        initParameters.put("loginPassword", "admin");

        bean.setInitParameters(initParameters);
        return bean;
    }

    //将druid的WebStatFilter过滤器，注册到容器的FilterRegistrationBean。
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean(new WebStatFilter());

        //在bean中添加初始化参数
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("exclusions","*.js, *.css, /druid/*");

        bean.setInitParameters(initParameters);
        return bean;
    }

}
