package com.sell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.sell.mapper")
@EnableCaching
public class WeixinSellApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeixinSellApplication.class, args);
	}
}
