package com.ys.tmall.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类，扫描mapper下面的所有接口(dao)
 * 当然也可以直接在mapper文件上分别注明mapper
 */
@Configuration
@MapperScan("com.ys.tmall.mapper")
public class MybatisConfig {
}
