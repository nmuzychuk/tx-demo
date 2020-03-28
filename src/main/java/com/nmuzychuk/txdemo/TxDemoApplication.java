package com.nmuzychuk.txdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class TxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxDemoApplication.class, args);
    }

}
