package com.apwglobal.allegro.server.db;

import com.apwglobal.allegro.server.dao.AuctionDao;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.File;


@Configuration
@MapperScan(basePackages="com.apwglobal.allegro.server.dao")
public class DbConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(getResources());
        factoryBean.setTypeHandlers(new TypeHandler[] {new LocalDateTypeHandler(), new OptionalLocalDateTypeHandler() } );
        return factoryBean;
    }

    private Resource[] getResources() {
        return new Resource[] { new ClassPathResource(
                    new ClassPathResource(getMapperXMLPath(AuctionDao.class)).getPath()) };
    }

    public static String getMapperXMLPath(Class<?> clazz) {
        return new ClassPathResource(clazz.getName()
                .replace(".", File.separator).replace("Dao", "Mapper").concat(".xml")).getPath();
    }
}
