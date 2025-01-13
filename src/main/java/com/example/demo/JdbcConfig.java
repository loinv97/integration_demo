//package com.example.demo;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class JdbcConfig {
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.sap.db.jdbc.Driver");  // SAP HANA JDBC driver class name
//        dataSource.setUrl("jdbc:sap://<hana_host>:<hana_port>");
//        dataSource.setUsername("<username>");
//        dataSource.setPassword("<password>");
//        return dataSource;
//    }
//}
