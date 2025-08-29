package ru.redtoss.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("ru.redtoss.library")
@EnableWebMvc
//@PropertySource("classpath:app.properties")
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer {


    private final ApplicationContext applicationContext;
    private final Environment environment;


    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver getTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(getTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }



    //Указываем ресурсы, откуда будем брать данные
    //Hibernate DataSource
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getRequiredProperty("hibernate.driver_class")));
        dataSource.setUrl(Objects.requireNonNull(environment.getRequiredProperty("hibernate.connection.url")));
        dataSource.setUsername(Objects.requireNonNull(environment.getRequiredProperty("hibernate.connection.username")));
        dataSource.setPassword(Objects.requireNonNull(environment.getRequiredProperty("hibernate.connection.password")));
        return dataSource;
    }

    //Hibernate настройка
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.highlight_sql", environment.getRequiredProperty("hibernate.highlight_sql"));
        properties.put("hibernate.format_sql", "true");
        return properties;
    }



    @Bean
    public LocalSessionFactoryBean sessionFactoryBean() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("ru.redtoss.library.models");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());
        return transactionManager;
    }


    ///JDBC DataSource
/*
    @Bean
    public DataSource dataSourceJDBC() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getRequiredProperty("db.driver")));
        dataSource.setUrl(Objects.requireNonNull(environment.getRequiredProperty("db.url")));
        dataSource.setUsername(Objects.requireNonNull(environment.getRequiredProperty("db.name")));
        dataSource.setPassword(Objects.requireNonNull(environment.getRequiredProperty("db.password")));
        return dataSource;
    }

    //  JDBC Настройка
    @Bean
    public JdbcTemplate template() {
        return new JdbcTemplate(dataSource());
    }*/
}


