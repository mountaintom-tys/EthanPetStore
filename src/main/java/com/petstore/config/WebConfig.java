
package com.petstore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
/**
 * @program: file
 * @description: (webconfig 视图层自定义配置)
 * @author: kikock
 * @create: 2018-11-15 09:26
 * 
 * 关键点在于两个： 1、配置order属性  2、配置viewnames属性
 *
 *  
 *
 * Controller 层
 *
 *  return new ModelAndView("jsp/index");
 *
 * 或者
 *
 * return "jsp/index"
 *
 *
 *  对应访问 /jsp/index.jsp
 *  ==========================
 * 直接返回String类型字符串 
 * return  "index".
 *
 * 对应访问 /templates/index.html
 * ————————————————
 *  
 *  静态资源在 /static
 *       引用方式和springboot默认一样
 *       ./退回 页面根目录--> 对应为static
 *  
 **/
@Configuration
@ComponentScan
public class WebConfig implements WebMvcConfigurer {
    //日志
    private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

    /**
     * @Description: 注册jsp视图解析器
     * @params: []
     * @return: org.springframework.web.servlet.ViewResolver
     * @author kikock
     * @date 2018/11/15 9:28
     */
    @Bean
    public ViewResolver indexViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/"); //配置放置jsp文件夹
        resolver.setViewNames("index/*.jsp");  //重要 setViewNames 通过它识别为jsp页面引擎
        resolver.setOrder(1);//设置解析的等级
        return resolver;
    }
    @Bean
    public ViewResolver adminViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/"); //配置放置jsp文件夹
        resolver.setViewNames("admin/*.jsp");  //重要 setViewNames 通过它识别为jsp页面引擎
        resolver.setOrder(2);//设置解析的等级
        return resolver;
    }
    /**
     * @Description: 注册html视图解析器
     * @params: []
     * @return: org.thymeleaf.templateresolver.ITemplateResolver
     * @author kikock
     * @date 2018/11/15 9:30
     */
//    @Bean
//    public ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setTemplateMode("HTML");
//        templateResolver.setPrefix("/templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setCharacterEncoding("utf-8");
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }

    /**
     * @Description: 将自定义tml视图解析器添加到模板引擎并主持到ioc
     * @params: []
     * @return: org.thymeleaf.spring5.SpringTemplateEngine
     * @author kikock
     * @date 2018/11/15 9:32
     */
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
    /**
     * @Description: Thymeleaf视图解析器配置
     * @params: []
     * @return: org.thymeleaf.spring5.view.ThymeleafViewResolver
     * @author kikock
     * @date 2018/11/15 9:38
     */
//    @Bean
//    public ThymeleafViewResolver viewResolverThymeLeaf() {
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("utf-8");
//        viewResolver.setViewNames(new String[]{"themleaf"});
//        viewResolver.setOrder(2);
//        return viewResolver;
//    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * @Description: 配置静态文件映射
     * @params: [registry]
     * @return: void
     * @author kikock
     * @date 2018/11/15 9:41 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }


}

