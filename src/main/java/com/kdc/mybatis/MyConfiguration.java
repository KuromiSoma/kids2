package com.kdc.mybatis;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class MyConfiguration {

	@Autowired
	@Bean
	public DataSource dataSource() throws SQLException {
		org.postgresql.jdbc3.Jdbc3SimpleDataSource dataSource = new org.postgresql.jdbc3.Jdbc3SimpleDataSource();
		dataSource.setUser("kidcontrol_user");
		dataSource.setPassword("kidtest");
		//dataSource.setUrl("jdbc:postgresql://172.30.20.110:5432/hibikidb");
		//dataSource.setUrl("jdbc:postgresql://172.30.20.110:5432/hibikidb?currentSchema=kidcontrol_new");
		//dataSource.setUrl("jdbc:postgresql://10.1.1.10:5432/kidcontrol_db");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/kidcontrol_db");
		//dataSource.setUrl("jdbc:postgresql://172.30.254.2:5432/kidcontrol_db");
		return dataSource;
	}
	
    @Autowired
    @Bean
    public DataSourceTransactionManager transactionManager(
            @Qualifier("dataSource") DataSource dataSource) {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }

    @Autowired
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(
                new DefaultResourceLoader());

        // MyBatis のコンフィグレーションファイル
        bean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis.xml"));
        // MyBatis で使用する SQL ファイル群
        bean.setMapperLocations(resolver.getResources("classpath:mybatis/sql/*/**/*.xml"));

        return new SqlSessionTemplate(bean.getObject());
    }

}
