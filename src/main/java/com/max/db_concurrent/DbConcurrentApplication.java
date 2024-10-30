package com.max.db_concurrent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.max.db_concurrent.dao")
public class DbConcurrentApplication {

  public static void main(String[] args) {
    SpringApplication.run(DbConcurrentApplication.class, args);
  }

}
